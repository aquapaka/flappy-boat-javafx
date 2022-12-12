package com.aquapaka.model;

import javafx.scene.image.Image;

public class Boat extends Entity {
    private static final String imageUrl = "/image/Player_Boat.png";
    private static final double JUMP_POWER = 6;

    public Boat(double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        super(positionX, positionY, velocityX, velocityY, accelerationX, accelerationY);
        Image image = new Image(imageUrl);
        image = new Image(imageUrl, image.getWidth() * 6, 0, true, false);
        setImage(image);
    }

    @Override
    public void update() {
        setVelocityX(getVelocityX() + getAccelerationX());
        setVelocityY(getVelocityY() + getAccelerationY());
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());

        // Fix velocityY if player boat about to jump out of screen
        if(getY() < 0) setVelocityY(0.5);
    }

    public void jump() {
        setVelocityY(getVelocityY() - JUMP_POWER);
    }
}
