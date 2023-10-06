package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The Room Class represents a given instance of a room with respective width, height. Also each room
 * has four position fields, representing room's upper left position, button left position, upper right
 * position and button right position respectively.
 */
public class Room {    protected int width;
  protected int height;
  protected Position lowerLeft;
  protected Position upperRight;
  protected Position upperLeft;
  protected Position lowerRight;
  protected Position horizontalDoor;
  protected Position verticalDoor;
  protected Random random;
  protected Position blockPosition;

  protected static final int MIN_SIDE_LENGTH = 6;
  protected static final int MAX_SIDE_LENGTH = 12;

  /**
   * Creates a new Room object with a random width and height from the given seed.
   * Uses these random widths and heights, along with a random starting Position in the lowerLeft
   * in order to create a Room instance. In any case where the Room exceeds the dimensions of the
   * screen, we will simply take the upperRight to have dimension of WIDTH and HEIGHT,
   * respectively.
   */
  public Room(Random random) {
    this.random = random;
    this.width = randomSideLength();
    this.height = randomSideLength();
    this.lowerLeft = randomStartingPosition();
    this.upperRight = getUpperRight(width, height, lowerLeft);
    this.upperLeft = getUpperLeft(height, lowerLeft);
    this.lowerRight = getLowerRight(width, lowerLeft);
    this.horizontalDoor = getHorizontalDoor();
    this.verticalDoor = getVerticalDoor();
    this.blockPosition = new Position(verticalDoor.getX(), horizontalDoor.getY());
  }

  /** Returns a random side length (Integer) that is between the specified range. */
  public int randomSideLength() {
    return Math.max(1, random.nextInt(MAX_SIDE_LENGTH - MIN_SIDE_LENGTH) + MIN_SIDE_LENGTH);
  }

  /** Returns a random starting Position in the lower left corner of a Room. */
  public Position randomStartingPosition() {
    int lowerLeftX = random.nextInt(World.WIDTH);
    int lowerLeftY = random.nextInt(World.STARTINGHEIGHT);
    return new Position(lowerLeftX, lowerLeftY);
  }

  /**
   * Returns the corresponding Upper Right Position of a room given its width, height and
   * its corresponding lower left Position.
   */
  public Position getUpperRight(int inputWidth, int inputHeight, Position inputLowerLeft) {
    int upperRightX = (inputLowerLeft.getX() + (inputWidth));
    int upperRightY = (inputLowerLeft.getY() + (inputHeight));
    if (upperRightX >= World.WIDTH - 1) {
      upperRightX = World.WIDTH - 1;
      this.width = upperRightX - inputLowerLeft.getX();
    }
    if (upperRightY >= World.STARTINGHEIGHT - 1) {
      upperRightY = World.STARTINGHEIGHT - 1;
      this.height = upperRightY - inputLowerLeft.getY();
    }
    return new Position(upperRightX, upperRightY);

  }

  /** Returns the corresponding upper Left corner of a Room. */
  public Position getUpperLeft(int inputHeight, Position inputLowerLeft) {
    int upperLeftX = inputLowerLeft.getX();
    int upperLeftY = inputLowerLeft.getY() + (inputHeight - 1);
    return new Position(upperLeftX, upperLeftY);

  }

  /** Returns the corresponding lower right corner of a Room. */
  public Position getLowerRight(int inputWidth, Position inputLowerLeft) {
    int lowerRightX = inputLowerLeft.getX() + (inputWidth - 1);
    int lowerRightY = inputLowerLeft.getY();
    return new Position(lowerRightX, lowerRightY);
  }


  /** Return a boolean describing whether a Position lies within the corners
   * of a room. */
  public boolean isInside(Position p) {
    int llx = lowerLeft.getX();
    int lly = lowerLeft.getY();
    int urx = upperRight.getX();
    int ury = upperRight.getY();

    boolean inXBound = p.getX() >= llx && p.getX() <= urx;
    boolean inYBound = p.getY() >= lly && p.getY() <= ury;

    return inXBound && inYBound;
  }

  /** Returns a boolean describing whether the input Room possesses no corners
   * that lie within the corners of another Room but still overlaps. */
  public boolean sideOverlap(Room r) {
    int llx = lowerLeft.getX(); int lly = lowerLeft.getY();
    int urx = upperRight.getX(); int ury = upperRight.getY();

    int newLLX = r.lowerLeft.getX(); int newLLY = r.lowerLeft.getY();
    int newURX = r.upperRight.getX(); int newURY = r.upperRight.getY();

    if (newLLX < llx && newURX > urx
            && newLLY < ury && newLLY > lly
            && newURY < ury && newURY > lly) {
      return true;
    }
    if (newLLY < lly && newURY > ury
            && newLLX < urx && newLLX > llx
            && newURX < urx && newURX > llx) {
      return true;
    }
    return false;
  }

  /** Returns the Position (horizontally facing) of the door. */
  public Position getHorizontalDoor() {
    int doorX;
    if (upperRight.getX() < (World.WIDTH / 2)) {
      doorX = upperRight.getX() - 1;
    } else {
      doorX = lowerLeft.getX();
    }
    int doorY = lowerLeft.getY() + (height / 2) - 1;
    return new Position(doorX, doorY);
  }

  /** Returns the Position (vertically facing) of the door. */
  public Position getVerticalDoor() {
    int doorY;
    if (upperRight.getY() < (World.STARTINGHEIGHT / 2)) {
      doorY = upperRight.getY() - 1;
    } else {
      doorY = lowerLeft.getY();
    }
    int doorX = lowerLeft.getX() + (width / 2) - 1;
    return new Position(doorX, doorY);
  }

}
