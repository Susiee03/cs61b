package randomizedtest;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.*;

import org.junit.Test;
public class JUnitTest {
  @Test
  public void testThreeAddThreeRemove() {
    AListNoResizing<Integer> lst1 = new AListNoResizing<>();
    BuggyAList<Integer> lst2 = new BuggyAList<>();
    lst1.addLast(4);
    lst2.addLast(4);
    lst1.addLast(5);
    lst2.addLast(5);
    lst1.addLast(6);
    lst2.addLast(6);

    assertEquals(lst1.size(), lst2.size());
    assertEquals(lst1.removeLast(), lst2.removeLast());
    assertEquals(lst1.removeLast(), lst2.removeLast());
    assertEquals(lst1.removeLast(), lst2.removeLast());
  }

  @Test
  public void randomizedTest() {
    AListNoResizing<Integer> L = new AListNoResizing<>();
    BuggyAList<Integer> B = new BuggyAList<>();

    int N = 5000;
    for (int i = 0; i < N; i += 1) {
      int operationNumber = StdRandom.uniform(0, 3);
      if (operationNumber == 0) {
        // addLast
        int randVal = StdRandom.uniform(0, 100);
        L.addLast(randVal);
        B.addLast(randVal);
        //System.out.println("addLast(" + randVal + ")");
      } else if (operationNumber == 1) {
        // size
        int size = L.size();
        int size2 = B.size();
        assertEquals(size, size2);
        if (size > 0) {
          assertEquals(L.getLast(), B.getLast());

        }
      } else if (operationNumber == 2) {
        int size = L.size();
        int size2 = B.size();
        if (size > 0) {
          //System.out.println("Remove last: " + L.removeLast());
          assertEquals(L.removeLast(), B.removeLast());
        }
      }
    }
  }


}
