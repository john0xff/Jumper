package com.phoenixjcam.application.player;

/**
 * fields that specify player physics
 * 
 * @author Bart Bien
 * 
 */
public class Physics {
    /** player movement speed - x axis */
    public double moveSpeed;

    /** player jump vector has to minus - y axis */
    public double jumpVector;

    /** player is turned to left - y axis */
    public double fallingSpeed;

    public Physics() {
	moveSpeed = 5.0;
	jumpVector = -14.0;
	fallingSpeed = 5.0;
    }
}