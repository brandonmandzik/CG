package ab3;

//Alle Operationen �ndern das Matrixobjekt selbst und geben das eigene Matrixobjekt zur�ck
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {
	
	public float[][] m = new float[4][4];

	public Matrix4() {
		for(int i = 4; --i >= 0;) {
			m[i][i] = 1;
		}
	}

	public Matrix4(Matrix4 copy) {
		for(int i = 4; --i >= 0;) {
			for(int j = 4; --j >=0;) {
				m[j][i] = copy.m[j][i];
			}
		}
	}

	public Matrix4(float near, float far) {
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzuf�gen
		m[0][0]= near;
		m[1][1]= near;
		m[2][2]= (-far - near)/(far-near);
		m[3][2]= (-2*near*far)/(far-near);
		m[2][3]= -1;
	}

	public Matrix4 multiply(Matrix4 other) {

		float[][] ergebnis = new float [4][4];
		float value;
		for(int i = 0;  i<4;i++) {
			for(int j = 0;  j<4;j++) {
				value = 0.0f;
				for (int n = 0;  n<4;n++) {
					value += other.m[n][i] * m[j][n];
				} ergebnis[j][i]=value;}}
		m = ergebnis;
		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		Matrix4 trans = new Matrix4();
		trans.m[3][0] = x;
		trans.m[3][1] = y;
		trans.m[3][2] = z;
		multiply(trans);
		return this;
	}

	public Matrix4 scale(float uniformFactor) {

		Matrix4 trans = new Matrix4();
		trans.m[0][0] = uniformFactor;
		trans.m[1][1] = uniformFactor;
		trans.m[2][2] = uniformFactor;
		multiply(trans);
		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		Matrix4 trans = new Matrix4();
		trans.m[0][0] = sx;
		trans.m[1][1] = sy;
		trans.m[2][2] = sz;
		multiply(trans);
		return this;
	}

	public Matrix4 rotateX(float angle) {
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		Matrix4 trans = new Matrix4();
		trans.m[1][1] = cos;
		trans.m[2][1] = -sin;
		trans.m[1][2] = sin;
		trans.m[2][2] = cos;
		multiply(trans);
		return this;
	}

	public Matrix4 rotateY(float angle) {
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		Matrix4 trans = new Matrix4();
		trans.m[0][0] = cos;
		trans.m[2][0] = -sin;
		trans.m[0][2] = sin;
		trans.m[2][2] = cos;
		multiply(trans);
		return this;
	}
	
	
	public Matrix4 rotateZ(float angle) {
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		Matrix4 trans = new Matrix4();
		trans.m[0][0] = cos;
		trans.m[1][0] = -sin;
		trans.m[0][1] = sin;
		trans.m[1][1] = cos;
		multiply(trans);
		return this;
	}

	public float[] getValuesAsArray() {
		float[] matrixValues = new float [16];
		int pos=0;
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				matrixValues[pos]=m[i][j];
				pos++;
			}
		}
		return matrixValues;
	}
}