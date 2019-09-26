package checkers;

import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static double tmpX;
    private static double tmpY;
    static final Color BLACK_COLOR = Color.valueOf("#555555");
    private static final Color WHITE_COLOR = Color.WHITE;
    private static Set<Coordinates> playerRedFields = createPlayerOneFieds();
    private static Set<Coordinates> playerGreenFields = createPlayerTwoFields();
    private static List<Coordinates> fieldsToNotify = createfieldsToNotify();
    private static List<Coordinates> playerRedQueens = createRedQueenList();
    private static List<Coordinates> playerGreenQueens = createGreenQueenList();

    private static Set<Coordinates> createPlayerOneFieds() {
        Set<Coordinates> coordinates = new HashSet<>();
        coordinates.add(new Coordinates(1, 0));
        coordinates.add(new Coordinates(3, 0));
        coordinates.add(new Coordinates(5, 0));
        coordinates.add(new Coordinates(7, 0));
        coordinates.add(new Coordinates(0, 1));
        coordinates.add(new Coordinates(2, 1));
        coordinates.add(new Coordinates(4, 1));
        coordinates.add(new Coordinates(6, 1));
        coordinates.add(new Coordinates(1, 2));
        coordinates.add(new Coordinates(3, 2));
        coordinates.add(new Coordinates(5, 2));
        coordinates.add(new Coordinates(7, 2));
        return coordinates;
    }

    private static Set<Coordinates> createPlayerTwoFields() {
        Set<Coordinates> coordinates = new HashSet<>();
        coordinates.add(new Coordinates(0, 5));
        coordinates.add(new Coordinates(2, 5));
        coordinates.add(new Coordinates(4, 5));
        coordinates.add(new Coordinates(6, 5));
        coordinates.add(new Coordinates(1, 6));
        coordinates.add(new Coordinates(3, 6));
        coordinates.add(new Coordinates(5, 6));
        coordinates.add(new Coordinates(7, 6));
        coordinates.add(new Coordinates(0, 7));
        coordinates.add(new Coordinates(2, 7));
        coordinates.add(new Coordinates(4, 7));
        coordinates.add(new Coordinates(6, 7));
        return coordinates;
    }

    private static List<Coordinates> createfieldsToNotify() {
        return new ArrayList<>();
    }

    private static List<Coordinates> createRedQueenList() {
        return new ArrayList<>();
    }

    private static List<Coordinates> createGreenQueenList() {
        return new ArrayList<>();
    }

    private GameController gameController;
    double x;
    double y;
    private Color baseColor;
    private Color actualColor;
    boolean isRedTaken;
    boolean isGreenTaken;
    private Coordinates greenCoordinatesToKill;
    private Coordinates redCoordinatesToKill;
    private Coordinates checkerToVanish;
    private Coordinates checkerRedToFalse;
    private Coordinates checkerGreenToFalse;

    private Image imageGreen = new Image(getClass().getResource("/greenChecker.png").toString());
    private Image imageRed = new Image(getClass().getResource("/redChecker.png").toString());
    private Image imageQueenGreen = new Image(getClass().getResource("/queenGreen.png").toString());
    private Image imageQueenRed = new Image(getClass().getResource("/queenRed.png").toString());

    public Field() {

    }

    Field(double x, double y, GameController gameController) {
        super(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        this.gameController = gameController;

        boolean activeField = false;
        if ((y + x) % 2 == 1) {
            setFill(BLACK_COLOR);
            activeField = true;
            this.actualColor = BLACK_COLOR;
            this.baseColor = BLACK_COLOR;
        } else {
            setFill(WHITE_COLOR);
            this.actualColor = Color.WHITE;
            this.baseColor = Color.WHITE;
        }

        if (activeField) {
            setOnMouseClicked(event -> gameController.onMouseClicked(this));
        }

        this.gameController.addField(new Coordinates(x, y), this);

        if(playerRedFields.contains(new Coordinates(x, y))) {
            setFill(new ImagePattern(imageRed));
            setRedTaken(true);
        }

        if(playerGreenFields.contains(new Coordinates(x, y))) {
            setFill(new ImagePattern(imageGreen));
            setGreenTaken(true);
        }
    }


    void checkMoveRed(double x, double y) {
        System.out.println("CheckerRed clicked on: " + x + " x" +  " / " + y + " y");
        System.out.println("PlayerRedCheckers: " + playerRedFields.size());

        if (x + 1 < 8 && y + 1 <= 7) {
            fieldsToNotify.add(new Coordinates(x + 1, y + 1));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (x - 1 >= 0 && y + 1 <= 7) {
            fieldsToNotify.add(new Coordinates(x - 1, y + 1));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y + 2))
                && (x + 2 < 8)
                && (y + 2 <= 7)) {
            fieldsToNotify.add(new Coordinates(x + 2, y + 2));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y + 2))
                && (x - 2 >= 0)
                && (y + 2 <= 7)) {
            fieldsToNotify.add(new Coordinates(x - 2, y + 2));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x + 1, y + 3))
                && !playerGreenFields.contains(new Coordinates(x, y + 4))
                && !playerRedFields.contains(new Coordinates(x, y + 4))
                && (x + 2 < 8)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {
            fieldsToNotify.add(new Coordinates(x, y + 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x - 1, y + 3))
                && !playerGreenFields.contains(new Coordinates(x, y + 4))
                && !playerRedFields.contains(new Coordinates(x, y + 4))
                && (x - 2 >= 0)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {
            fieldsToNotify.add(new Coordinates(x, y + 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x + 3, y + 3))
                && !playerGreenFields.contains(new Coordinates(x + 4, y + 4))
                && !playerRedFields.contains(new Coordinates(x + 4, y + 4))
                && (x + 4 < 8)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {
            fieldsToNotify.add(new Coordinates(x + 4, y + 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x - 3, y + 3))
                && !playerGreenFields.contains(new Coordinates(x - 4, y + 4))
                && !playerRedFields.contains(new Coordinates(x - 4, y + 4))
                && (x - 4 >= 0)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {
            fieldsToNotify.add(new Coordinates(x - 4, y + 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if ((!playerRedFields.contains(new Coordinates(x + 1, y + 1))
                && !playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && (y + 1 <= 7)
                && (x + 1 < 8))) {

            tmpX = x;
            tmpY = y;

        } else if (!playerRedFields.contains(new Coordinates(x - 1, y + 1))
                && !playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && (y + 1 <= 7)
                && (x - 1 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y + 2))
                && (y + 2 <= 7)
                && (x + 2 < 8)) {

            tmpX = x;
            tmpY = y;

        } else if (playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y + 2))
                && (y + 2 <= 7)
                && (x - 2 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x + 1, y + 3))
                && !playerGreenFields.contains(new Coordinates(x, y + 4))
                && !playerRedFields.contains(new Coordinates(x, y + 4))
                && (x + 2 < 8)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {

            tmpX = x;
            tmpY = y;

        } else if (playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x - 1, y + 3))
                && !playerGreenFields.contains(new Coordinates(x, y + 4))
                && !playerRedFields.contains(new Coordinates(x, y + 4))
                && (x - 2 >= 0)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {

            tmpX = x;
            tmpY = y;

        } else if (playerGreenFields.contains(new Coordinates(x + 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x + 3, y + 3))
                && !playerGreenFields.contains(new Coordinates(x + 4, y + 4))
                && !playerRedFields.contains(new Coordinates(x + 4, y + 4))
                && (x + 4 < 8)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {

            tmpX = x;
            tmpY = y;

        } else if (playerGreenFields.contains(new Coordinates(x - 1, y + 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y + 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y + 2))
                && playerGreenFields.contains(new Coordinates(x - 3, y + 3))
                && !playerGreenFields.contains(new Coordinates(x - 4, y + 4))
                && !playerRedFields.contains(new Coordinates(x - 4, y + 4))
                && (x - 4 >= 0)
                && (y + 2 <= 7)
                && (y + 4 <= 7)) {

            tmpX = x;
            tmpY = y;

        } else {
            if (playerRedFields.size() - playerRedQueens.size() < 3) {
                gameController.checkPossibleGreenWin();
            }
            System.out.println("\u001B[31m" + "There is no possibility to move Red !!!" + "\u001B[0m");
        }
        gameController.sendCheckerSizes(playerRedFields.size(), playerGreenFields.size(),
                playerRedQueens.size(), playerGreenQueens.size());
        System.out.println("************************************");
    }

    void makeMoveRed(double x, double y) {

        if ((y - tmpY == 1) && (x - tmpX == 1 || x - tmpX == -1)) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else if ((y - tmpY == 2) && (x - tmpX == 2) && (tmpY + 2 <= 7)
                && playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 1))
                && !playerRedFields.contains(new Coordinates(tmpX + 2, tmpY + 2))
                && !playerGreenFields.contains(new Coordinates(tmpX + 2, tmpY + 2))) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 1))) {
                greenCoordinatesToKill = new Coordinates(tmpX + 1, tmpY + 1);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            playerGreenFields.remove(new Coordinates(tmpX + 1, tmpY + 1));
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else if ((y - tmpY == 2) && (x - tmpX == -2) && (tmpY + 2 <= 7)
                && playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 1))
                && !playerRedFields.contains(new Coordinates(tmpX - 2, tmpY + 2))
                && !playerGreenFields.contains(new Coordinates(tmpX - 2, tmpY + 2))) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 1))) {
                greenCoordinatesToKill = new Coordinates(tmpX - 1, tmpY + 1);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            playerGreenFields.remove(new Coordinates(tmpX - 1, tmpY + 1));
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else if ((y - tmpY == 4) && (tmpX == x) && (tmpY + 4 <= 7)
                && playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 1))
                && !playerRedFields.contains(new Coordinates(tmpX + 2, tmpY + 2))
                && !playerGreenFields.contains(new Coordinates(tmpX + 2, tmpY + 2))
                && playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 3))
                && !playerGreenFields.contains(new Coordinates(tmpX, tmpY + 4))
                && !playerRedFields.contains(new Coordinates(tmpX, tmpY + 4))) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 1))) {
                greenCoordinatesToKill = new Coordinates(tmpX + 1, tmpY + 1);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            if (playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 3))) {
                greenCoordinatesToKill = new Coordinates(tmpX + 1, tmpY + 3);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            playerGreenFields.remove(new Coordinates(tmpX + 1, tmpY + 1));
            playerGreenFields.remove(new Coordinates(tmpX + 1, tmpY + 3));
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else if ((y - tmpY == 4) && (tmpX == x) && (tmpY + 4 <= 7)
                && playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 1))
                && !playerRedFields.contains(new Coordinates(tmpX - 2, tmpY + 2))
                && !playerGreenFields.contains(new Coordinates(tmpX - 2, tmpY + 2))
                && playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 3))
                && !playerGreenFields.contains(new Coordinates(tmpX, tmpY + 4))
                && !playerRedFields.contains(new Coordinates(tmpX, tmpY + 4))) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 1))) {
                greenCoordinatesToKill = new Coordinates(tmpX - 1, tmpY + 1);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            if (playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 3))) {
                greenCoordinatesToKill = new Coordinates(tmpX - 1, tmpY + 3);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            playerGreenFields.remove(new Coordinates(tmpX - 1, tmpY + 1));
            playerGreenFields.remove(new Coordinates(tmpX - 1, tmpY + 3));
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else if ((y - tmpY == 4) && (x - tmpX == 4) && (tmpY + 4 <= 7)
                && playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 1))
                && !playerRedFields.contains(new Coordinates(tmpX + 2, tmpY + 2))
                && !playerGreenFields.contains(new Coordinates(tmpX + 2, tmpY + 2))
                && playerGreenFields.contains(new Coordinates(tmpX + 3, tmpY + 3))
                && !playerGreenFields.contains(new Coordinates(tmpX + 4, tmpY + 4))
                && !playerRedFields.contains(new Coordinates(tmpX + 4, tmpY + 4))) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerGreenFields.contains(new Coordinates(tmpX + 1, tmpY + 1))) {
                greenCoordinatesToKill = new Coordinates(tmpX + 1, tmpY + 1);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            if (playerGreenFields.contains(new Coordinates(tmpX + 3, tmpY + 3))) {
                greenCoordinatesToKill = new Coordinates(tmpX + 3, tmpY + 3);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            playerGreenFields.remove(new Coordinates(tmpX + 1, tmpY + 1));
            playerGreenFields.remove(new Coordinates(tmpX + 3, tmpY + 3));
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else if ((y - tmpY == 4) && (x - tmpX == -4) && (tmpY + 4 <= 7)
                && playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 1))
                && !playerRedFields.contains(new Coordinates(tmpX - 2, tmpY + 2))
                && !playerGreenFields.contains(new Coordinates(tmpX - 2, tmpY + 2))
                && playerGreenFields.contains(new Coordinates(tmpX - 3, tmpY + 3))
                && !playerGreenFields.contains(new Coordinates(tmpX - 4, tmpY + 4))
                && !playerRedFields.contains(new Coordinates(tmpX - 4, tmpY + 4))) {

            this.setRedTaken(true);
            checkerRedToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerRedFalse(checkerRedToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerGreenFields.contains(new Coordinates(tmpX - 1, tmpY + 1))) {
                greenCoordinatesToKill = new Coordinates(tmpX - 1, tmpY + 1);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            if (playerGreenFields.contains(new Coordinates(tmpX - 3, tmpY + 3))) {
                greenCoordinatesToKill = new Coordinates(tmpX - 3, tmpY + 3);
                System.out.println("\u001B[34m" + "Green killed : " + greenCoordinatesToKill + "\u001B[0m");
                this.gameController.killGreen(greenCoordinatesToKill);
            }
            playerGreenFields.remove(new Coordinates(tmpX - 1, tmpY + 1));
            playerGreenFields.remove(new Coordinates(tmpX - 3, tmpY + 3));
            playerRedFields.remove(new Coordinates(tmpX, tmpY));
            playerRedFields.add(new Coordinates(x, y));
            if (y == 7) {
                setFill(new ImagePattern(imageQueenRed));
                playerRedQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageRed));
            }
            System.out.println("CheckerRed put onto: x " + x + " / " + y + " y");

        } else {
            System.out.println("\u001B[31m" + "There is no possibility to set Red !!!" + "\u001B[0m");
        }
        gameController.vanishPossibilities(fieldsToNotify);
        fieldsToNotify.clear();
        gameController.sendCheckerSizes(playerRedFields.size(), playerGreenFields.size(),
                playerRedQueens.size(), playerGreenQueens.size());
        System.out.println("PlayerRedCheckers: " + playerRedFields.size());
        System.out.println("************************************");
    }

    void checkMoveGreen(double x, double y) {
        System.out.println("CheckerGreen clicked on: " + x + " x" +  " / " + y + " y");
        System.out.println("PlayerGreenCheckers: " + playerGreenFields.size());

        if (x + 1 < 8 && y - 1 >= 0) {
            fieldsToNotify.add(new Coordinates(x + 1, y - 1));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (x - 1 >= 0 && y - 1 >= 0) {
            fieldsToNotify.add(new Coordinates(x - 1, y - 1));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y - 2))
                && (x + 2 < 8)
                && (y - 2 >= 0)) {
            fieldsToNotify.add(new Coordinates(x + 2, y - 2));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y - 2))
                && (x - 2 >= 0)
                && (y - 2 >= 0)) {
            fieldsToNotify.add(new Coordinates(x - 2, y - 2));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y - 2))
                && playerRedFields.contains(new Coordinates(x + 1, y - 3))
                && !playerRedFields.contains(new Coordinates(x, y - 4))
                && !playerGreenFields.contains(new Coordinates(x, y - 4))
                && (x + 2 < 8)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {
            fieldsToNotify.add(new Coordinates(x, y - 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y - 2))
                && playerRedFields.contains(new Coordinates(x - 1, y - 3))
                && !playerRedFields.contains(new Coordinates(x, y - 4))
                && !playerGreenFields.contains(new Coordinates(x, y - 4))
                && (x - 2 >= 0)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {
            fieldsToNotify.add(new Coordinates(x, y - 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y - 2))
                && playerRedFields.contains(new Coordinates(x + 3, y - 3))
                && !playerRedFields.contains(new Coordinates(x + 4, y - 4))
                && !playerGreenFields.contains(new Coordinates(x + 4, y - 4))
                && (x + 4 < 8)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {
            fieldsToNotify.add(new Coordinates(x + 4, y - 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if (playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y - 2))
                && playerRedFields.contains(new Coordinates(x - 3, y - 3))
                && !playerRedFields.contains(new Coordinates(x - 4, y - 4))
                && !playerGreenFields.contains(new Coordinates(x - 4, y - 4))
                && (x - 4 >= 0)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {
            fieldsToNotify.add(new Coordinates(x - 4, y - 4));
            gameController.showPossibilities(fieldsToNotify);
        }

        if ((!playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerGreenFields.contains(new Coordinates(x + 1, y - 1))
                && (y - 1 >= 0)
                && (x + 1 < 8))) {

            tmpX = x;
            tmpY = y;

        } else if (!playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerGreenFields.contains(new Coordinates(x - 1, y - 1))
                && (y - 1 >= 0)
                && (x - 1 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y - 2))
                && (y - 2 >= 0)
                && (x + 2 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y - 2))
                && (y - 2 >= 0)
                && (x - 2 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y - 2))
                && playerRedFields.contains(new Coordinates(x + 1, y - 3))
                && !playerRedFields.contains(new Coordinates(x, y - 4))
                && !playerGreenFields.contains(new Coordinates(x, y - 4))
                && (x + 2 < 8)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y - 2))
                && playerRedFields.contains(new Coordinates(x - 1, y - 3))
                && !playerRedFields.contains(new Coordinates(x, y - 4))
                && !playerGreenFields.contains(new Coordinates(x, y - 4))
                && (x - 2 >= 0)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerRedFields.contains(new Coordinates(x + 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x + 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x + 2, y - 2))
                && playerRedFields.contains(new Coordinates(x + 3, y - 3))
                && !playerRedFields.contains(new Coordinates(x + 4, y - 4))
                && !playerGreenFields.contains(new Coordinates(x + 4, y - 4))
                && (x + 4 < 8)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else if (playerRedFields.contains(new Coordinates(x - 1, y - 1))
                && !playerRedFields.contains(new Coordinates(x - 2, y - 2))
                && !playerGreenFields.contains(new Coordinates(x - 2, y - 2))
                && playerRedFields.contains(new Coordinates(x - 3, y - 3))
                && !playerRedFields.contains(new Coordinates(x - 4, y - 4))
                && !playerGreenFields.contains(new Coordinates(x - 4, y - 4))
                && (x - 4 >= 0)
                && (y - 2 >= 0)
                && (y - 4 >= 0)) {

            tmpX = x;
            tmpY = y;

        } else {
            if (playerGreenFields.size() - playerGreenQueens.size() < 3) {
                gameController.checkPossibleRedWin();
            }
            System.out.println("\u001B[31m" + "There is no possibility to move Green !!!" + "\u001B[0m");
        }
        gameController.sendCheckerSizes(playerRedFields.size(), playerGreenFields.size(),
                playerRedQueens.size(), playerGreenQueens.size());
        System.out.println("************************************");
    }

    void makeMoveGreen(double x, double y) {

        if ((-y + tmpY == 1) && (-x + tmpX == 1 || -x + tmpX == -1)) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else if ((-y + tmpY == 2) && (-x + tmpX == 2)
                && playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 1))
                && !playerRedFields.contains(new Coordinates(tmpX - 2, tmpY - 2))
                && !playerGreenFields.contains(new Coordinates(tmpX - 2, tmpY - 2))) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 1))) {
                redCoordinatesToKill = new Coordinates(tmpX - 1, tmpY -1);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            playerRedFields.remove(new Coordinates(tmpX - 1, tmpY -1));
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else if ((-y + tmpY == 2) && (-x + tmpX == -2)
                && playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 1))
                && !playerRedFields.contains(new Coordinates(tmpX + 2, tmpY - 2))
                && !playerGreenFields.contains(new Coordinates(tmpX + 2, tmpY - 2))) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 1))) {
                redCoordinatesToKill = new Coordinates(tmpX + 1, tmpY -1);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            playerRedFields.remove(new Coordinates(tmpX + 1, tmpY - 1));
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else if ((-y + tmpY == 4) && (tmpX == x)
                && playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 1))
                && !playerRedFields.contains(new Coordinates(tmpX + 2, tmpY - 2))
                && !playerGreenFields.contains(new Coordinates(tmpX + 2, tmpY - 2))
                && playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 3))
                && !playerRedFields.contains(new Coordinates(tmpX, tmpY - 4))
                && !playerGreenFields.contains(new Coordinates(tmpX, tmpY - 4))) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 1))) {
                redCoordinatesToKill = new Coordinates(tmpX + 1, tmpY - 1);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            if (playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 3))) {
                redCoordinatesToKill = new Coordinates(tmpX + 1, tmpY - 3);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            playerRedFields.remove(new Coordinates(tmpX + 1, tmpY - 1));
            playerRedFields.remove(new Coordinates(tmpX + 1, tmpY - 3));
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else if ((-y + tmpY == 4) && (tmpX == x)
                && playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 1))
                && !playerRedFields.contains(new Coordinates(tmpX - 2, tmpY - 2))
                && !playerGreenFields.contains(new Coordinates(tmpX - 2, tmpY - 2))
                && playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 3))
                && !playerRedFields.contains(new Coordinates(tmpX, tmpY - 4))
                && !playerGreenFields.contains(new Coordinates(tmpX, tmpY - 4))) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 1))) {
                redCoordinatesToKill = new Coordinates(tmpX - 1, tmpY - 1);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            if (playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 3))) {
                redCoordinatesToKill = new Coordinates(tmpX - 1, tmpY - 3);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            playerRedFields.remove(new Coordinates(tmpX - 1, tmpY - 1));
            playerRedFields.remove(new Coordinates(tmpX - 1, tmpY - 3));
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else if ((-y + tmpY == 4) && (-x + tmpX == -4)
                && playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 1))
                && !playerRedFields.contains(new Coordinates(tmpX + 2, tmpY - 2))
                && !playerGreenFields.contains(new Coordinates(tmpX + 2, tmpY - 2))
                && playerRedFields.contains(new Coordinates(tmpX + 3, tmpY - 3))
                && !playerRedFields.contains(new Coordinates(tmpX + 4, tmpY - 4))
                && !playerGreenFields.contains(new Coordinates(tmpX + 4, tmpY - 4))) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerRedFields.contains(new Coordinates(tmpX + 1, tmpY - 1))) {
                redCoordinatesToKill = new Coordinates(tmpX + 1, tmpY - 1);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            if (playerRedFields.contains(new Coordinates(tmpX + 3, tmpY - 3))) {
                redCoordinatesToKill = new Coordinates(tmpX + 3, tmpY - 3);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            playerRedFields.remove(new Coordinates(tmpX + 1, tmpY - 1));
            playerRedFields.remove(new Coordinates(tmpX + 3, tmpY - 3));
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else if ((-y + tmpY == 4) && (-x + tmpX == 4)
                && playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 1))
                && !playerRedFields.contains(new Coordinates(tmpX - 2, tmpY - 2))
                && !playerGreenFields.contains(new Coordinates(tmpX - 2, tmpY - 2))
                && playerRedFields.contains(new Coordinates(tmpX - 3, tmpY - 3))
                && !playerRedFields.contains(new Coordinates(tmpX - 4, tmpY - 4))
                && !playerGreenFields.contains(new Coordinates(tmpX - 4, tmpY - 4))) {

            this.setGreenTaken(true);
            checkerGreenToFalse = new Coordinates(tmpX, tmpY);
            gameController.makeCheckerGreenFalse(checkerGreenToFalse);
            checkerToVanish = new Coordinates(tmpX, tmpY);
            this.gameController.vanishChecker(checkerToVanish);
            if (playerRedFields.contains(new Coordinates(tmpX - 1, tmpY - 1))) {
                redCoordinatesToKill = new Coordinates(tmpX - 1, tmpY - 1);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            if (playerRedFields.contains(new Coordinates(tmpX - 3, tmpY - 3))) {
                redCoordinatesToKill = new Coordinates(tmpX - 3, tmpY - 3);
                System.out.println("\u001B[34m" + "Red killed : " + redCoordinatesToKill + "\u001B[0m");
                this.gameController.killRed(redCoordinatesToKill);
            }
            playerRedFields.remove(new Coordinates(tmpX - 1, tmpY - 1));
            playerRedFields.remove(new Coordinates(tmpX - 3, tmpY - 3));
            playerGreenFields.remove(new Coordinates(tmpX, tmpY));
            playerGreenFields.add(new Coordinates(x, y));
            if (y == 0) {
                setFill(new ImagePattern(imageQueenGreen));
                playerGreenQueens.add(new Coordinates(x, y));
            } else {
                setFill(new ImagePattern(imageGreen));
            }
            System.out.println("CheckerGreen put onto: x " + x + " / " + y + " y");

        } else {
            System.out.println("\u001B[31m" + "There is no possibility to set Green !!!" + "\u001B[0m");
        }
        gameController.vanishPossibilities(fieldsToNotify);
        fieldsToNotify.clear();
        gameController.sendCheckerSizes(playerRedFields.size(), playerGreenFields.size(),
                playerRedQueens.size(), playerGreenQueens.size());
        System.out.println("PlayerGreenCheckers: " + playerGreenFields.size());
        System.out.println("************************************");
    }

    public boolean isRedTaken() {
        return isRedTaken;
    }

    private void setRedTaken(boolean taken) {
        isRedTaken = taken;
    }

    public boolean isGreenTaken() {
        return isGreenTaken;
    }

    private void setGreenTaken(boolean taken) {
        isGreenTaken = taken;
    }

    public static Set<Coordinates> getPlayerRedFields() {
        return playerRedFields;
    }

    public static Set<Coordinates> getPlayerGreenFields() {
        return playerGreenFields;
    }

    public static List<Coordinates> getPlayerRedQueens() {
        return playerRedQueens;
    }

    public static List<Coordinates> getPlayerGreenQueens() {
        return playerGreenQueens;
    }
}