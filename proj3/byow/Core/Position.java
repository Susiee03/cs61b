package byow.Core;

/**
 * The position in world we generated.
 * */
public class Position {
  int x;  // x coordinate
  int y; // y coordinate

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /** Get the object's x coordinate. */
  public int getX() {
    return x;
  }

  /** Get the object's y coordinate. */
  public int getY() {
    return y;
  }
}
