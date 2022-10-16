package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.Disposable;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture implements Disposable {
    private int textureId;
    Texture() {}

    void load(String filename) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filename));
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int[] pixels_raw = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(pixels_raw.length * 4);
            for (int pixel : pixels_raw) {
                pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
                pixels.put((byte) ((pixel >> 8) & 0xFF));  // GREEN
                pixels.put((byte) (pixel & 0xFF));         // BLUE
                pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
            }
            pixels.flip();

            textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        } catch (IOException e) {
            throw new IllegalStateException("Can't load a texture with name => " + filename);
        }
    }

    public void bind(int sampler) {
        if (sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, textureId);
        }
    }

    @Override
    public void dispose() {
        glDeleteTextures(textureId);
    }
}
