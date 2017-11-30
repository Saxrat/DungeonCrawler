package main;

import java.util.ArrayList;

import items.Item;
import items.TrainingSword;
import processing.core.PApplet;
import processing.core.PConstants;

public class Main extends PApplet {
	public float x, y, z;
	public float fov;
	public float cameraz, cameray, camerax;
	public static final float distance = 20;
	public double r;
	public static final float SPEED = 3;
	public static final float HEIGHT = 20;
	public static final int ARRAYSIZE = 20;
	public int testX, testZ;
	public int mode = 0;
	public float doorX, doorZ;

	int[][] floor = new int[ARRAYSIZE][ARRAYSIZE];

	public ArrayList<Item> inventory = new ArrayList<Item>();

	public void settings() {
		size(600, 600, P3D);
		fullScreen();
	}

	public void setup() {
		generateNewLevel();
		inventory.add(new TrainingSword());
	}

	public void placePlayer() {
		for (int i = 0; i < ARRAYSIZE; i++) {
			for (int j = 0; j < ARRAYSIZE; j++) {
				if (floor[i][j] == 1) {
					x = i * 100;
					z = j * 100;
					return;
				}
			}
		}
	}

	public void draw() {
		if (mode == 1) {
			
		}
		if (mode == 0) {
			background(255);

			r = map(mouseX, 0, 600, 0, (float) Math.PI * 2);
			camera((float) (x - (distance * Math.cos(r))), HEIGHT, (float) (z - (distance * Math.sin(r))), x, HEIGHT, z,
					0, 1, 0);

			box(100);

			for (int i = 0; i < ARRAYSIZE; i++) {
				for (int j = 0; j < ARRAYSIZE; j++) {
					if (floor[i][j] == 0) {
						translate(i * 100, 0, j * 100);
						box(100);
						translate(-i * 100, 0, -j * 100);
					}
					if (floor[i][j] == 3) {
						translate(i * 100, 45, j * 100);
						fill(100);
						box(10);
						translate(-i * 100, -45, -j * 100);
						translate(i * 100, (float) 47.5, j * 100);
						fill(0);
						box(5);
						fill(51);
						translate(-i * 100, -47.5f, -j * 100);
					}
				}
			}

			if (keyPressed) {
				float tempZ = 0;
				float tempX = 0;

				if (keyCode == UP || key == 'w') {
					tempZ = (float) (z + SPEED * Math.sin(r));
					tempX = (float) (x + SPEED * Math.cos(r));

				}
				if (keyCode == DOWN || key == 's') {

					tempZ = (float) (z - SPEED * Math.sin(r));
					tempX = (float) (x - SPEED * Math.cos(r));

				}
				if (keyCode == LEFT || key == 'a') {

					tempX = (float) (x + SPEED * Math.cos(r - (Math.PI / 2)));
					tempZ = (float) (z + SPEED * Math.sin(r - (Math.PI / 2)));

				}
				if (keyCode == RIGHT || key == 'd') {

					tempX = (float) (x + SPEED * Math.cos(r + (Math.PI / 2)));
					tempZ = (float) (z + SPEED * Math.sin(r + (Math.PI / 2)));

				}
				// Checks if character is in ladder room, and if so, generates lower dungeon
				if (key == 'q') {
					if (checkDoor()) {
						generateNewLevel();
					}
					return;
				}
				// Checks if player's step will take them into a wall and returns if they will
				for (int i = 0; i < ARRAYSIZE; i++) {
					for (int j = 0; j < ARRAYSIZE; j++) {
						if (floor[i][j] == 0) {
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
	}

	public void fillFloor(int x, int z, int size, int value) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (x + i < floor.length && z + j < floor[0].length) {
					if (x + i > 0 && z + j > 0) {
						floor[x + i][z + j] = value;
					}
				}
			}
		}
	}

	public void resetFloor() {
		for (int i = 0; i < ARRAYSIZE; i++) {
			for (int j = 0; j < ARRAYSIZE; j++) {
				floor[i][j] = 0;
			}
		}
	}

	public void generateNewLevel() {
		testX = 0;
		testZ = 0;
		resetFloor();
		for (int a = 0; a < 30; a++) {
			testX = (int) (Math.random() * 17 + 1);
			testZ = (int) (Math.random() * 17 + 1);
			fillFloor(testX, testZ, 3, 1);
		}
		for (int i = 0; i < ARRAYSIZE; i++) {
			for (int j = 0; j < ARRAYSIZE; j++) {
				if (j == 0 || j == ARRAYSIZE - 1) {
					floor[i][j] = 0;
				} else if (i == 0 || i == ARRAYSIZE - 1) {
					floor[i][j] = 0;
				} else if (i == ARRAYSIZE - 2 && j == ARRAYSIZE - 2) {
					floor[i][j] = 3;
					doorX = i * 100;
					doorZ = j * 100;
				}
			}
		}

		fov = (float) (Math.PI / 3);
		rectMode(CENTER);
		background(255);
		fill(51);
		placePlayer();

		boolean path = .5 < (Math.random());
		for (int a = 0; a < ARRAYSIZE - (x / 100 + 2); a++) {
			for (int b = 1; b < ARRAYSIZE - (z / 100 + 2); b++) {
				if (path = true) {
					floor[(int) (x / 100 + a)][(int) (z / 100)] = 1;
					floor[ARRAYSIZE - 2][(int) (z / 100 + b)] = 1;
				} else {
					floor[(int) (x / 100 + a)][ARRAYSIZE - 2] = 1;
					floor[(int) (x / 100)][(int) (z / 100 + b)] = 1;
				}
			}
		}

	}

	public boolean checkDoor() {
		boolean doorcheck = false;
		if (x >= doorX - (52 + (distance * Math.cos(r))) && x <= doorX + (52 + (distance * Math.cos(r)))
				&& z >= doorZ - (52 + (distance * Math.sin(r))) && z <= doorZ + 52 + (distance * Math.sin(r))) {
			doorcheck = true;

		}
		return doorcheck;
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "main.Main" });
	}

	public void keyReleased() {
		if (key == 'e') {
			if (mode == 0) {
				mode = 1;
			} else {
				mode = 0;
			}
		}
	}

	
}
