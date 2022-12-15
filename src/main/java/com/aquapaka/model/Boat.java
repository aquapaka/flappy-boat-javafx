package com.aquapaka.model;

import javafx.scene.layout.Pane;

public class Boat extends Entity {
    private static final String imageUrl = "/image/Player_Boat.png";
    private static final double JUMP_POWER = 6;

    public Boat(double positionX, double positionY, double velocityX, double velocityY, double accelerationX, double accelerationY) {
        super(imageUrl, 6, positionX, positionY, velocityX, velocityY, accelerationX, accelerationY);
    }

    @Override
    public void update() {
        super.update();

        // Rotate when dead
        if(isDead()) {
            setRotate(getRotate() - 1);
        }

    }

    public void jump() {
        setVelocityY(getVelocityY() - JUMP_POWER);
    }

    public void shoot(Pane gamePane) {
        gamePane.getChildren().add(new Bullet(this, getX() + 50, getY(), 5, 0, 0, 0));
    }
}
