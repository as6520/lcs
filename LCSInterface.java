import java.util.Set;

/**
 * File Name: NaiveImplementation.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 05/03/2016
 * Description: Used for Profiling
 */
public interface LCSInterface {
    public int LengthLongestSubsequence(String firstInput, String secondInput);
    public Set<String> LongestSubsequence(String firstInputString, String secondInputString);
    public int getNumberOfRecursiveCallLength();
}
