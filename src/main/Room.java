package main;
import processing.core.PApplet;

public class Room {
	public int x, y;
	public static final float w = 50;
	public boolean topEntrance;
	public boolean bottomEntrance;
	public boolean leftEntrance;
	public boolean rightEntrance;

	public Room(int x, int y, boolean topEntrance, boolean bottomEntrance, boolean leftEntrance,
			boolean rightEntrance) {
		super();
		this.x = x;
		this.y = y;
		this.topEntrance = topEntrance;
		this.bottomEntrance = bottomEntrance;
		this.leftEntrance = leftEntrance;
		this.rightEntrance = rightEntrance;
	}

	public void draw(PApplet p, int scale) {
		if (x * scale == 0) {
			leftEntrance = false;
		}
		if (x * scale == 500) {
			rightEntrance = false;
		}
		if (y * scale == 0) {
			topEntrance = false;
		}
		if (y * scale == 500) {
			bottomEntrance = false;
		}
		
			p.rect(x * scale, y * scale, w, w);

			//if (bottomEntrance) {

			//	p.rect(x * scale + w / 4, y * scale + w, w / 2, scale - w);
			//}
			//if (topEntrance) {

			//	p.rect(x * scale + w / 4, y * scale - (scale - w), w / 2, scale - w);
			//}
			//if (leftEntrance) {
			//	p.rect(x * scale - (scale - w), y * scale + w / 4, scale - w, w / 2);
			//}
			//if (rightEntrance) {
			//	p.rect(x * scale + w, y * scale + w / 4, scale - w, w / 2);
			//}
		
	}

}
