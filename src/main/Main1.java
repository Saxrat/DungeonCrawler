package main;

import processing.core.PApplet;

public class Main1 extends PApplet {
	public float x, y, z;
	public float fov;
	public float cameraz, cameray, camerax;
	public static final float distance = 20;
	public double r;
	public static final float SPEED = 3;
	public static final float HEIGHT = 20;
	public static final int ARRAYSIZE = 20;


	int[][] floor = new int[ARRAYSIZE][ARRAYSIZE];

	public void settings() {
		size(600, 600, P3D);
	}

	public void setup() {
		x = 0;
		y = 0;
		z = 0;
		camerax = 0;
		cameray = 0;

		for (int i = 0; i < ARRAYSIZE; i++) {
			for (int j = 0; j < ARRAYSIZE; j++) {
				if (j == 0 || j == ARRAYSIZE - 1) {
					floor[i][j] = 1;
				} else if (i == 0 || i == ARRAYSIZE - 1) {
					floor[i][j] = 1;
				} else {
					floor[i][j] = (int) (Math.random() * 3);
				}
			}
		}
		for (int i = 0; i < ARRAYSIZE; i++) {
			for (int j = 0; j < ARRAYSIZE; j++) {
				if (floor[i][j] == 0 || floor[i][j] == 2) {
					x = 100 * i;
					z = 100 * j;
				}
			}
		}

		fov = (float) (Math.PI / 3);
		rectMode(CENTER);
		background(255);
		fill(51);
	}

	public void draw() {
		background(255);

		r = map(mouseX, 0, 600, 0, (float) Math.PI * 2);
		camera((float) (x - (distance * Math.cos(r))), HEIGHT, (float) (z - (distance * Math.sin(r))), x, HEIGHT, z, 0,
				1, 0);

		box(100);

		for (int i = 0; i < ARRAYSIZE; i++) {
			for (int j = 0; j < ARRAYSIZE; j++) {
				if (floor[i][j] == 1) {
					translate(i * 100, 0, j * 100);
					box(100);
					translate(-i * 100, 0, -j * 100);
				}
			}
		}

		if (keyPressed) {
			float tempZ = 0;
			float tempX = 0;
			if (keyCode == UP) {
				tempZ = (float) (z + SPEED * Math.sin(r));
				tempX = (float) (x + SPEED * Math.cos(r));

			}
			if (keyCode == DOWN) {

				tempZ = (float) (z - SPEED * Math.sin(r));
				tempX = (float) (x - SPEED * Math.cos(r));

			}
			if (keyCode == LEFT) {

				tempX = (float) (x + SPEED * Math.cos(r - (Math.PI / 2)));
				tempZ = (float) (z + SPEED * Math.sin(r - (Math.PI / 2)));

			}
			if (keyCode == RIGHT) {

				tempX = (float) (x + SPEED * Math.cos(r + (Math.PI / 2)));
				tempZ = (float) (z + SPEED * Math.sin(r + (Math.PI / 2)));

			}

			for (int i = 0; i < ARRAYSIZE; i++) {
				for (int j = 0; j < ARRAYSIZE; j++) {
					if (floor[i][j] == 1) {
						if (tempX >= 100 * i - (52 + (distance * Math.cos(r)))
								&& tempX <= 100 * i + (52 + (distance * Math.cos(r)))
								&& tempZ >= 100 * j - (52 + (distance * Math.sin(r)))
								&& tempZ <= 100 * j + 52 + (distance * Math.sin(r))) {
							return;
						}
					}
				}
			}
			x = tempX;
			z = tempZ;
		}

	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "main.Main" });
	}
}
