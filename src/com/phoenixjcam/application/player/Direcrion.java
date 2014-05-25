package com.phoenixjcam.application.player;

/**
 * fields that specify player movement directions
 * 
 * @author Bart Bien
 * 
 */
public class Direcrion {
    /** player is turned to left - x axis */
    public boolean turnedLeft;

    /** player is turned to right - x axis */
    public boolean turnedRight;

    /** player is jumping - y axis */
    public boolean jumping;

    /** player is falling - y axis */
    public boolean falling;

    public Direcrion() {
    }
}