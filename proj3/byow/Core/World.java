package byow.Core;

import byow.TileEngine.TETile;
import java.util.Random;

public class World {
  private final int WIDTH = 75;
  private final int HEIGHT = 45;
  private TETile[][] world;
  private Random random;
  public World(long seed) {
    world = new TETile[WIDTH][HEIGHT];
    random = new Random(seed);
    initializeWorld();
  }

  private void initializeWorld() {

  }
  public int getWidth() {
    return WIDTH;
  }

  public static void main(String[] args) {

  }
}
