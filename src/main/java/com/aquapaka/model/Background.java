package com.aquapaka.model;

import com.aquapaka.FlappyBoat;

public class Background extends Entity {
    private static final String imageUrl = "/image/Background.png";

    public Background() {
        super(imageUrl, 6);

        // Center background image
        setX(-(getImage().getWidth() - FlappyBoat.WINDOW_WIDTH) / 2);
        setY(-(getImage().getHeight() - FlappyBoat.WINDOW_HEIGHT) / 2);
    }

    @Override
    public void update() {
        super.update();
    }
}
