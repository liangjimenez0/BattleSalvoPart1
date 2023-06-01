package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class AIPlayer and all respective methods
 */
class AiPlayerTest {

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
  Player aiPlayer;
  Map<ShipType, Integer> specificationsAi;

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

    playerShip1 = new Ship(ShipType.CARRIER, coordList1, true, new Random(3));
    playerShip2 = new Ship(ShipType.DESTROYER, coordList2, true, new Random(4));
    playerShip3 = new Ship(ShipType.SUBMARINE, coordList3, true, new Random(1));

    playerShips = new ArrayList<>();

    playerShips.add(playerShip1);
    playerShips.add(playerShip2);
    playerShips.add(playerShip3);

    aiShip1 = new Ship(ShipType.BATTLESHIP, coordList2, true, new Random(3));
    aiShip2 = new Ship(ShipType.SUBMARINE, coordList3, true, new Random(4));
    aiShip3 = new Ship(ShipType.CARRIER, coordList1, true, new Random(1));

    aiShips = new ArrayList<>();

    aiShips.add(aiShip1);
    aiShips.add(aiShip2);
    aiShips.add(aiShip3);

    playerBoard = new Board();
    playerBoard.generateAllCoords(8, 8);
    aiBoard = new Board();
    aiBoard.generateAllCoords(10, 10);

    aiPlayer = new AiPlayer(aiBoard, playerBoard, new Random(3));

    specificationsAi = new BuildMap().buildMap(2, 2, 1, 1);
  }

  /**
   * Tests the method name() in that it returns the name
   */
  @Test
  void name() {
    assertEquals("AI Player", aiPlayer.name());
  }

  /**
   * Tests the method setup() in that it properly sets up the ships according to the map, width
   * , and height
   */
  @Test
  void setup() {
    List<Ship> aiPlayerShips = aiPlayer.setup(8, 8, specificationsAi);

    assertEquals(6, aiPlayerShips.size());
    assertEquals(ShipType.CARRIER, aiPlayerShips.get(0).getShipType());
    assertEquals(ShipType.BATTLESHIP, aiPlayerShips.get(2).getShipType());
    assertEquals(ShipType.DESTROYER, aiPlayerShips.get(4).getShipType());
    assertEquals(ShipType.SUBMARINE, aiPlayerShips.get(5).getShipType());
  }

  /**
   * Tests the method takeShots() in that it returns a list of the shots the user made
   */
  @Test
  void takeShots() {
    aiPlayer.setup(8, 8, specificationsAi);
    List<Coord> allShots = aiPlayer.takeShots();

    assertEquals(6, allShots.size());

    for (Coord c : allShots) {
      assertTrue(c.getX() >= 0);
      assertTrue(c.getPosY() >= 0);
      assertTrue(c.getPosY() < aiBoard.getCoords().length);
      assertTrue(c.getX() < aiBoard.getCoords()[0].length);
    }
  }

  /**
   * Tests the method reportDamage() in that it returns which of this player's shots hit their
   * opponents ships
   */
  @Test
  void reportDamage() {
    aiPlayer.setup(8, 8, specificationsAi);
    List<Coord> shots = new ArrayList<>();
    Coord coord1 = new Coord(5, 1);
    Coord coord2 = new Coord(6, 1);
    Coord coord3 = new Coord(7, 1);
    shots.add(coord1);
    shots.add(coord2);
    shots.add(coord3);

    assertEquals(3, aiPlayer.reportDamage(shots).size());
    assertEquals(shots.get(0).getX(), aiPlayer.reportDamage(shots).get(0).getX());
    assertEquals(shots.get(1).getPosY(), aiPlayer.reportDamage(shots).get(1).getPosY());
    assertEquals(shots.get(2).getX(), aiPlayer.reportDamage(shots).get(2).getX());
  }

  /**
   * Tests successfulHits() in that it updates the opponent's board based on what was hit or missed
   */
  @Test
  void successfulHits() {
    aiPlayer.successfulHits(coordList1);
    aiPlayer.successfulHits(coordList2);

    assertTrue(playerBoard.getCoords()[0][0].getIsHit());
    assertTrue(playerBoard.getCoords()[1][1].getIsHit());
    assertTrue(playerBoard.getCoords()[2][2].getIsHit());
    assertTrue(playerBoard.getCoords()[3][3].getIsHit());
    assertFalse(playerBoard.getCoords()[6][6].getIsHit());
    assertFalse(playerBoard.getCoords()[7][7].getIsHit());

  }

}