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
public class Room {
//  private Position upperLeft;
//  private Position upperRight;
//  private Position bottomRight;
  private Position bottomLeft;
  private int width;
  private int height;
  private static final int MAX_LENGTH = 10;


  /** Initialize the room. */
  public Room(Position bottomLeft, int width, int height) {//Position upperRight, Position botttomRight, Position upperLeft, ) {
      this.bottomLeft = bottomLeft;
      this.width = width;
      this.height = height;
      //this.upperRight = upperRight;
      //this.bottomRight = botttomRight;
      //this.upperLeft = upperLeft;

  }

  /** Get the upper left coordination of the room. */
  public Position getUpperLeft() {
    int yP = bottomLeft.getY() + height;
    return new Position(bottomLeft.getX(), yP);
  }

  /** Get the bottom left coordination of the room. */
  public Position getBottomLeft() {
    return bottomLeft;
  }

  /** Get the upper right coordination of the room. */
  public Position getUpperRight() {
    int xP = bottomLeft.getX() + width;
    int yP = bottomLeft.getY() + height;
    return new Position (xP, yP);
  }

  /** Get the upper left coordination of the room. */
  public Position getBottomRight() {
    int xP = bottomLeft.getX() + width;
    return new Position(xP, bottomLeft.getY());
  }

  /** Get the center position of the room. */
/*  public double[] getCenter() {
    double[] center = new double[2];
    center[0] = (double)(upperLeft.getX() + upperRight.getX())/2;   //xMiddle
    center[1] = (double) (bottomLeft.getY() + upperLeft.getY())/2;  //y middle
    return center;
  }

 */

  /** Get the center distance between two rooms. */
/*  public double getDistance(Room r) {
    double[] center = getCenter();
    double[] centerR = r.getCenter();
    double width = center[0] - centerR[0];
    double height = center[1] - centerR[1];
    double result = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
    return result;
  }
  */


  /** Whether two rooms are overlapped. */
  public boolean isOverlapped(Room r) {
    Position rUpperLeft = r.getUpperLeft();
    Position rLowerLeft = r.getBottomLeft();
    Position rUpperRight = r.getUpperRight();
    Position rLowerRight = r.getBottomRight();
    return contains(rUpperRight) && contains(rLowerLeft) && contains(rUpperLeft) && contains(rLowerRight);
  }

  /** Check whether the point is inside the room. */
  private boolean contains(Position p) {
    Position uL = getUpperLeft();
    Position bL = getBottomLeft();
    Position uR = getUpperRight();
    if (uL.getX() <= p.getX() && p.getX() <= uR.getX()) {
      if (bL.getY() <= p.getY() && p.getY() <= uL.getY()) {
        return true;
      }
    }
    return false;
  }

  /** Draw a single room in the world. */
  public void drawRoom(TETile[][] world) {
    int height = getUpperLeft().getY() - getBottomLeft().getY();
    int width = getUpperRight().getX() - getUpperLeft().getX();

    //print the wall of the room
    for (int i = 0; i < width; i++) {
      world[bottomLeft.getX() + i][bottomLeft.getY()] = Tileset.WALL;
      world[getUpperLeft().getX() + i][getUpperLeft().getY()] = Tileset.WALL;
    }
    for (int j = 0; j < height; j++) {
      world[bottomLeft.getX()][bottomLeft.getY() + j] = Tileset.WALL;
      world[getBottomRight().getX()][getBottomRight().getY() + j] = Tileset.WALL;
    }

    //print the part inside room
    for (int k = 1; k < width-1; k++) {
      for (int l = 1; l < height-1; l++) {
        world[bottomLeft.getX() + k][bottomLeft.getY() + l] = Tileset.TREE;
      }
    }
  }

  /** Generate rooms in world, and return all generated rooms. Make sure there is no overlap
   * between rooms.
   */
  public List<Room> roomGenerator(TETile[][] world) {
    List<Room> allRooms = new ArrayList<>();

  }

  /** Generate a room at a random position in world, it doesn't exceed the world edge. */
  private Room generateRoom(Random random, TETile[][] world) {
    int randomX =  random.nextInt(world.length);
    int randomY = random.nextInt(world[0].length);
    Position randomBottomLeft = new Position(randomX, randomY);
    int width = random.nextInt(MAX_LENGTH);
    int height = random.nextInt(MAX_LENGTH);

    //Generated room doesn't out of edge
    if (randomX + width >= world.length ) {
      while (randomX + width >= world.length) {
        width --;
      }
    }
    if (randomY + height >= world[0].length) {
      while (randomY + height >= world[0].length) {
        height --;
      }
    }
    Room generatedRoom = new Room(randomBottomLeft, width, height);
    return generatedRoom;
  }
}
