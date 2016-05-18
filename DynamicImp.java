import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * File Name: NaiveImplementation.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 04/02/2016
 * Description: To determine the length and all possible longest
 * sub-sequence given 2 input strings using dynamic programming
 * version of Naive recursive algorithm
 */
public class DynamicImp implements LCSInterface{

    // Flags for direction matrix
    private int UP = 1;
    private int LEFT = 2;
    private int UP_LEFT = 3;
    private int SAME = 4;
    private int[][] directionMatrix;

    /**
     * Determining Longest Subsequence Matrix
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @param lengthMatrix Initial Length Matrix
     */
    private void LongestSubsequenceMatrixHelper(String firstInput,
                                                String secondInput,
                                                 int[][] lengthMatrix){
        for(int indexX=1;indexX<firstInput.length()+1;indexX++){
            for(int indexY=1; indexY<secondInput.length()+1; indexY++){
                if(firstInput.charAt(indexX-1)==
                        secondInput.charAt(indexY-1)){
                    lengthMatrix[indexX][indexY] =
                            lengthMatrix[indexX-1][indexY-1] + 1;
                    directionMatrix[indexX][indexY] = UP_LEFT;
                }else{
                    if (lengthMatrix[indexX-1][indexY]>
                            lengthMatrix[indexX][indexY-1]){
                        lengthMatrix[indexX][indexY] =
                                lengthMatrix[indexX-1][indexY];
                        directionMatrix[indexX][indexY] = UP;
                    }else if(lengthMatrix[indexX-1][indexY]
                            <lengthMatrix[indexX][indexY-1]){
                        lengthMatrix[indexX][indexY] =
                                lengthMatrix[indexX][indexY-1];
                        directionMatrix[indexX][indexY] = LEFT;
                    }else{
                        lengthMatrix[indexX][indexY] =
                                lengthMatrix[indexX-1][indexY];
                        directionMatrix[indexX][indexY] = SAME;
                    }

                }
            }
        }
    }

    /**
     * Function to find Length of Subsequence
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @return Length matrix
     */
    public int[][] LengthOfSubsequenceMatrix(String firstInput,
                                             String secondInput){
        int[][] lengthMatrix = new int[firstInput.length()+1]
                [secondInput.length()+1];
        directionMatrix = new int[firstInput.length()+1]
                [secondInput.length()+1];
        LongestSubsequenceMatrixHelper(firstInput, secondInput, lengthMatrix);
        return lengthMatrix;
    }


    @Override
    /**
     * Interface method that returns length of length of longest subsequence
     */
    public int LengthLongestSubsequence(String firstInput, String secondInput) {
                return LengthOfSubsequenceMatrix(firstInput,
                        secondInput)[firstInput.length()][secondInput.length()];

    }

    /**
     * Function that recovers all the subsequences
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @return Set of all recovered longest subsequence
     */
    public Set<String> SubsequenceHelper(String firstInput,
                                         String secondInput){
        List<List<String>> firstRow = new ArrayList<List<String>>();
        for(int i=0;i<secondInput.length()+1;i++){
            ArrayList<String> temp = new ArrayList<String>();
            temp.add("");
            firstRow.add(temp);
        }
        List<List<String>> newRow = new ArrayList<List<String>>(firstRow);
        for(int i=1;i<firstInput.length()+1;i++){

            for(int j=0;j<secondInput.length()+1;j++) {
                if (directionMatrix[i][j] == UP) {
                    continue;
                } else if (directionMatrix[i][j] == LEFT) {
                    List<String> temp = new ArrayList<>(newRow.get(j - 1));
                    newRow.remove(j);
                    newRow.add(j, temp);
                } else if (directionMatrix[i][j] == UP_LEFT) {
                    List<String> temp = new ArrayList<>(firstRow.get(j - 1));
                    List<String> new_array = new ArrayList<>();
                    for (String value : temp
                            ) {
                        value += secondInput.charAt(j - 1);
                        new_array.add(value);
                    }
                    newRow.remove(j);
                    newRow.add(j, new_array);
                } else if (directionMatrix[i][j] == SAME) {
                    List<String> temp = new ArrayList<>(newRow.get(j - 1));
                    List<String> currentArray = new ArrayList<>(newRow.get(j));
                    for (String value : temp) {
                        if (!currentArray.contains(value)) {
                            currentArray.add(value);
                        }
                    }
                    newRow.remove(j);
                    newRow.add(j, currentArray);
                }
            }
            firstRow = new ArrayList<>(newRow);
        }
        List<String> result = newRow.get(secondInput.length());
        return new HashSet<>(result);
    }

    /**
     * Interface Function to recover all longest subsequences
     * @param firstInputString First Input String
     * @param secondInputString Second Input String
     * @return Set of recovered longest sequences
     */
    @Override
    public Set<String> LongestSubsequence(String firstInputString,
                                          String secondInputString) {
        LengthLongestSubsequence(firstInputString, secondInputString);
        return SubsequenceHelper(firstInputString, secondInputString);
    }

    /**
     * Interface Function to return number of recursive calls
     * @return 0
     */
    @Override
    public int getNumberOfRecursiveCallLength() {
        return 0;
    }

    /**
     * Main Function
     * @param args command line (ignored)
     */
    public static void main(String[] args){
        DynamicImp d = new DynamicImp();
        Set<String> result = d.LongestSubsequence("abcbdab","bdcaba");
        System.out.println(result);
    }
}
