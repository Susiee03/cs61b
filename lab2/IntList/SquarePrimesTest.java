package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesDouble() {
        IntList lst = IntList.of(13, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("169 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesMany() {
        IntList lst = IntList.of(12, 15, 17, 19, 21, 22, 23);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("12 -> 15 -> 289 -> 361 -> 21 -> 22 -> 529", lst.toString());
        assertTrue(changed);

    }

    @Test
    public void testSquarePrimesNone() {
        IntList lst = IntList.of(12, 15, 21, 22);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("12 -> 15 -> 21 -> 22", lst.toString());
        assertFalse(changed);
    }
}
