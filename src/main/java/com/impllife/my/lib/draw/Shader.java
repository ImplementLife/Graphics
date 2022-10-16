package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements Disposable {
	private static final Logger LOG = LoggerFactory.getLogger(Shader.class);

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	Shader(String filename) {
		programId = glCreateProgram();
		
		vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderId, readFile(filename + ".vs"));
		glCompileShader(vertexShaderId);
		if (glGetShaderi(vertexShaderId, GL_COMPILE_STATUS) != 1) {
			LOG.error(glGetShaderInfoLog(vertexShaderId));
			throw new IllegalStateException();
		}
		
		fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderId, readFile(filename + ".fs"));
		glCompileShader(fragmentShaderId);
		if (glGetShaderi(fragmentShaderId, GL_COMPILE_STATUS) != 1) {
			LOG.error(glGetShaderInfoLog(fragmentShaderId));
			throw new IllegalStateException();
		}
		
		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		
		glBindAttribLocation(programId, 0, "vertices");
		glBindAttribLocation(programId, 1, "textures");
		
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) != 1) {
			LOG.error(glGetProgramInfoLog(programId));
			throw new IllegalStateException();
		}
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) != 1) {
			LOG.error(glGetProgramInfoLog(programId));
			throw new IllegalStateException();
		}
	}
	
	public void setUniform(String uniformName, int value) {
		int location = glGetUniformLocation(programId, uniformName);
		if (location != -1) glUniform1i(location, value);
	}
	
	public void setUniform(String uniformName, Vector4f value) {
		int location = glGetUniformLocation(programId, uniformName);
		if (location != -1) glUniform4f(location, value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		int location = glGetUniformLocation(programId, uniformName);
		FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
		value.get(matrixData);
		if (location != -1) glUniformMatrix4fv(location, false, matrixData);
	}
	
	public void bind() {
		glUseProgram(programId);
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
		glDetachShader(programId, vertexShaderId);
		glDetachShader(programId, fragmentShaderId);
		glDeleteShader(vertexShaderId);
		glDeleteShader(fragmentShaderId);
		glDeleteProgram(programId);
	}
}
