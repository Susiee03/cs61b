package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class World {
  protected static final int WIDTH = 75;
  protected static final int STARTINGHEIGHT = 41;
  protected static final int HUDSIZE = 4;
  protected static final int HEIGHT = (STARTINGHEIGHT + HUDSIZE);
  protected static final int SCORELIMIT = 10;
  protected static final int STEPLIMIT = 100;

  protected Random random;
  protected ArrayList<Room> rooms = new ArrayList<>();
  protected Position avatarPosition;
  protected int score;
  protected int stepsRemaining = STEPLIMIT;
  protected boolean wonGame;
  protected TETile avatarTile = Tileset.AVATAR;
  protected TETile landTile = Tileset.WALL;
  protected  TETile oceanTile = Tileset.WATER;
  /** Creates an instance of the World class with a given integer seed. To be used
   * within the Engine class and Main class. */
  public World(long longSeed) {
    this.random = new Random(longSeed);
  }

  /** Returns a created world to the engine class with all tiles filled in. */
  public TETile[][] createWorld() {
    TETile[][] world = new TETile[WIDTH][HEIGHT];

    setEmptyWorld(world);
    addNRooms(world, 20);
    setCorners(world, Tileset.LOCKED_DOOR);
    createAllHallways(world);
    setCorners(world, Tileset.SAND);
    fixDoors(world);
    closeHallwayOpenings(world);

    setAvatarPosition(world, rooms.get(0).horizontalDoor);
    setTreasurePositions(world, rooms);

    return world;
  }

  /** Iterates through all tiles within the world and fills them with oceanTile. */
  public void setEmptyWorld(TETile[][] world) {
    setWorldBuffer(world);
    for (int x = 0; x < WIDTH; x += 1) {
      for (int y = 0; y < STARTINGHEIGHT; y += 1) {
        world[x][y] = oceanTile;
      }
    }
  }

  /** Sets the given Position p within the 2D-Representation of the world to avatarTile. */
  public void setAvatarPosition(TETile[][] world, Position p) {
    this.avatarPosition = p;
    world[avatarPosition.getX()][avatarPosition.getY()] = avatarTile;
  }

  /** Sets the given Position p within the 2D Tile array of the world to Tileset.TREASURE. */
  public void setTreasurePositions(TETile[][] world, ArrayList<Room> inputRooms) {
    for (Room room : inputRooms) {
      Position p = room.verticalDoor;
      world[p.getX()][p.getY()] = Tileset.COIN;
    }
  }

  /** Fixes the issue of openings in hallways offset by one tile. */
  public void closeHallwayOpenings(TETile[][] world) {
    for (int x = 0; x < WIDTH; x++) {
      for (int y = 0; y < HEIGHT; y++) {
        if (world[x][y] == landTile) {
          if (world[x + 1][y] == oceanTile) {
            world[x + 1][y] = Tileset.SAND;
          }
          if (world[x - 1][y] == oceanTile) {
            world[x - 1][y] = Tileset.SAND;
          }
          if (world[x][y + 1] == oceanTile) {
            world[x][y + 1] = Tileset.SAND;
          }
          if (world[x][y - 1] == oceanTile) {
            world[x][y - 1] = Tileset.SAND;
          }
          if (world[x + 1][y + 1] == oceanTile) {
            world[x + 1][y + 1] = Tileset.SAND;
          }
          if (world[x - 1][y - 1] == oceanTile) {
            world[x - 1][y - 1] = Tileset.SAND;
          }
          if (world[x - 1][y + 1] == oceanTile) {
            world[x - 1][y + 1] = Tileset.SAND;
          }
          if (world[x + 1][y - 1] == oceanTile) {
            world[x + 1][y - 1] = Tileset.SAND;
          }
        }
      }
    }
  }

  /** Logic behind the rendering of the World world attribute. */
  private TETile[][] adjustWorldForMove(TETile[][] world, Position newPosition, Position currPosition) {
    if (world[newPosition.getX()][newPosition.getY()] == Tileset.COIN) {
      score++;
    }
    if (world[newPosition.getX()][newPosition.getY()] == oceanTile) {
      world[newPosition.getX()][newPosition.getY()] = Tileset.SAND;
    }
    if (world[newPosition.getX()][newPosition.getY()] != Tileset.SAND
            && world[newPosition.getX()][newPosition.getY()] != oceanTile) {
      world[currPosition.getX()][currPosition.getY()] = landTile;
      if (world[newPosition.getX()][newPosition.getY()] == Tileset.GRASS) {
        Room teleport = rooms.get(random.nextInt(rooms.size()));
        while (teleport.blockPosition.equals(newPosition)) {
          teleport = rooms.get(random.nextInt(rooms.size()));
        }
        setAvatarPosition(world, teleport.blockPosition);
        world[newPosition.getX()][newPosition.getY()] = landTile;
        return world;
      } else {
        world[newPosition.getX()][newPosition.getY()] = avatarTile;
      }
      this.stepsRemaining--;
      this.avatarPosition = newPosition;
    }
    checkFinish();
    return world;
  }

  /** Moves the corresponding Avatar up one position within the world. If the next move
   * will be into a wall, then the program will prohibit its movement. */
  public TETile[][] moveAvatarUp(TETile[][] world) {
    Position currPosition = this.avatarPosition;
    Position newPosition = new Position(currPosition.getX(), currPosition.getY() + 1);
    return adjustWorldForMove(world, newPosition, currPosition);
  }

  /** Moves the corresponding Avatar down one position within the world. If the next move
   *  will be into a wall, then the program will prohibit its movement. */
  public TETile[][] moveAvatarDown(TETile[][] world) {
    Position currPosition = this.avatarPosition;
    Position newPosition = new Position(currPosition.getX(), currPosition.getY() - 1);
    return adjustWorldForMove(world, newPosition, currPosition);
  }

  /** Moves the corresponding Avatar left one position within the world. If the next move
   *  will be into a wall, then the program will prohibit its movement. */
  public TETile[][] moveAvatarLeft(TETile[][] world) {
    Position currPosition = this.avatarPosition;
    Position newPosition = new Position(currPosition.getX() - 1, currPosition.getY());
    return adjustWorldForMove(world, newPosition, currPosition);
  }

  /** Moves the corresponding Avatar right one position within the world. If the next move
   *  will be into a wall, then the program will prohibit its movement. */
  public TETile[][] moveAvatarRight(TETile[][] world) {
    Position currPosition = this.avatarPosition;
    Position newPosition = new Position(currPosition.getX() + 1, currPosition.getY());
    return adjustWorldForMove(world, newPosition, currPosition);
  }

  /** Reduces user's score by 2 in exchange for 25 extra steps */
  protected void purchaseSteps() {
    if (this.score < 2) {
      return;
    }
    this.score -= 2;
    this.stepsRemaining += 25;
  }

  /** Sets wonGame to true if the SCORELIMIT is reached and false
   * if stepsRemaining equals zero. */
  public void checkFinish() {
    if (this.score == SCORELIMIT) {
      this.wonGame = true;
    } else if (this.stepsRemaining == 0) {
      this.wonGame = false;
    }
  }

  /** Iterates through all tiles within the world HUD buffer and fills them with
   * Tileset.NOTHING. */
  public void setWorldBuffer(TETile[][] world) {
    for (int x = 0; x < WIDTH; x += 1) {
      for (int y = STARTINGHEIGHT; y < HEIGHT; y += 1) {
        world[x][y] = Tileset.NOTHING;
      }
    }
  }

  /** Sets all corners of every Room within the ArrayList to the given TETile object. */
  public void setCorners(TETile[][] world, TETile tile) {
    for (Room room : rooms) {
      world[room.lowerLeft.getX()][room.lowerLeft.getY()] = tile;
      world[room.upperRight.getX() - 1][room.upperRight.getY() - 1] = tile;
      world[room.upperLeft.getX()][room.upperLeft.getY()] = tile;
      world[room.lowerRight.getX()][room.lowerRight.getY()] = tile;
    }
  }

  /** Sets all doors of every Room within the World instance to Tileset.ARROW. */
  public void fixDoors(TETile[][] world) {
    for (Room room : rooms) {
      Position horDoor = room.horizontalDoor;
      Position vertDoor = room.verticalDoor;

      world[horDoor.getX()][horDoor.getY()] = Tileset.UNLOCKED_DOOR;
      world[vertDoor.getX()][vertDoor.getY()] = Tileset.UNLOCKED_DOOR;

      fixTilesAroundHorizontalDoor(world, horDoor);
      fixTilesAroundVerticalDoor(world, vertDoor);
    }
  }

  /** Checks each of the diagonal tiles respective to a given Position to see
   * if they are oceanTile. If this is the case, change them to Tileset.SAND objects. */
  public void fixTilesAroundHorizontalDoor(TETile[][] world, Position p) {
    int x = p.getX();
    int y = p.getY();

    if (world[x - 1][y - 1] == oceanTile) {
      world[x - 1][y - 1] = Tileset.SAND;
      world[x - 2][y - 1] = Tileset.SAND;
    }
    if (world[x - 1][y + 1] == oceanTile) {
      world[x - 1][y + 1] = Tileset.SAND;
      world[x - 2][y + 1] = Tileset.SAND;
    }
    if (world[x + 1][y - 1] == oceanTile) {
      world[x + 1][y - 1] = Tileset.SAND;
      world[x + 2][y - 1] = Tileset.SAND;
    }
    if (world[x + 1][y + 1] == oceanTile) {
      world[x + 1][y + 1] = Tileset.SAND;
      world[x + 2][y + 1] = Tileset.SAND;
    }
  }

  /** Checks each of the diagonal tiles respective to a given Position to see if they are
   * oceanTile. If this is the case, change them to Tileset.SAND objects. */
  public void fixTilesAroundVerticalDoor(TETile[][] world, Position p) {
    int x = p.getX();
    int y = p.getY();

    if (world[x - 1][y - 1] == oceanTile) {
      world[x - 1][y - 1] = Tileset.SAND;
      world[x - 1][y - 2] = Tileset.SAND;
    }
    if (world[x - 1][y + 1] == oceanTile) {
      world[x - 1][y + 1] = Tileset.SAND;
      world[x - 1][y + 2] = Tileset.SAND;
    }
    if (world[x + 1][y - 1] == oceanTile) {
      world[x + 1][y - 1] = Tileset.SAND;
      world[x + 1][y - 2] = Tileset.SAND;
    }
    if (world[x + 1][y + 1] == oceanTile) {
      world[x + 1][y + 1] = Tileset.SAND;
      world[x + 1][y + 2] = Tileset.SAND;
    }
  }

  /** Adds numRooms number of random Room instances to the given world. */
  public void addNRooms(TETile[][] world, int numRooms) {
    for (int i = 0; i < numRooms; i++) {
      addRandomRoom(world);
    }
  }

  /** Takes in a world and creates all the hallway paths stemming from all the open Doors
   * within each room object. */
  public void createAllHallways(TETile[][] world) {
    for (Room room : rooms) {
      if (room.horizontalDoor.getX() == room.lowerLeft.getX()) {
        tunnelLeft(world, room);
      } else {
        tunnelRight(world, room);
      }
      if (room.verticalDoor.getY() == room.lowerLeft.getY()) {
        tunnelDown(world, room);
      } else {
        tunnelUp(world, room);
      }
    }
  }

  /** Returns a room of Random WIDTH and HEIGHT using the seed from the World class. */
  public Room generateRandomRoom() {
    Room room = new Room(random);
    while (!isValidRoom(room)) {
      room = new Room(random);
    }
    return room;
  }

  /** Returns whether or not a Room object should be added to the world. */
  public boolean isValidRoom(Room newRoom) {
    for (Room room : rooms) {
      boolean cond1 = room.isInside(newRoom.lowerLeft) || room.isInside(newRoom.upperRight);
      boolean cond2 = newRoom.isInside(room.lowerLeft) || newRoom.isInside(room.upperRight);
      boolean cond3 = room.isInside(newRoom.upperLeft) || room.isInside(newRoom.lowerRight);
      boolean cond4 = newRoom.isInside(room.upperLeft) || newRoom.isInside(room.lowerRight);
      boolean cond5 = room.sideOverlap(newRoom);
      if (cond1 || cond2 || cond3 || cond4 || cond5) {
        return false;
      }
    }
    return newRoom.width >= Room.MIN_SIDE_LENGTH && newRoom.height >= Room.MIN_SIDE_LENGTH;
  }

  /**
   * Adds a random Room instance to its respective location using its lowerLeft and
   * upperRight corners. Using the seed from the World class, this method will
   * generate a random room, and then using that random Room's dimensions and corners,
   * add the appropriate tiles to the world.
   */
  public void addRandomRoom(TETile[][] world) {
    Room room = generateRandomRoom();
    rooms.add(room);

    createFloor(world, room);
    drawVerticalWalls(world, room);
    drawHorizontalWalls(world, room);
    openDoors(world, room);
    placeBlocks(world, room);
  }

  /** Lays down all landTile tiles onto the given area of a Room instance. */
  public void createFloor(TETile[][] world, Room inputRoom) {
    Position lowerLeft = inputRoom.lowerLeft;
    Position upperRight = inputRoom.upperRight;

    for (int x = lowerLeft.getX(); x < upperRight.getX(); x++) {
      for (int y = lowerLeft.getY(); y < upperRight.getY(); y++) {
        world[x][y] = landTile;
      }
    }
  }

  /** Draws the Vertical Walls of a given Room instance. */
  public void drawVerticalWalls(TETile[][] world, Room inputRoom) {
    Position lowerLeft = inputRoom.lowerLeft;
    Position upperRight = inputRoom.upperRight;
    for (int x = lowerLeft.getX(); x < upperRight.getX(); x += inputRoom.width - 1) {
      for (int y = lowerLeft.getY(); y < upperRight.getY(); y++) {
        world[x][y] = Tileset.SAND;
      }
    }
  }

  /** Draws the Horizontal Walls of a given Room instance. */
  public void drawHorizontalWalls(TETile[][] world, Room inputRoom) {
    Position lowerLeft = inputRoom.lowerLeft;
    Position upperRight = inputRoom.upperRight;

    for (int x = lowerLeft.getX(); x < upperRight.getX(); x++) {
      for (int y = lowerLeft.getY(); y < upperRight.getY(); y += inputRoom.height - 1) {
        world[x][y] = Tileset.SAND;
      }
    }
  }

  /** Opens the Tile at a given Room's doorLoc Position position by making it Tileset.MOUNTAIN. */
  public void openDoors(TETile[][] world, Room inputRoom) {
    Position doorX = inputRoom.horizontalDoor;
    Position doorY = inputRoom.verticalDoor;
    world[doorX.getX()][doorX.getY()] = Tileset.MOUNTAIN;
    world[doorY.getX()][doorY.getY()] = Tileset.MOUNTAIN;
  }

  /** Places a Tileset.SAND tile within the middle of each room. */
  public void placeBlocks(TETile[][] world, Room inputRoom) {
    int blockPosX = inputRoom.blockPosition.getX();
    int blockPosY = inputRoom.blockPosition.getY();
    world[blockPosX][blockPosY] = Tileset.GRASS;
  }

  /** Starting at the given doorPosition of a Room instance, this method will build a
   * Hallway to the left until it arrives at a wall or at the edge of the screen. */
  public void tunnelLeft(TETile[][] world, Room inputRoom) {
    Position startingPosition = inputRoom.horizontalDoor;
    int startingX = startingPosition.getX();
    int startingY = startingPosition.getY();
    while ((world[startingX][startingY] == oceanTile
            || world[startingX][startingY] == Tileset.MOUNTAIN) && startingX > 0) {
      world[startingX][startingY] = landTile;
      world[startingX][startingY - 1] = Tileset.SAND;
      world[startingX][startingY + 1] = Tileset.SAND;
      startingX--;
    }
    if (startingX == 0) {
      world[startingX][startingY - 1] = Tileset.SAND;
      world[startingX][startingY] = Tileset.SAND;
      world[startingX][startingY + 1] = Tileset.SAND;

    } else if (world[startingX][startingY] == Tileset.LOCKED_DOOR) {
      fixCorners(world, startingX, startingY);
    } else {
      world[startingX][startingY] = landTile;
    }
  }

  /** Starting at the given doorPosition of a Room instance, this method will build a Hallway
   * to the right until it arrives at a wall or at the edge of the screen. */
  public void tunnelRight(TETile[][] world, Room inputRoom) {
    Position startingPosition = inputRoom.horizontalDoor;
    int startingX = startingPosition.getX();
    int startingY = startingPosition.getY();
    while ((world[startingX][startingY] == oceanTile
            || world[startingX][startingY] == Tileset.MOUNTAIN)
            && startingX < World.WIDTH - 1) {
      world[startingX][startingY] = landTile;
      world[startingX][startingY - 1] = Tileset.SAND;
      world[startingX][startingY + 1] = Tileset.SAND;
      startingX++;
    }
    if (startingX == World.WIDTH - 1) {
      world[startingX - 1][startingY] = Tileset.SAND;
    } else if (world[startingX][startingY] == Tileset.LOCKED_DOOR) {
      fixCorners(world, startingX, startingY);
    } else {
      world[startingX][startingY] = landTile;
    }
  }

  /** Starting at the given doorPosition of a Room instance, this method will build a Hallway
   *  upwards until it arrives at a wall or at the edge of the screen. */
  public void tunnelUp(TETile[][] world, Room inputRoom) {
    Position startingPosition = inputRoom.verticalDoor;
    int startingX = startingPosition.getX();
    int startingY = startingPosition.getY();
    while ((world[startingX][startingY] == oceanTile
            || world[startingX][startingY] == Tileset.MOUNTAIN)
            && startingY < World.STARTINGHEIGHT - 1) {
      world[startingX][startingY] = landTile;
      world[startingX - 1][startingY] = Tileset.SAND;
      world[startingX + 1][startingY] = Tileset.SAND;
      startingY++;
    }
    if (startingY == World.STARTINGHEIGHT - 1) {
      world[startingX][startingY - 1] = Tileset.SAND;
    } else if (world[startingX][startingY] == Tileset.LOCKED_DOOR) {
      fixCorners(world, startingX, startingY);
    } else {
      world[startingX][startingY] = landTile;
    }
  }

  /** Starting at the given doorPosition of a Room instance, this method will build a Hallway
   *  to the left until it arrives at a wall or at the edge of the screen. */
  public void tunnelDown(TETile[][] world, Room inputRoom) {
    Position startingPosition = inputRoom.verticalDoor;
    int startingX = startingPosition.getX();
    int startingY = startingPosition.getY();
    while ((world[startingX][startingY] == oceanTile
            || world[startingX][startingY] == Tileset.MOUNTAIN)
            && startingY > 0) {
      world[startingX][startingY] = landTile;
      world[startingX - 1][startingY] = Tileset.SAND;
      world[startingX + 1][startingY] = Tileset.SAND;
      startingY--;
    }
    if (startingY == 0) {
      world[startingX][startingY] = Tileset.SAND;
      world[startingX - 1][startingY] = Tileset.SAND;
      world[startingX + 1][startingY] = Tileset.SAND;
    } else if (world[startingX][startingY] == Tileset.LOCKED_DOOR) {
      fixCorners(world, startingX, startingY);
    } else {
      world[startingX][startingY] = landTile;
    }
  }

  /** Fixes Hallways that travel directly into the corners of a given room. */
  public void fixCorners(TETile[][] world, int x, int y) {
    if (world[x - 1][y] == oceanTile) {
      world[x - 1][y] = Tileset.SAND;
    }
    if (world[x + 1][y] == oceanTile) {
      world[x + 1][y] = Tileset.SAND;
    }
    if (world[x][y - 1] == oceanTile) {
      world[x][y - 1] = Tileset.SAND;
    }
    if (world[x][y + 1] == oceanTile) {
      world[x][y + 1] = Tileset.SAND;
    }
  }

  /** Returns the WIDTH variable of the World Class. */
  public static int getWidth() {
    return WIDTH;
  }

  /** Returns the STARTINGHEIGHT variable of the World Class. */
  public static int getStartingHeight() {
    return STARTINGHEIGHT;
  }

  /** Returns the HEIGHT variable of the World Class. */
  public static int getHeight() {
    return HEIGHT;
  }

  /** Returns the HUDSIZE variable of the World Class. */
  public static int getHUDSize() {
    return HUDSIZE;
  }

  /** Returns the score variable of the World instance. */
  public int getScore() {
    return score;
  }

  /** Returns the stepsRemaining variable of the World instance. */
  public int getStepsRemaining() {
    return stepsRemaining;
  }
}
