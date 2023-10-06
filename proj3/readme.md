# Build Your Own World #
**A 2D tile-based game made for Josh Hug's CS61B at the University of California, Berkeley**

Written in Java, our two-dimensional tile-based game finds itself
well within the intersection of Nintendo's *Mario* and Namco's *Pac-Man*. The game supports basic movements using the *'W'*, *'A'*, *'S'*, and *'D'* keys
and special interactions with certain tiles within the world.

Each world is pseudo-randomly generated from a seed entered by the current user; this randomness manifests in a unique player experience every time.
The game supports any seed up Java's maximum long value (9,223,372,036,854,775,807). The world consists of rooms that are connected to each other
via hallways that sprawl across the world.

The objective of the game is to collect **10 coins** in a total of **150 steps**. To add elements of strategy to the game, we have introduced
portals within each room that will randomly transport the current player to another random portal anywhere within the world. Additionally, we have implemented
a **coin exchange**, where a user can press **'E'** to exchanged two of their collected coins for 25 additional steps.

The game supports the creation of new worlds via a random seed, but also supports a `Save and Quit` feature. This allows for a user to save their current game and
return back at a later time in order to continue. The current state of the world is only saved if the user chooses to do so. Additionally, we have implemented a
`Replay Mode`, in which a user can "play back" their last saved game and watch their movements.

At all times, the playing screen will have a Heads-Up Display rendered at its top. Thus HUD contains the current number of coins collected, the number of steps remaining,
and the current tile that a user's mouse is currently hovering over.


## Classes and Data Structures

Classes include *Main*, *Engine*, *World*, *Room*, and *Position*.

#### World
The `World` class is largely responsible for the random generation
of the world (a 2d-Array of Tiles). Its constructor takes in a `SEED` provided by the
user input, and then creates a random world using that seed.

**Instance variables**: int WIDTH of the world window, int HEIGHT of the world window, Random random to generate pseudorandom numbers, and ArrayList<Room> room that keeps track of all Room objects.

#### Room
The `Room` class stores information about the rooms that occupy space within the world.
Each `Room` instance has variables describing its dimensions, and maps its four
corners to instance variables to account for overlap.

**Instance variables**: int width of the room, int height of the room, Position lowerLeft corner, Position upperRight corner, Position upperLeft corner, Position lowerRight corner, Position horizontalDoor of a door that is oriented horizontally, Position verticalDoor of a door that is oriented vertically, and Random random to generate pseudorandom numbers.

#### Position
The `Position` class is representative of a given location within the world. It represents
an (x,y) pair and contains the corresponding instance attributes to do so.

**Instance variables**: int x specifying the horizontal axis location and int y specifying the vertical axis location.

## Algorithms

#### World
1. **Constructor**: Takes a seed (long) that will be used to deterministically create
   the World object.

2. **createWorld**: Returns a 2D array of Tiles that represents the
   world after adding a specified number of random rooms and their corresponding hallways.

3. **setEmptyWorld**: Takes in a 2D array of TETiles and iterates through all tiles within the world and fills them with Tileset.WATER.

4. **setCorners**: Takes in a 2D array of TETiles and a singular TETile and sets all corners of every Room within the ArrayList of Rooms to the given TETile.

5. **fixDoors**: Takes in a 2D array of TETiles and sets all doors of every Room within the World instance to Tileset.UNLOCKED_DOOR.

6. **fixTilesAroundHorizontalDoor**: Takes in a 2D array of TETiles and a Position and checks each of the diagonal tiles respective to the given Position to see if they are Tileset.WATER. If this is the case, change them to Tileset.TREE.

7. **fixTilesAroundVerticalDoor**: Takes in a 2D array of TETiles and a Position and checks each of the diagonal tiles respective to the given Position to see if they are Tileset.WATER. If this is the case, change them to Tileset.TREE.

8. **addNRooms**: Takes in a 2D array of TETiles and an int (numRooms) and adds numRooms number of random Rooms to the given world.

9. **createAllHallways**: Takes in a 2D array of TETiles and creates all the hallway paths stemming from all the open doors within each Room object.

10. **generateRandomRoom**: Returns a Room of random width and height using the seed from the World class.

11. **isValidRoom**: Takes in a Room object and returns whether or not the Room should be added to the world.

12. **addRandomRoom**: Takes in a 2D array of TETiles and adds a random Room instance to its respective location using its lowerLeft and upperRight corners. Using the seed from the World class, this method will generator a random room, and then using that random Room's dimensions and corners, add the appropriate tiles to the world.

13. **createFloor**: Takes in a 2D array of TETiles and a Room object and lays down all Tileset.GRASS tiles onto the given area of a Room instance.

14. **drawVerticalWalls**: Takes in a 2D array of TETiles and a Room object and draws the vertical walls of the given Room instance.

15. **drawHorizontalWalls**: Takes in a 2D array of TETiles and a Room object and draws the horizontal walls of the given Room instance.

16. **openDoors**: Takes in a 2D array of TETiles and a Room object and opens the tile at the given Room's door Position position by making it Tileset.MOUNTAIN.

17. **tunnelLeft**: Takes in a 2D array of TETiles and a Room object and starting at the given door of the Room, builds a hallway to the left until it arrives at a wall (Tileset.TREE) or at the edge of the world window.

18. **tunnelRight**: Takes in a 2D array of TETiles and a Room object and starting at the given door of the Room, builds a hallway to the right until it arrives at a wall (Tileset.TREE) or at the edge of the world window.

19. **tunnelUp**: Takes in a 2D array of TETiles and a Room object and starting at the given door of the Room, builds a hallway up until it arrives at a wall (Tileset.TREE) or at the edge of the world window.

20. **tunnelDown**: Takes in a 2D array of TETiles and a Room object and starting the given door of the Room, builds a hallway down until it arrives at a wall (Tileset.TREE) or at the edge of the world window.

21. **fixCorners**: Takes in a 2D array of TETiles and two ints (x and y) and fixes hallways that travel directly into the corners of a given room.

#### Room
1. **Constructor**: The Room constructor takes in a `Random` object that will be used
   to randomly determine the dimensions and starting location of a given room. Rooms are
   constructed with minimum and maximum side lengths set.

2. **randomSideLength**: Returns a random side length (Integer) that is between the specified minimum and maximum range.

3. **randomStartingPosition**: Returns a random starting Position object that serves as the Room's lowerLeft corner.

4. **getUpperRight**: Takes in an int inputWidth, int inputHeight, and Position inputLowerLeft and returns the corresponding upperRight Position of a room given its width, height, and its lowerLeft Position.

5. **getUpperLeft**: Takes in an int inputHeight and Position inputLowerLeft and returns the corresponding upperLeft Position of a Room.

6. **getLowerRight**: Takes in an int inputWidth and Position inputLowerLeft and returns the corresponding lowerRight Position of a Room.

7. **isInside**: Takes in a Position and returns a boolean describing whether the Position lies within the corners of a Room.

8. **sideOverlap**: Takes in a Room and returns a boolean describing whether the input Room possesses no corners that lie within the corners of another Room but still overlaps.

9. **getHorizontalDoor**: Returns the Position of a Room's horizontally-facing door.

10. **getVerticalDoor**: Returns the Position of a Room's vertically-facing door.

#### Position
1. **Constructor**: The Position constructor takes in an int xPos and int yPos and sets the Position object's x and y instance variables to those passed in.

2. **getX:** Returns the x-value that is mapped to a given `Position` instance.

3. **getY:** Returns the y-value that is mapped to a given `Position` instance.


