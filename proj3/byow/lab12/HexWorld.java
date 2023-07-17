package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
  private static final int WIDTH = 50;
  private static final int HEIGHT = 50;

  private static final long SEED = 2873124;
  private static final Random RANDOM = new Random(SEED);


  /** A private class to define the position in the space. */
  private static class Position {
    int x;
    int y;
    private Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    private Position shift(int dx, int dy) {
      return new Position (this.x + dx, this.y + dy);
    }
  }


  /**
   * Draw a row of tiles to the board, anchored at a given position.
   * */
  public static void drawRole(TETile[][] tiles, Position p, TETile tile, int length) {
    for (int dx = 0; dx < length; dx ++) {
      tiles[p.x + dx][p.y] = tile;
    }
  }

  /**
   * Adds the hexagon to the world at the position P of the size S.
   * */
  public static void addHexagon(TETile[][] tiles, Position p, TETile t, int size) {
    if (size < 2) return;

    addHexagonHelper(tiles, p, t, size - 1, size);
  }

  /**
   * Helper method, for add hexagon.
   * */
  private static void addHexagonHelper(TETile[][] tiles, Position p, TETile tile, int blank, int filledTile) {
    //Draw this row.
    Position startOfRow = p.shift(blank, 0);
    drawRole(tiles, startOfRow, tile, filledTile);

    //Draw the remained part of hexagon recursively
    if (blank > 0) {
      Position nextP = p.shift(0, -1);
      addHexagonHelper(tiles, nextP, tile, blank-1, filledTile+2);
    }
    //Draw this row reflection.
    Position startOfReflectedRow = startOfRow.shift(0, -(2*blank + 1));
    drawRole(tiles, startOfReflectedRow, tile, filledTile);
  }

  /**
   * Adds a column of num hexagons, each of whose biomes are chosen randomly to the
   * world at position P. Each of hexagons are of size SIZE.
   * */
  public static void addHexColumn(TETile[][] tiles, Position p, int size, int num) {
    if (num < 1) return;

    //Draw this hexagon
    addHexagon(tiles, p, randomBiome(), size);

    //Draw n-1 hexagons below it
    if (num > 1) {
      Position bottom = getPositionNeighbor(p, size);
      addHexColumn(tiles, bottom, size, num-1);
    }
  }

  /**
   * Gets the position of bottom neighbor of a hexagon at position p.
   * Size is the size of hexagon we are tessellating.
   */
  public static Position getPositionNeighbor(Position p, int size) {
    return p.shift(0, -2*size);
  }


  /**
   * Fills the given 2D array of tiles with RANDOM tiles.
   * @param tiles
   */
  public static void fillBoardWithNothing(TETile[][] tiles) {
    int height = tiles[0].length;
    int width = tiles.length;
    for (int x = 0; x < width; x += 1) {
      for (int y = 0; y < height; y += 1) {
        tiles[x][y] = Tileset.NOTHING;
      }
    }
  }

  /**
   * Picks a RANDOM biome tile.
   */
  private static TETile randomBiome() {
    int tileNum = RANDOM.nextInt(3);
    switch (tileNum) {
      case 0: return Tileset.GRASS;
      case 1: return Tileset.FLOWER;
      case 2: return Tileset.SAND;
      case 3: return Tileset.TREE;
      case 4: return Tileset.MOUNTAIN;
      case 5: return Tileset.WALL;
      default: return Tileset.NOTHING;
    }
  }


  /**
   * Draws the hexagonal world.
   * */
  public static void drawWorld(TETile[][] tiles, Position p, int hexSize, int tessSize) {
    //Draw the first hexagon column
    addHexColumn(tiles, p, hexSize, tessSize);

    //Expand up and to the right
    for (int i = 1; i < tessSize; i++) {
      p = getTopRightNeighbor(p, hexSize);
      addHexColumn(tiles, p, hexSize, tessSize+i);
    }

    //Expand down and to the right
    for (int i = tessSize - 2; i >= 0; i--) {
      p = getBottomRightNeighbor(p, hexSize);
      addHexColumn(tiles, p, hexSize, tessSize+i);
    }
  }

  /**
   * Gets the position of the top right neighbor of a hexagon at position P.
   * N is the size of hexagon we are tessellating.
   * */
  public static Position getTopRightNeighbor(Position p, int size) {
    return p.shift(2*size-1, size);
  }

  /**
   * Gets the position of the bottom right neighbor of a hexagon at position P.
   * N is the size of hexagon we are tessellating.
   * */
  public static Position getBottomRightNeighbor(Position p, int size) {
    return p.shift(2*size-1, -size);
  }
  public static void main(String[] args) {
    TERenderer ter = new TERenderer();
    ter.initialize(WIDTH, HEIGHT);

    TETile[][] world = new TETile[WIDTH][HEIGHT];
    fillBoardWithNothing(world);
    Position anchor = new Position(12, 34);
    drawWorld(world, anchor, 3, 4);


    ter.renderFrame(world);
  }
}
