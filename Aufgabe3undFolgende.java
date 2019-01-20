package ab3;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {

	private ShaderProgram shaderProgram, shaderProgramKugel;
	private Matrix4 transformationMatrix = new Matrix4();
	private Matrix4 modelMatrix = new Matrix4();
	private Matrix4 viewMatrix, viewMatrixSec;
	private int counter;
	private float counter2;
	public boolean first = true;
	ObjectGenerator og = new ObjectGenerator();
	private int vaoTetra;
	private int vaold;
	private float[] tetraVertices;

	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe3");
		shaderProgramKugel = new ShaderProgram("aufgabe3Kugel");
		
		vaold = glGenVertexArrays();
		glBindVertexArray(vaold);
		int vbold = glGenBuffers();
		float[] wuerfel = new float[] {
				// vorne
				-0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F,
				0.5F, 0.5F,

				// hinten
				-0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F,

				-0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F,

				// unten
				-0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F,

				-0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F,

				// oben
				-0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F,

				-0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F,

				// links
				-0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, 0.5F,

				-0.5F, -0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F,

				// rechts
				0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, -0.5F,

				0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F };
		bind(vbold, wuerfel, 0);

		int vboColors = glGenBuffers();
		float[] normalen = new float[36 * 3];
		float[] möglicheNormalen = new float[] { 0, 0, 1, 0, 0, -1, 0, -1, 0, 0, 1, 0, -1, 0, 0, 1, 0, 0 };
		int arraypos = 0;
		for (int s = 0; s < 6; s++) {
			for (int ecken = 6; --ecken >= 0;) {
				normalen[arraypos++] = möglicheNormalen[3 * s];
				normalen[arraypos++] = möglicheNormalen[3 * s + 1];
				normalen[arraypos++] = möglicheNormalen[3 * s + 2];
			}
		}
		bind(vboColors, normalen, 1);
		
		
		
		
		
		
		
		vaoTetra = glGenVertexArrays();
		glBindVertexArray(vaoTetra);
		int vboVertices = glGenBuffers();
		tetraVertices = og.generateTetra(6);
		bind (vboVertices, tetraVertices, 0);
		
		
		
		
		
		

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
//		glEnable(GL_CULL_FACE); // backface culling aktivieren
		
		glUseProgram(shaderProgram.getId());
		int uniformProjectionMatrixID = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
		glUniformMatrix4fv(uniformProjectionMatrixID, false, new Matrix4(1f, 100F).getValuesAsArray());
		
		glUseProgram(shaderProgramKugel.getId());
		uniformProjectionMatrixID = glGetUniformLocation(shaderProgramKugel.getId(), "projectionMatrix");
		glUniformMatrix4fv(uniformProjectionMatrixID, false, new Matrix4(1f, 100F).getValuesAsArray());
		
		viewMatrix = new Matrix4().translate(-1, 0, -2.5f);
		viewMatrixSec = new Matrix4().translate(1.5f, 0, -3.5f);
	}

	@Override
	public void update() {
		if (counter < 45)
			modelMatrix.rotateX(0.03f);
		else
			modelMatrix.rotateY(0.03f);
		counter = (counter + 1) % 90;

		if (counter2 < 16)
			modelMatrix.scale(1.02f);
		else
			modelMatrix.scale(1 / 1.02f);
		counter2 = (counter2 + 1) % 32;

		transformationMatrix = new Matrix4(modelMatrix);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
		draw(vaold, shaderProgram, transformationMatrix, viewMatrix, 36);
		
		draw(vaoTetra, shaderProgramKugel, transformationMatrix, viewMatrixSec, tetraVertices.length/2);
	}

	@Override
	public void destroy() {
	}
	
	public void bind(int vbo, float[] vertices, int loc ) {

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glVertexAttribPointer(loc, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(loc);
	}

	public void draw(int vao, ShaderProgram shader, Matrix4 trans, Matrix4 view, int vertices ) {
		glBindVertexArray(vao);
		glUseProgram(shader.getId());
		float[] matrixFloatArray = trans.getValuesAsArray();
		int loc = glGetUniformLocation(shader.getId(), "modelMatrix");
		glUniformMatrix4fv(loc, false, matrixFloatArray);
		int locView = glGetUniformLocation(shader.getId(), "viewMatrix");
		glUniformMatrix4fv(locView, false, view.getValuesAsArray());
		// VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, vertices);
	}


}
