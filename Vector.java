package ab3;

public class Vector {
public float x,y,z;
	public Vector (float x,float y,float z) {this.x=x; this.y =y ; this.z = z;}
	
	public Vector middle (Vector v) {
		Vector temp = new Vector ((x+v.x)/2, (y+v.y)/2, (z+v.z)/2);
		return temp;
	}
	
	public void norm() {
		float length = (float) Math.sqrt(x*x + y*y + z*z);
		x = x/length;
		y = y/length;
		z = z/length;
	}
}
