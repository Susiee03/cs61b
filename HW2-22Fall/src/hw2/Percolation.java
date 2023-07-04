package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/** 本来根据提示，创建了virtualTop和virtualBottom，为了防止backwater，决定去掉virtualBottom。
 * */
public class Percolation {
  private int sizeOfSides;
  private int numOfOpenSite;
  private int VirtualTop; //根据提示，为了速度

  //private int VirtualBottom;
  private boolean[] sites; //sites 是一共有多少个格子，用boolean来记录open or not
  private WeightedQuickUnionUF uf;
  private WeightedQuickUnionUF anti_back_wash;

  public Percolation(int N){
    if (N <= 0){
      throw new IllegalArgumentException();
    }
    sizeOfSides = N;
    VirtualTop = N*N;
    //VirtualBottom = N*N + 1;
    numOfOpenSite = 0;  //最开始默认都是blocked
    sites = new boolean[N*N]; //index start from 0, 一共有N*N个格子, 算上创建的虚拟顶点和底点
    uf = new WeightedQuickUnionUF(N*N+2); //一共有N*N个格子, 算上创建的虚拟顶点和底点
    //anti_back_wash = new WeightedQuickUnionUF(N*N +1);
    initialized();
  }

  /** Connect virtualTop and virtualBottom with the first col and last row respectively*/
  private void initialized(){
    for (int i=0; i<sizeOfSides; i++){
      //anti_back_wash.union(VirtualTop,i); //virtualTop和第一排连起来
      uf.union(VirtualTop,i);
    }

/*    for (int j = sizeOfSides * sizeOfSides - sizeOfSides; j < sizeOfSides * sizeOfSides; j++){
      uf.union(VirtualBottom,j); //virtualBottom 和最后一排连起来
    }*/
  }

  /**把N*N坐标转换成数字，为了好之后的connect*/
  private int xyTo1D(int row, int col){
    return row * sizeOfSides + col;
  }

  private void connectEmpty(int row, int col){
    validateIndex(row, col);
    for (int position: neighbors(row, col)){
      if (position!= -1 && sites[position] == true){
        uf.union(position, xyTo1D(row, col));
      }
    }
  }

/*  public void open(int row, int col){
    //sites[] =
    int booleanChangePosition = xyTo1D(row, col);
    if (!sites[booleanChangePosition]){
      sites[booleanChangePosition] = true;
      connectEmpty(row, col);
      numOfOpenSite++;
    }*/

    public void open(int i, int j){
      //sites[] =
      int booleanChangePosition = xyTo1D(i, j);
      if (!sites[booleanChangePosition]){
        sites[booleanChangePosition] = true;
        connectEmpty(i, j);
        numOfOpenSite++;
      }

  }


  /** show the specific grid's four direction neighbours. up, down, left, right  */

  private int[] neighbors(int row, int col){
    int[] neighbors = {-1, -1, -1, -1};
    validateIndex(row, col);
    int originNumber = xyTo1D(row, col);
/*    if (row == 0) { //第一行，上面就是自己
      neighbors[0]=originNumber;
    }*/
    if (row > 0){//不是第一行，算出来上方向的位置
      neighbors[0]=xyTo1D(row-1, col);
    }
    if (row != sizeOfSides -1){ //不是最后一行，算出下方向的位置
      neighbors[1]=xyTo1D(row+1, col);
    }
    if (col > 0){
      neighbors[2] = xyTo1D(row, col-1);
    }
    if (col != sizeOfSides -1){
      neighbors[3] = xyTo1D(row, col+1);
    }
    return neighbors;
  }


  public boolean isOpen(int row, int col){
    validateIndex(row, col);
    return sites[xyTo1D(row, col)];

  }


  /**check whether row and col are valid parameter*/
  private void validateIndex(int row, int col){
    if (row < 0 || col < 0 || row > sizeOfSides - 1 || col > sizeOfSides - 1){
      throw new IllegalArgumentException();
    }
  }


  public boolean isFull(int row, int col){
    validateIndex(row, col);
    return (uf.connected(VirtualTop, xyTo1D(row, col)) && isOpen(row, col));
  }

  public int numberOfOpenSites(){

    return numOfOpenSite;
  }

  public boolean percolates(){
    for (int i = 0; i<sizeOfSides; i++){
      if (uf.connected(VirtualTop, sizeOfSides*sizeOfSides-1-i)){
        return true;
      }
    }
    return false;
    //return uf.connected(VirtualTop, );
  }


  public static int letterNum(String s, int i){
    int ithChar = s.charAt(i);
    return ithChar - 'a' + 1;
  }
  /**
  public static void main(String[] args){
   String s = "bee";
   int rep = 0;
    for (int i=0; i<s.length(); i++){
      rep = rep*27;
      rep = rep + letterNum(s, i);
    }
    System.out.println(rep);
    //System.out.println(-3/2);
  }
   */
}
