package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns= new AList<>();
        AList<Double> time = new AList<>();
        AList<Integer> opCount = new AList<>();

        for (int i = 1000; i <= 64000; i *= 2) {
            int count = 0;
            AList<Integer> eg= new AList<>();
            Stopwatch sw = new Stopwatch();
            for (int j = 1; j <= i; j ++) {
                eg.addLast(j);
                count++;
            }
            double timeInSeconds = sw.elapsedTime();
            Ns.addLast(i);
            time.addLast(timeInSeconds);
            opCount.addLast(count * 1);
        }
        printTimingTable(Ns, time, opCount);

    }
}
