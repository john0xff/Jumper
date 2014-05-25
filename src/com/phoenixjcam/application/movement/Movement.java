package com.phoenixjcam.application.movement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.phoenixjcam.application.map.GameMap;
import com.phoenixjcam.application.player.Player;

/**
 * Calculate all collisions and movements.
 * 
 * @author Bart Bien
 * 
 */
public class Movement {
    private GameMap map;
    int coulmnTile;
    int rowTile;

    /** flag specify if player has obstacle at top left position */
    private boolean topLeft;

    /** flag specify if player has obstacle at top right position */
    private boolean topRight;

    /** flag specify if player has obstacle at bottom left position */
    private boolean bottomLeft;

    /** flag specify if player has obstacle at bottom right position */
    private boolean bottomRight;

    private Player player;

    /** player new position */
    private Point2D.Double newPosition;

    /** player position */
    private Point2D.Double position;

    /** player next position */
    private Point2D.Double nextPosition;

    /** player movement collision */
    private Point2D.Double collision;

    public Movement(GameMap map, Player player) {
	this.map = map;
	this.player = player;
	newPosition = new Point2D.Double();

	collision = new Point2D.Double();
    }

    /**
     * specify new player position <br>
     * movement: left, right, stopped, jumping, falling
     */
    public void nextPosition() {
	position = this.player.getPosition();
	nextPosition = this.player.getNextPosition();

	// player is turned left
	if (player.isTurnedLeft()) {
	    nextPosition.x = -player.getMoveSpeed();
	}
	// player is turned right
	else if (player.isTurnedRight()) {
	    nextPosition.x = player.getMoveSpeed();
	}
	// player stopped movement - nextPosition must be 0
	else {
	    // player has stopped from right movement
	    if (nextPosition.x > 0) {
		nextPosition.x = 0;
	    }
	    // player has stopped from left movement
	    else if (nextPosition.x < 0) {
		nextPosition.x = 0;
	    }
	}

	// player jumping
	if (player.isJumping()) {
	    nextPosition.y = player.getJumpVector();
	    player.setFalling(true);
	    player.setJumping(false);
	}

	// player falling
	else if (player.isFalling()) {
	    nextPosition.y += 0.8;
	}
    }

    /**
     * calculate collision for given coordinates <br>
     * 0 = wall, 1 = space <br>
     * by collision means all obstacles at player way in every directions
     * 
     * @param x
     * @param y
     */
    private void calculateCollision(double x, double y) {
	// center of player size
	int centerSizeX = player.getWidth() / 2;
	int centerSizeY = player.getHeight() / 2;

	// get player nearest tiles
	int leftTile = map.getColumnTile((int) (x - centerSizeX));
	int rightTile = map.getColumnTile((int) (x + centerSizeX) - 1);
	int topTile = map.getRowTile((int) (y - centerSizeY));
	int bottomTile = map.getRowTile((int) (y + centerSizeY) - 1);

	// topLeft (0 equals wall)
	if (map.getTile(topTile, leftTile) == 0) {
	    topLeft = true;
	} else {
	    topLeft = false;
	}

	// topRight (0 equals wall)
	if (map.getTile(topTile, rightTile) == 0) {
	    topRight = true;
	} else {
	    topRight = false;
	}

	// bottomLeft (0 equals wall)
	if (map.getTile(bottomTile, leftTile) == 0) {
	    bottomLeft = true;
	} else {
	    bottomLeft = false;
	}

	// bottomRight (0 equals wall)
	if (map.getTile(bottomTile, rightTile) == 0) {
	    bottomRight = true;
	} else {
	    bottomRight = false;
	}
    }

    public void jumpCollision() {
	if (nextPosition.y < 0) {
	    if (topLeft || topRight) {
		nextPosition.y = 0;
		newPosition.y = rowTile * map.getTileSize()
			+ player.getHeight() / 2;
	    } else {
		newPosition.y += nextPosition.y;
	    }
	}
    }

    public void fallCollision() {
	if (nextPosition.y > 0) {
	    if (bottomLeft || bottomRight) {
		nextPosition.y = 0;
		player.setFalling(false);
		newPosition.y = (rowTile + 1) * map.getTileSize()
			- player.getHeight() / 2;
	    } else {
		newPosition.y += nextPosition.y;
	    }
	}
    }

    public void leftCollision() {
	if (nextPosition.x < 0) {
	    if (topLeft || bottomLeft) {
		nextPosition.x = 0;
		newPosition.x = coulmnTile * map.getTileSize()
			+ player.getWidth() / 2;
	    } else {
		newPosition.x += nextPosition.x;
	    }
	}
    }

    public void rightCollision() {
	if (nextPosition.x > 0) {
	    if (topRight || bottomRight) {
		nextPosition.x = 0;
		newPosition.x = (coulmnTile + 1) * map.getTileSize()
			- player.getWidth() / 2;
	    } else {
		newPosition.x += nextPosition.x;
	    }
	}
    }

    /** if player isn't falling and doesn't have a floor set falling */
    public void lackFloor() {
	if (!player.isFalling()) {
	    calculateCollision(position.x, position.y + 1);
	    if (!bottomLeft && !bottomRight) {
		player.setFalling(true);
	    }
	}
    }

    /** calculate position and collisions */
    public void update() {
	// player next position
	nextPosition();

	// current tiles of player position
	coulmnTile = map.getColumnTile((int) position.x);
	rowTile = map.getRowTile((int) position.y);

	// player collision coordinates
	collision.x = position.x + nextPosition.x;
	collision.y = position.y + nextPosition.y;

	// newPosition - calculate player new position
	newPosition.x = position.x;
	newPosition.y = position.y;

	// y axis for jumping and falling
	calculateCollision(position.x, collision.y);

	// jumping collision
	jumpCollision();
	// falling collision
	fallCollision();

	// x axis for left and right movement
	calculateCollision(collision.x, position.y);
	// left collision
	leftCollision();
	// right collision
	rightCollision();

	// if player isn't falling and doesn't have a floor set falling
	lackFloor();

	// update player position
	position.x = newPosition.x;
	position.y = newPosition.y;
    }

    public void draw(Graphics2D g) {
	// player color
	g.setColor(Color.RED);

	int width = player.getWidth();
	int height = player.getHeight();

	// left corner of player (square)
	int x = (int) (position.x - width / 2);
	int y = (int) (position.y - height / 2);

	g.fillRect(x, y, width, height);
    }
}
