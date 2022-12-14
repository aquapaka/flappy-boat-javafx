package com.aquapaka.scene;

import com.aquapaka.FlappyBoat;
import com.aquapaka.model.Background;
import com.aquapaka.state.GameState;
import com.aquapaka.ui.TextFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuScene extends Scene {
    private final Stage stage;
    private final GameScene gameScene;

    public MenuScene(Stage stage, GameScene gameScene, double v, double v1) {
        super(new StackPane(), v, v1);
        this.stage = stage;
        this.gameScene = gameScene;
        initMenuPane();
    }

    private void initMenuPane() {
        StackPane menuPane = (StackPane) getRoot();
        menuPane.setMinWidth(FlappyBoat.WINDOW_WIDTH);
        menuPane.setMinHeight(FlappyBoat.WINDOW_HEIGHT);

        // Create background
        ImageView background = new Background();

        // Create menu items
        Text titleText = TextFactory.getText("Flappy Boat", 128);
        titleText.setTranslateY(-200);
        Text startInstructionText = TextFactory.getText("Press ENTER to start game", 48);
        startInstructionText.setTranslateY(50);

        for (Node node : menuPane.getChildren()) {
            if(node instanceof Text) {
                ((Text) node).setTextAlignment(TextAlignment.CENTER);
                menuPane.setAlignment(Pos.CENTER);
            }
        }

        menuPane.getChildren().addAll(background, titleText, startInstructionText);


        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    stage.setScene(gameScene);
                    FlappyBoat.gameState = GameState.WAITING_FOR_START;
                    break;
                }
            }
        });
    }
}
