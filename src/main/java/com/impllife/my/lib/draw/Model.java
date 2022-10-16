package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.Disposable;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Model implements Disposable {
	private final int drawCount;
	private final int vertexBufferId;
	private final int textureCoordinatesBufferId;
	private final int indexOfVerticesBufferId;
	
	Model(float[] vertices, float[] texture_coordinates, int[] indices) {
		drawCount = indices.length;
		
		vertexBufferId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		
		textureCoordinatesBufferId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesBufferId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(texture_coordinates), GL_STATIC_DRAW);
		
		indexOfVerticesBufferId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexOfVerticesBufferId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indices), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesBufferId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexOfVerticesBufferId);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}

	private IntBuffer createBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public void dispose() {
		glDeleteBuffers(vertexBufferId);
		glDeleteBuffers(textureCoordinatesBufferId);
		glDeleteBuffers(indexOfVerticesBufferId);
	}
}
