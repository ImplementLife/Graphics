package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements Disposable {
	private static final Logger LOG = LoggerFactory.getLogger(Shader.class);

	private int programObject;
	private int vertexShaderObject;
	private int fragmentShaderObject;
	
	Shader(String filename) {
		programObject = glCreateProgram();
		
		vertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderObject, readFile(filename + ".vs"));
		glCompileShader(vertexShaderObject);
		if (glGetShaderi(vertexShaderObject, GL_COMPILE_STATUS) != 1) {
			LOG.error(glGetShaderInfoLog(vertexShaderObject));
			throw new IllegalStateException();
		}
		
		fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderObject, readFile(filename + ".fs"));
		glCompileShader(fragmentShaderObject);
		if (glGetShaderi(fragmentShaderObject, GL_COMPILE_STATUS) != 1) {
			LOG.error(glGetShaderInfoLog(fragmentShaderObject));
			throw new IllegalStateException();
		}
		
		glAttachShader(programObject, vertexShaderObject);
		glAttachShader(programObject, fragmentShaderObject);
		
		glBindAttribLocation(programObject, 0, "vertices");
		glBindAttribLocation(programObject, 1, "textures");
		
		glLinkProgram(programObject);
		if (glGetProgrami(programObject, GL_LINK_STATUS) != 1) {
			LOG.error(glGetProgramInfoLog(programObject));
			throw new IllegalStateException();
		}
		glValidateProgram(programObject);
		if (glGetProgrami(programObject, GL_VALIDATE_STATUS) != 1) {
			LOG.error(glGetProgramInfoLog(programObject));
			throw new IllegalStateException();
		}
	}
	
	public void setUniform(String uniformName, int value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform1i(location, value);
	}
	
	public void setUniform(String uniformName, Vector4f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		if (location != -1) glUniform4f(location, value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		int location = glGetUniformLocation(programObject, uniformName);
		FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
		value.get(matrixData);
		if (location != -1) glUniformMatrix4fv(location, false, matrixData);
	}
	
	public void bind() {
		glUseProgram(programObject);
	}
	
	private String readFile(String filename) {
		StringBuilder outputString = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader("resources/shaders/" + filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				outputString.append(line).append("\n");
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return outputString.toString();
	}

	@Override
	public void dispose() {
		glDetachShader(programObject, vertexShaderObject);
		glDetachShader(programObject, fragmentShaderObject);
		glDeleteShader(vertexShaderObject);
		glDeleteShader(fragmentShaderObject);
		glDeleteProgram(programObject);
	}
}
