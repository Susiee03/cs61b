package flik;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.*;
import org.junit.Test;


public class tests {

  @Test
  public void test128() {
      int i = 128;
      int j = 128;
      assertTrue(Flik.isSameNumber(i, j));
  }

}
