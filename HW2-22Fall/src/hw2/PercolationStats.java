package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private int experiment;
  private double[] res;

  public PercolationStats(int N, int T, PercolationFactory pf){
    if (N <= 0 || T <= 0) {
      throw new IllegalArgumentException("Invalid input");
    }
    res = new double[T];
    experiment = T;
    for (int i = 0; i<T; i++){
      res[i] = pf.prob();
    }
  }

  public double mean(){

    double result = 0.0;
    for (int i = 0; i<res.length; i++){
      result += res[i];
    }
    double mean = result/experiment;
    return mean;
  }

  public double stddev(){
    double result = 0.0;
    for (int i = 0; i<res.length; i++){
      result += Math.pow(res[i] - mean(),2);
    }
    double stddev = Math.pow(result/(experiment-1), 0.5);
    return stddev;
  }

  public double confidenceLow(){
    return mean()-1.96*stddev()/Math.pow(experiment, 0.5);
  }

  public double confidenceHigh(){
    return mean()+1.96*stddev()/Math.pow(experiment, 0.5);
  }
}
