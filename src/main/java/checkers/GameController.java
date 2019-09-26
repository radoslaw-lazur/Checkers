package checkers;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameController {

    private Map<Coordinates, Field> checkerMap = new HashMap<>();
    private boolean isGreenTurn = true;
    private boolean isFieldChoosen = false;
    private boolean possibleGreenWin = false;
    private boolean possibleRedWin = false;
    private int playerRedSize = 12;
    private int playerGreenSize = 12;
    private int playerRedQueenSize;
    private int playerGreenQueenSize;
    private Board board;

    GameController(Board board) {
        this.board = board;
    }

    void addField(Coordinates coordinates, Field field) {
        checkerMap.put(coordinates, field);
    }

    void onMouseClicked(Field field) {
        System.out.println("Possible playerGreen win: " + possibleGreenWin);
        System.out.println("Possible playerRed win: " + possibleRedWin);
        //System.out.println("IsGreenTurn " + isFieldChoosen + ". IsFieldChoosen " + isFieldChoosen);
        if (!isGreenTurn) {
            if (field.isRedTaken) {
                field.checkMoveRed(field.x, field.y);
                isFieldChoosen = true;
                board.setGreenWinner(possibleGreenWin);
                board.setRedWinner(possibleRedWin);
            } else if (isFieldChoosen) {
                field.makeMoveRed(field.x, field.y);
                isGreenTurn = true;
                isFieldChoosen = false;
                board.setTurnNotification(false);
            }
        } else {
            if (field.isGreenTaken) {
                field.checkMoveGreen(field.x, field.y);
                isFieldChoosen = true;
                board.setGreenWinner(possibleGreenWin);
                board.setRedWinner(possibleRedWin);
            } else if (isFieldChoosen) {
                field.makeMoveGreen(field.x, field.y);
                isGreenTurn = false;
                isFieldChoosen = false;
                board.setTurnNotification(true);
            }
        }
        if (Field.getPlayerRedFields().size() - Field.getPlayerRedQueens().size() == 0) {
            board.setGreenWinner(true);
        }
        if (Field.getPlayerGreenFields().size() - Field.getPlayerGreenQueens().size() == 0) {
            board.setRedWinner(true);
        }
    }

    void makeCheckerRedFalse(Coordinates coordinates) {
        Field checkerRedToFalse = checkerMap.get(coordinates);
        checkerRedToFalse.isRedTaken = false;
    }

    void makeCheckerGreenFalse(Coordinates coordinates) {
        Field checkerGreenToFalse = checkerMap.get(coordinates);
        checkerGreenToFalse.isGreenTaken = false;
    }

    void vanishChecker(Coordinates coordinates) {
        Field checkerToVanish = checkerMap.get(coordinates);
        checkerToVanish.setFill(Field.BLACK_COLOR);
    }

    void killGreen(Coordinates coordinates) {
        System.out.println("Kill green with coordinates: " + coordinates);
        Field greenFieldToKill = checkerMap.get(coordinates);
        greenFieldToKill.isGreenTaken = false;
        greenFieldToKill.setFill(Field.BLACK_COLOR);
        System.out.println("************************************");
    }

    void killRed(Coordinates coordinates) {
        System.out.println("Kill red with coordinates: " + coordinates);
        Field redFieldToKill = checkerMap.get(coordinates);
        redFieldToKill.isRedTaken = false;
        redFieldToKill.setFill(Field.BLACK_COLOR);
        System.out.println("************************************");
    }

    void showPossibilities(List<Coordinates> possibilities) {
        for (Coordinates possibility : possibilities) {
            Field field = checkerMap.get(possibility);
            if (!field.isGreenTaken && !field.isRedTaken) {
                field.setFill(Color.valueOf("#C7C7C7"));
            }
        }
    }

    void vanishPossibilities(List<Coordinates> possibilities) {
        for (Coordinates possibility: possibilities) {
            Field field = checkerMap.get(possibility);
            if (!field.isRedTaken && !field.isGreenTaken) {
                field.setFill(Field.BLACK_COLOR);
            }
        }
    }

    void sendCheckerSizes(int playerRed, int playerGreen, int playerRedQueens, int playerGreenQueens) {
        playerGreenSize = playerGreen;
        playerRedSize  = playerRed;
        playerRedQueenSize = playerRedQueens;
        playerGreenQueenSize = playerGreenQueens;
    }

    void checkPossibleGreenWin() {
        if ((playerRedSize - playerRedQueenSize < playerGreenSize - playerGreenQueenSize)
                && (playerGreenQueenSize > playerRedQueenSize)) {
            possibleGreenWin = true;
        }
    }

    void checkPossibleRedWin() {
        if ((playerGreenSize - playerGreenQueenSize < playerRedSize - playerRedQueenSize)
                && (playerRedQueenSize > playerGreenQueenSize)) {
            possibleRedWin = true;
        }
    }
}