package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    /** Draws the associated world of a World instance to the screen. */
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(World.WIDTH, World.HEIGHT);

        World world = new World(66);
        TETile[][] finalWorldFrame = world.createWorld();

        ter.renderFrame(finalWorldFrame);
    }
}
