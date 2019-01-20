package ab3;

public class ObjectGenerator {
	float[] vertices; int pos;
	
	public ObjectGenerator() {
	}

	public float[] generateTetra(int level) {
		pos=0;
		Vector middle = new Vector(0, 0, 0);
		float fak = (float) Math.sqrt(2)/2;
		Vector a = new Vector(0, fak, -1);
		Vector b = new Vector(0, fak, 1);
		Vector c = new Vector(-1, -fak, 0);
		Vector d = new Vector(1, -fak, 0);
		a.norm(); b.norm(); c.norm(); d.norm();
		
		vertices = new float[(int) (36 * Math.pow(4, level-1))];
		
		dreieck(a,b,c, level); 
		dreieck(c,b,d, level);
		dreieck(b,a,d, level);
		dreieck(d,a,c, level);
		
		return vertices;

	}


	void dreieck (Vector a, Vector b, Vector c, int level ) {
		if (level == 1) {
			vertices[pos++] = a.x; vertices[pos++] = a.y; vertices[pos++] = a.z;
			vertices[pos++] = b.x; vertices[pos++] = b.y; vertices[pos++] = b.z;
			vertices[pos++] = c.x; vertices[pos++] = c.y; vertices[pos++] = c.z;
		}
		else {
			Vector ba = b.middle(a), ac = a.middle(c), cb = c.middle(b);
			ba.norm(); ac.norm(); cb.norm();
			dreieck (a,ac,ba,level-1);
			dreieck (ac,c,cb,level-1);
			dreieck (ba,cb,b,level-1);
			dreieck (ac,cb,ba,level-1);
		}
	}
}
