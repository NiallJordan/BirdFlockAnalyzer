package Group;

/**
 * This class obtains the coordinates of the bird, which is then used
 * for the printing of the rectangles on to the image.
 * 
 * @author Niall Jordan
 *
 */
public class BirdGroups {

	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	public BirdGroups(int x1, int y1, int x2, int y2) {
		if(x1 < x2) {
			this.setX1(x1);
			this.x2 = x2;
		}else {
			this.setX1(x2);
			this.x2 = x1;
		}
		
		if(y1 < y2) {
			this.setY1(y1);
			this.setY2(y2);
		}else {
			this.setY1(y2);
			this.setY2(y1);
		}
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	 
	@Override
	public String toString() {
		return "BirdGrouping [x1=" + x1 + ", x2=" + x2 + ", y1=" + y1 + ", y2=" + y2 
				+ ",getX1()="+ getX1()  + ",getY1()="+ getY1() + ",getX2()="+ getX2() 
				+  ",getY2()=" + getY2() +"]";
	}	
}