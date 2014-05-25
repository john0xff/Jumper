package com.phoenixjcam.application.player;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * 
 * @author Bart Bien
 *
 */
public class Player {
    /** player position x, y */
    private Point2D.Double position;

    /** player next position x, y */
    private Point2D.Double nextPosition;

    /** player size as x - width and y - height */
    private Point size;

    private Physics physics;

    private Direcrion direcrion;

    /**
     * Point size - size of player, x = width, y = height <br>
     * Point2D.Double position - first position of player at the map
     * 
     * @param Point
     *            size
     * @param Point2D
     *            .Double position
     */
    public Player(Point size, Point2D.Double position) {
	this.size = size;
	this.position = position;
	physics = new Physics();
	direcrion = new Direcrion();

	nextPosition = new Point2D.Double(0.0, 0.0);

    }

    public Point2D.Double getPosition() {
	return position;
    }

    /**
     * first position of player at the map
     * 
     * @param position
     */
    public void setPosition(Point2D.Double position) {
	this.position = position;
    }

    public Point2D.Double getNextPosition() {
	return nextPosition;
    }

    public void setNextPosition(Point2D.Double nextPosition) {
	this.nextPosition = nextPosition;
    }

    public boolean isTurnedLeft() {
	return direcrion.turnedLeft;
    }

    public void setTurnedLeft(boolean turnedLeft) {
	direcrion.turnedLeft = turnedLeft;
    }

    public boolean isTurnedRight() {
	return direcrion.turnedRight;
    }

    public void setTurnedRight(boolean turnedRight) {
	direcrion.turnedRight = turnedRight;
    }

    public boolean isJumping() {
	return direcrion.jumping;
    }

    public void setJumping(boolean jumping) {
	direcrion.jumping = jumping;
    }

    /**
     * When falling is done, set jumping.
     */
    public void setJumping() {
	if (!direcrion.falling) {
	    direcrion.jumping = true;
	}
    }

    public boolean isFalling() {
	return direcrion.falling;
    }

    public void setFalling(boolean falling) {
	direcrion.falling = falling;
    }

    public int getWidth() {
	return size.x;
    }

    public void setWidth(int width) {
	this.size.x = width;
    }

    public int getHeight() {
	return size.y;
    }

    public void setHeight(int height) {
	this.size.y = height;
    }

    public double getMoveSpeed() {
	return physics.moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed) {
	physics.moveSpeed = moveSpeed;
    }

    public double getFallingSpeed() {
	return physics.fallingSpeed;
    }

    public void setFallingSpeed(double fallingSpeed) {
	physics.fallingSpeed = fallingSpeed;
    }

    public double getJumpVector() {
	return physics.jumpVector;
    }

    public void setJumpVector(double jumpStart) {
	physics.jumpVector = jumpStart;
    }

    public void increaseSpeed() {
	if (physics.moveSpeed > 10)
	    return;

	physics.moveSpeed += 1;
    }

    public void decreaseSpeed() {
	if (physics.moveSpeed < 2)
	    return;
	physics.moveSpeed -= 1;
    }
}
