public class Larger {
    public static int larger(int x, int y){
        if (x>y) {
            return x;
        }
        return y;
    }
    public static void main(String[] args){
        System.out.println(larger(10, 4));
    }
}
/*
1, function must be declared as part of a class in java. A function that is part of a class, is called method
So in java, all functions are methods.
2, to define a function in java, we use public static. We will see alternate ways of defining functions later.
3, All parameters of the function must have the declare type, and the return value of the function must have the
declared type
4, Functions in java return only one value
 */
