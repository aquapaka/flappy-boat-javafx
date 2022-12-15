package com.aquapaka.model;

public class Boat extends Entity {
    private static final String imageUrl = "/image/Player_Boat.png";
    private static final double JUMP_POWER = 7;
    private boolean isDead = false;

    public Boat(double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        super(imageUrl, 6, positionX, positionY, velocityX, velocityY, accelerationX, accelerationY);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public void update() {
        super.update();

        // Fix velocityY if player boat about to jump out of screen
        if(getY() < 0) setVelocityY(1);

        // Rotate when dead
        if(isDead) {
            setRotate(getRotate() - 1);
        }

    }

    public void jump() {
        setVelocityY(getVelocityY() - JUMP_POWER);
    }
}
