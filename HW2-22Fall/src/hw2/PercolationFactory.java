package hw2;
import java.util.Random;

public class PercolationFactory {
    private int numOpen;
    private int size;
    private Random rand;
    private Percolation p;

    public Percolation make(int N) {
        size = N;
        numOpen = 0;
        return new Percolation(N);
    }

    public PercolationFactory(){
        rand = new Random();
        p = make(size);

    }

    public void openPercolation(int size){
        int openX = rand.nextInt(size);
        int openY = rand.nextInt(size);
        p.open(openX, openY);
        numOpen++;
    }

    public boolean checkPercolates(){
        if (p.percolates()){
            return true;
        }
        return false;
    }

    public double prob(){
        while (!checkPercolates()){
            openPercolation(size);
        }
        double result = numOpen/size*size;
        return result;
    }


}
