package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a ship on a board in the battleship game
 */
public class Ship {
  ShipType shipType;
  ArrayList<Coord> location;
  boolean horizontal;
  Random rand;

  /**
   * Constructor used when creating ships in the battleship game
   *
   * @param shipType   a ShipType for the Ship
   * @param location   a list of coordinates that represents the location
   * @param horizontal whether the ship is horizontal or vertical
   * @param rand       a random used for testing
   */
  public Ship(ShipType shipType, ArrayList<Coord> location, boolean horizontal, Random rand) {
    this.shipType = shipType;
    this.location = location;
    this.horizontal = horizontal;
    this.rand = rand;
  }

  /**
   * Randomizes whether the ship is horizontal or vertical
   */
  public void randomizeHorizontal() {
    this.horizontal = rand.nextInt(2) == 0;
  }

  /**
   * Randomizes the position of the ship on the board and changes the location of it accordingly
   *
   * @param listOfShips a list of all the ships in one player/AI's board
   * @param width       the integer of the width of the board
   * @param height      the integer of the height of the board
   */
  public void randomizePosition(List<Ship> listOfShips, int width, int height) {
    List<Coord> coordinates = this.randomCoordinate(width, height);

    while (!checkOverlap(coordinates, listOfShips, width, height)) {
      coordinates = randomCoordinate(width, height);
    }

    location.addAll(coordinates);
  }

  /**
   * Checks to make sure that each coordinate to be added to the location does not already exist
   * and is within the bounds of the board
   *
   * @param coordinate  a list of coordinates that could be the potential location of the ship
   * @param listOfShips a list of all the ships
   * @param width       the width of the board
   * @param height      the height of the board
   * @return a boolean that indicates if all the coordinates are valid
   */
  private boolean checkOverlap(List<Coord> coordinate, List<Ship> listOfShips, int width,
                               int height) {

    boolean notOverlap = true;

    for (Coord coord : coordinate) {
      if (!(coord.getX() < width && coord.getPosY() < height)) {
        return false;
      }
    }

    for (Ship s : listOfShips) {
      for (Coord c : s.getLocation()) {
        for (Coord coord : coordinate) {
          notOverlap = c.getX() != coord.getX() || c.getPosY() != coord.getPosY();

          if (!notOverlap) {
            return notOverlap;
          }
        }
      }
    }

    return notOverlap;
  }

  /**
   * Returns the location of this ship
   *
   * @return a list of coordinates that represent the location
   */
  public List<Coord> getLocation() {
    return this.location;
  }

  /**
   * Returns the ship type of this ship
   *
   * @return a ship type of this ship
   */
  public ShipType getShipType() {
    return this.shipType;
  }

  /**
   * Generates a random list of coordinates based on whether a ship is horizontal or vertical
   *
   * @param width  the width of the board
   * @param height the height of the board
   * @return a list of coordinates that could represent the location of a ship
   */
  private List<Coord> randomCoordinate(int width, int height) {
    List<Coord> listOfCoords = new ArrayList<>();
    Coord coordinate = new Coord(rand.nextInt(width), rand.nextInt(height));

    listOfCoords.add(coordinate);

    if (this.horizontal) {
      for (int i = 0; i < this.shipType.getSize() - 1; i++) {
        int x = listOfCoords.get(i).getX() + 1;
        int y = listOfCoords.get(i).getPosY();
        Coord newCoordinate = new Coord(x, y);

        listOfCoords.add(newCoordinate);
      }
    } else {
      for (int i = 0; i < this.shipType.getSize() - 1; i++) {
        int x = listOfCoords.get(i).getX();
        int y = listOfCoords.get(i).getPosY() + 1;
        Coord newCoordinate = new Coord(x, y);

        listOfCoords.add(newCoordinate);
      }
    }

    return listOfCoords;
  }


}
