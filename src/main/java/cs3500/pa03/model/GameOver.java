package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a GameOver class that is responsible for determining when a game is over
 */
public class GameOver {

  /**
   * Returns a list of ships from all the users ships that have not sunk yet
   *
   * @param allShips a list of ships that represents all the player's/AI's ships
   * @param board A board that represents the player's/AI's board
   * @return a list of ships that have not sunk yet
   */
  public List<Ship> shipsNotSunk(List<Ship> allShips, Board board) {
    boolean wholeShipSunk;
    List<Ship> shipsNotSunk = new ArrayList<>();

    for (Ship s : allShips) {
      wholeShipSunk = true;
      for (Coord c : s.getLocation()) {
        for (int i = 0; i < board.getCoords().length; i++) {
          for (int j = 0; j < board.getCoords()[i].length; j++) {
            if (board.getCoords()[i][j].getX() == c.getX()
                && board.getCoords()[i][j].getPosY() == c.getPosY()) {
              if (!board.getCoords()[i][j].getIsHit()) {
                wholeShipSunk = false;
                break;
              }
            }
          }
          if (!wholeShipSunk) {
            break;
          }
        }
        if (!wholeShipSunk) {
          break;
        }
      }
      if (!wholeShipSunk) {
        shipsNotSunk.add(s);
      }
    }

    return shipsNotSunk;
  }

  /**
   * Outputs the game result when a game is over or null if the game is not over
   *
   * @param userShips     a list of all the player's ships
   * @param aiShips       a list of all the AI's ships
   * @param playerBoard   a board that represents the player's board
   * @param opponentBoard the AI's board
   * @return the game result when a game is over and null if it is not over
   */
  public GameResult isGameOver(List<Ship> userShips, List<Ship> aiShips, Board playerBoard,
                               Board opponentBoard) {

    if (shipsNotSunk(userShips, playerBoard).size() == 0
        && shipsNotSunk(aiShips, opponentBoard).size() == 0) {
      return GameResult.TIED;
    } else if (shipsNotSunk(userShips, playerBoard).size() == 0) {
      return GameResult.LOSE;
    } else if (shipsNotSunk(aiShips, opponentBoard).size() == 0) {
      return GameResult.WIN;
    } else {
      return null;
    }
  }

}
