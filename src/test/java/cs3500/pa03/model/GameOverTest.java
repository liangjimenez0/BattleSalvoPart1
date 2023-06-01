package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class GameOver and all respective methods
 */
class GameOverTest {

  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;
  Coord c5;
  Coord c6;
  ArrayList<Coord> coordList1;
  ArrayList<Coord> coordList2;
  ArrayList<Coord> coordList3;
  Ship playerShip1;
  Ship playerShip2;
  Ship playerShip3;
  List<Ship> playerShips;
  Ship aiShip1;
  Ship aiShip2;
  Ship aiShip3;
  List<Ship> aiShips;
  Board playerBoard;
  Board aiBoard;
  GameOver gameOver;

  /**
   * Sets up variables before each test method in this class
   */
  @BeforeEach
  void setUp() {
    c1 = new Coord(0, 0);
    c2 = new Coord(1, 1);
    c3 = new Coord(2, 2);
    c4 = new Coord(3, 3);
    c5 = new Coord(4, 4);
    c6 = new Coord(5, 5);

    coordList1 = new ArrayList<>();
    coordList2 = new ArrayList<>();
    coordList3 = new ArrayList<>();

    coordList1.add(c1);
    coordList1.add(c2);
    coordList2.add(c3);
    coordList2.add(c4);
    coordList3.add(c5);
    coordList3.add(c6);

    playerShip1 = new Ship(ShipType.CARRIER, coordList1, true, new Random(1));
    playerShip2 = new Ship(ShipType.DESTROYER, coordList2, false, new Random(2));
    playerShip3 = new Ship(ShipType.SUBMARINE, coordList3, true, new Random(3));

    playerShips = new ArrayList<>();

    playerShips.add(playerShip1);
    playerShips.add(playerShip2);
    playerShips.add(playerShip3);

    aiShip1 = new Ship(ShipType.BATTLESHIP, coordList2, true, new Random(4));
    aiShip2 = new Ship(ShipType.SUBMARINE, coordList3, true, new Random(5));
    aiShip3 = new Ship(ShipType.CARRIER, coordList1, false, new Random(6));

    aiShips = new ArrayList<>();

    aiShips.add(aiShip1);
    aiShips.add(aiShip2);
    aiShips.add(aiShip3);

    playerBoard = new Board();
    aiBoard = new Board();

    gameOver = new GameOver();
  }

  /**
   * Tests the shipsNotSunk method in that it returns the list of ships that have not sunk
   * on the respective player's board
   */
  @Test
  void shipsNotSunk() {
    playerBoard.generateAllCoords(6, 6);

    assertEquals(3, gameOver.shipsNotSunk(playerShips, playerBoard).size());

    for (Coord c : playerShip1.getLocation()) {
      playerBoard.getCoords()[c.getPosY()][c.getX()].changeHit();
    }

    assertEquals(2, gameOver.shipsNotSunk(playerShips, playerBoard).size());
  }

  /**
   * Tests the method isGameOver() in that it returns the correct game result when the game
   * is over
   */
  @Test
  void isGameOver() {
    playerBoard.generateAllCoords(6, 6);
    aiBoard.generateAllCoords(6, 6);

    assertTrue(Objects.isNull(gameOver.isGameOver(playerShips, aiShips, playerBoard, aiBoard)));

    for (Ship s : playerShips) {
      for (Coord c : s.getLocation()) {
        playerBoard.getCoords()[c.getPosY()][c.getX()].changeHit();
      }
    }

    assertEquals(GameResult.LOSE, gameOver.isGameOver(playerShips, aiShips, playerBoard, aiBoard));

    for (Ship s : playerShips) {
      for (Coord c : s.getLocation()) {
        playerBoard.getCoords()[c.getPosY()][c.getX()].changeMiss();
      }
    }

    for (Ship s : aiShips) {
      for (Coord c : s.getLocation()) {
        aiBoard.getCoords()[c.getPosY()][c.getX()].changeHit();
      }
    }

    assertEquals(GameResult.WIN, gameOver.isGameOver(playerShips, aiShips, playerBoard, aiBoard));

    for (Ship s : playerShips) {
      for (Coord c : s.getLocation()) {
        playerBoard.getCoords()[c.getPosY()][c.getX()].changeHit();
      }
    }

    assertEquals(GameResult.TIED, gameOver.isGameOver(playerShips, aiShips, playerBoard, aiBoard));
  }

}