package com.aquapaka.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {
    private double velocityX;
    private double velocityY;
    private double accelerationX;
    private double accelerationY;

    public Entity(String imageUrl, int imageScale) {
        Image image = new Image(imageUrl);
        image = new Image(imageUrl, image.getWidth() * imageScale, 0, true, false);
        setImage(image);
    }

    public Entity(String imageUrl, int imageScale, double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        this(imageUrl, imageScale);
        setX(positionX);
        setY(positionY);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
    }

    public void update() {
        setVelocityX(getVelocityX() + getAccelerationX());
        setVelocityY(getVelocityY() + getAccelerationY());
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());
    }


    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }
}
