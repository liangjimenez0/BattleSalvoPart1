package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a single AI Player in the battleship game
 */
public class AiPlayer implements Player {

  private String name;
  private List<Coord> shotsTaken;
  private List<Ship> listOfShips;
  private Board aiBoard;
  private Board playerBoard;
  private Random random;

  /**
   * Constructor used when creating an AI Player
   *
   * @param aiBoard     a board that represents an AI board
   * @param playerBoard a board that represents the player's board
   * @param random      a random used for testing
   */
  public AiPlayer(Board aiBoard, Board playerBoard, Random random) {
    this.name = "AI Player";
    this.shotsTaken = new ArrayList<>();
    this.listOfShips = new ArrayList<>();
    this.aiBoard = aiBoard;
    this.playerBoard = playerBoard;
    this.random = random;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    for (int i = 0; i < specifications.get(ShipType.CARRIER); i++) {
      listOfShips.add(new Ship(ShipType.CARRIER, new ArrayList<Coord>(), true, random));
    }

    for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i++) {
      listOfShips.add(new Ship(ShipType.BATTLESHIP, new ArrayList<Coord>(), true, random));
    }

    for (int i = 0; i < specifications.get(ShipType.DESTROYER); i++) {
      listOfShips.add(new Ship(ShipType.DESTROYER, new ArrayList<Coord>(), true, random));
    }

    for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i++) {
      listOfShips.add(new Ship(ShipType.SUBMARINE, new ArrayList<Coord>(), true, random));
    }

    for (Ship s : listOfShips) {
      s.randomizeHorizontal();
      s.randomizePosition(listOfShips, width, height);
    }

    return listOfShips;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {

    GameOver gameOver = new GameOver();
    int numberOfShots = Math.min(gameOver.shipsNotSunk(listOfShips, aiBoard).size(),
        aiBoard.countEmptyCoordinates());
    int size = shotsTaken.size();

    while (shotsTaken.size() != size + numberOfShots) {
      int x = random.nextInt(aiBoard.getCoords()[0].length);
      int y = random.nextInt(aiBoard.getCoords().length);
      Coord coord = new Coord(x, y);

      for (Coord[] coordinate : playerBoard.getCoords()) {
        for (Coord c : coordinate) {
          if ((c.getX() == coord.getX() && (c.getPosY() == coord.getPosY()))) {
            if (!(c.getIsMiss()) && !(c.getIsHit())) {
              this.shotsTaken.add(coord);
            }

          }
        }
      }
    }

    return this.shotsTaken;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damage = new ArrayList<>();

    for (Ship ship : this.listOfShips) {
      for (Coord c : ship.getLocation()) {
        for (Coord shot : opponentShotsOnBoard) {
          if (shot.getX() == c.getX() && shot.getPosY() == c.getPosY()) {
            damage.add(shot);
          }
        }
      }
    }

    for (int i = 0; i < aiBoard.getCoords().length; i++) {
      for (int j = 0; j < aiBoard.getCoords()[i].length; j++) {
        for (Coord shots : opponentShotsOnBoard) {
          if (shots.getX() == aiBoard.getCoords()[i][j].getX()
              && shots.getPosY() == aiBoard.getCoords()[i][j].getPosY()) {
            aiBoard.getCoords()[i][j].changeMiss();
          }
        }
      }
    }

    return damage;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

    for (Coord shots : shotsThatHitOpponentShips) {
      playerBoard.getCoords()[shots.getPosY()][shots.getX()].changeHit();
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    return;
  }
}
