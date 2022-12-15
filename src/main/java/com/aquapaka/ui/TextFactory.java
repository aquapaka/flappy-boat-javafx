package com.aquapaka.ui;

import com.aquapaka.FlappyBoat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextFactory {
    public static final String FONT_PATH = FlappyBoat.class.getResource("/font/VT323-Regular.ttf").toExternalForm();

    public static Text getText(String textValue, int fontSize) {
        Text text = new Text(textValue);
        text.setFill(Color.color(1, 0.8, 0.6));
        text.setFont(Font.loadFont(FONT_PATH, fontSize));

        return text;
    }
}
