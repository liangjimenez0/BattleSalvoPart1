package cs3500.pa03.model;

/**
 * Represents a coordinate which marks each coordinate on a board in a game of Battleship
 */
public class Coord {

  private int posX;
  private int posY;
  private boolean isHit;
  private boolean isMiss;

  /**
   * Constructor used when creating a Coord
   *
   * @param x an integer that represents the x coordinate of the Coord
   * @param posY an integer that represents the y coordinate of the Coord
   */
  public Coord(int x, int posY) {
    this.posX = x;
    this.posY = posY;
    this.isHit = false;
    this.isMiss = false;
  }

  /**
   * Gets the x coordinate of a Coord
   *
   * @return an integer that represents the x coordinate
   */
  public int getX() {
    return this.posX;
  }

  /**
   * Gets the y coordinate of a Coord
   *
   * @return an integer that represents the y coordinate
   */
  public int getPosY() {
    return this.posY;
  }

  /**
   * Gets the hit value of a Coord
   *
   * @return a boolean that represents whether a Coord has been hit
   */
  public boolean getIsHit() {
    return this.isHit;
  }

  /**
   * Gets the miss value of a Coord
   *
   * @return a boolean that represents whether a Coord has been missed
   */
  public boolean getIsMiss() {
    return this.isMiss;
  }

  /**
   * Changes the hit value of the coordinate to true and the miss value to false
   */
  public void changeHit() {
    this.isHit = true;
    this.isMiss = false;
  }

  /**
   * Changes the miss value of the coordinate to true and the hit value to false
   */
  public void changeMiss() {
    this.isMiss = true;
    this.isHit = false;
  }

}
