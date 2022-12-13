package com.aquapaka.model;

public class Boat extends Entity {
    private static final String imageUrl = "/image/Player_Boat.png";
    private static final double JUMP_POWER = 6;

    public Boat(double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        super(imageUrl, 6, positionX, positionY, velocityX, velocityY, accelerationX, accelerationY);
    }

    @Override
    public void update() {
        super.update();

        // Fix velocityY if player boat about to jump out of screen
        if(getY() < 0) setVelocityY(0.5);

    }

    public void jump() {
        setVelocityY(getVelocityY() - JUMP_POWER);
    }
}
