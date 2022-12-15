package com.aquapaka.model;

public class Bullet extends Entity{
    private static final String imageUrl = "/image/Bullet.png";
    private Entity owner;

    public Bullet(Entity owner, double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        super(imageUrl, 6, positionX, positionY, velocityX, velocityY, accelerationX, accelerationY);
        this.owner = owner;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }
}
