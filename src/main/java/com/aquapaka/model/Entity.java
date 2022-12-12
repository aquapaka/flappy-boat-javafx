package com.aquapaka.model;

import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {
    private double velocityX;
    private double velocityY;
    private double accelerationX;
    private double accelerationY;

    public Entity(double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        setX(positionX);
        setY(positionY);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
    }

    public abstract void update();

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
