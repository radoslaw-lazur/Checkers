package checkers;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

class Board {

    private Pane paneBoard = new Pane();
    private Pane paneTurnNotification = new Pane();
    private GameController gameController = new GameController(this);
    private Image imageGreen = new Image(getClass().getResource("/greenWood1.png").toString());
    private Image imageRed = new Image(getClass().getResource("/redWood1.png").toString());
    private Image imageWinnerLabel = new Image(getClass().getResource("/winnerLabel.png").toString());
    private Rectangle rectangleRed = new Rectangle(-35, 0, 50, 50);
    private Rectangle rectangleGreen = new Rectangle(-35, 750, 50, 50);
    private Rectangle rectangleWinner = new Rectangle(25, 350, 100,100);
    private Rectangle rectangleWinnerLabel = new Rectangle(50, 375, 50, 50);
    private List<Integer> playerRedWins = new ArrayList<>();
    private List<Integer> playerGreenWins = new ArrayList<>();
    private int tmpNumberRed = 0;
    private int tmpNumberGreen = 0;



    void createStartingBoard() {
        Rectangle frame = new Rectangle(-1, -1, 802, 802);
        paneBoard.getChildren().add(frame);
        rectangleGreen.setFill(new ImagePattern(imageGreen));
        rectangleRed.setFill(Color.TRANSPARENT);
        rectangleWinner.setFill(Color.TRANSPARENT);
        rectangleWinnerLabel.setFill(Color.TRANSPARENT);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Field field = new Field(y, x, gameController);
                paneBoard.getChildren().add(field);
            }
        }
    }

    void setTurnNotification(boolean turnNotification) {
        if (turnNotification) {
            rectangleRed.setFill(new ImagePattern(imageRed));
            rectangleGreen.setFill(Color.TRANSPARENT);
        } else {
            rectangleGreen.setFill(new ImagePattern(imageGreen));
            rectangleRed.setFill(Color.TRANSPARENT);
        }
    }

    void setRedWinner(boolean redWinner) {
        System.out.println("The winner is red: " + redWinner);
        if (redWinner) {
            playerRedWins.add(tmpNumberRed);
            tmpNumberRed += 1;
            rectangleWinner.setFill(Color.valueOf("#860000"));
            rectangleWinnerLabel.setFill(new ImagePattern(imageWinnerLabel));
        }
    }

    void setGreenWinner(boolean greenWinner) {
        System.out.println("The winner is green: " + greenWinner);
        if (greenWinner) {
            playerGreenWins.add(tmpNumberGreen);
            tmpNumberGreen += 1;
            rectangleWinner.setFill(Color.valueOf("#2B7672"));
            rectangleWinnerLabel.setFill(new ImagePattern(imageWinnerLabel));
        }
    }

    Pane getPaneBoard() {
        return paneBoard;
    }

    Pane getPaneTurnNotification() {
        paneTurnNotification.getChildren().addAll(rectangleRed, rectangleGreen, rectangleWinner, rectangleWinnerLabel);
        return paneTurnNotification;
    }
}