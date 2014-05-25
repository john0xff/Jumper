package com.phoenixjcam.application.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 
 * map is design in txt file as below example<br>
 * 0 0 0 0 0 <br>
 * 0 1 1 1 0 <br>
 * 0 0 0 0 0 <br>
 * 0 = walls and floors<br>
 * 1 = space to game <br>
 * 
 * @author Bart Bien
 */
public class GameMap {
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    private Tiles tiles;
    private Point camera;

    public GameMap(String s, int tilesSize) {
	tiles = new Tiles();
	tiles.position = new Point();
	tiles.size = tilesSize;

	camera = new Point();

	try {
	    BufferedReader bfReader = new BufferedReader(new FileReader(s));

	    // first and second line of map from txt file
	    mapWidth = Integer.parseInt(bfReader.readLine());
	    mapHeight = Integer.parseInt(bfReader.readLine());
	    map = new int[mapHeight][mapWidth];

	    String format = " ";

	    for (int row = 0; row < mapHeight; row++) {
		// read entire line from txt file
		String line = bfReader.readLine();

		// split line and put "," instead " " between each string
		String[] formatedLine = line.split(format);

		for (int column = 0; column < mapWidth; column++) {
		    map[row][column] = Integer.parseInt(formatedLine[column]);
		}
	    }
	    bfReader.close();
	} catch (Exception e) {
	}
    }

    public Tiles getTiles() {
	return tiles;
    }

    public void setTiles(Tiles tiles) {
	this.tiles = tiles;
    }

    public int getColumnTile(int x) {
	return x / tiles.size;
    }

    public int getRowTile(int y) {
	return y / tiles.size;
    }

    /**
     * map is design in txt file as below example<br>
     * 0 0 0 0 0 <br>
     * 0 1 1 1 0 <br>
     * 0 0 0 0 0 <br>
     * 0 = walls and floors<br>
     * 1 = space to game <br>
     */
    public int getTile(int row, int column) {
	return map[row][column];
    }

    public int getTileSize() {
	return tiles.size;
    }

    public Point getCamera() {
	return camera;
    }

    public void setCamera(Point camera) {
	this.camera = camera;
    }

    /** draw map with specify tile size (squares) */
    public void draw(Graphics2D g) {
	for (int row = 0; row < mapHeight; row++) {
	    for (int column = 0; column < mapWidth; column++) {
		int currentTile = map[row][column];

		if (currentTile == 0) {
		    g.setColor(Color.BLACK);
		}
		if (currentTile == 1) {
		    g.setColor(Color.WHITE);
		}

		tiles.position.x = column * tiles.size;
		tiles.position.y = row * tiles.size;

		g.fillRect(tiles.position.x, tiles.position.y, tiles.size,
			tiles.size);
	    }
	}
    }

    /** specify units of map */
    private class Tiles {
	/** 2D position of left corner x, y */
	private Point position;
	/** size of one tile - square (width = size, heigth = size) */
	private int size;
    }
}
