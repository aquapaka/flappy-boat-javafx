package com.aquapaka.model;

public class Enemy extends Entity{
    private static final String imageUrl = "/image/Enemy.png";

    public Enemy(double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        super(imageUrl, 6, positionX, positionY, velocityX, velocityY, accelerationX, accelerationY);
    }
}
