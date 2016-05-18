import javax.print.attribute.HashAttributeSet;
import java.util.HashSet;
import java.util.Set;

/**
 * File Name: NaiveImplementation.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 04/18/2016
 * Description: To determine the length and all possible longest sub-sequence
 *              given 2 input strings using memoized recursive algorithm
 */
public class MemoizationImplementation implements LCSInterface{
    public int numberOfRecursiveCall;

    /**
     * Interface Function that return number of recursive calls
     * @return number of recursive calls
     */
    public int getNumberOfRecursiveCallLength(){
        return numberOfRecursiveCall;
    }

    /**
     * Constructor of the class
     */
    public MemoizationImplementation(){
        numberOfRecursiveCall = 0;
    }

    /**
     * Helper Function to calculate longest sequence matrix
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @param lengthMatrix longest length sequence matrix
     */
    private void LongestSubsequenceHelper(String firstInput,
                                          String secondInput,
                                         int[][] lengthMatrix){
        if(firstInput.length()==0 || secondInput.length()==0){
            numberOfRecursiveCall += 1;
            lengthMatrix[firstInput.length()][secondInput.length()] = 0;
        }else if(lengthMatrix[firstInput.length()][secondInput.length()]>0){
            return;
        }
        else if(firstInput.substring(firstInput.length()-1).
                equals(secondInput.substring(secondInput.length()-1))){
            LongestSubsequenceHelper(firstInput.substring(0,firstInput.length()-1),
                    secondInput.substring(0,secondInput.length()-1), lengthMatrix);
            numberOfRecursiveCall += 1;
            lengthMatrix[firstInput.length()][secondInput.length()] =
                    lengthMatrix[firstInput.length()-1]
            [secondInput.length()-1] + 1;
        }else{
            LongestSubsequenceHelper(firstInput,
                    secondInput.substring(0,secondInput.length()-1), lengthMatrix);
            LongestSubsequenceHelper(firstInput.substring(0,
                            firstInput.length()-1),secondInput, lengthMatrix);
            numberOfRecursiveCall += 2;
            lengthMatrix[firstInput.length()][secondInput.length()] = Math.max(
                    lengthMatrix[firstInput.length()-1][secondInput.length()],
                    lengthMatrix[firstInput.length()][secondInput.length()-1]
            );
        }

    }

    /**
     * Function to calculate longest sequence matrix
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @return Length Matrix
     */
    public int[][] LengthOfSubsequenceMatrix(String firstInput,
                                             String secondInput){
        int[][] lengthMatrix = new int[firstInput.length()+1]
                [secondInput.length()+1];
        for(int counterFirstInput=0;
            counterFirstInput<firstInput.length()+1;counterFirstInput++){
            lengthMatrix[counterFirstInput][0] = 0;
        }
        for(int counterSecondInput=0;
            counterSecondInput<secondInput.length()+1; counterSecondInput++) {
            lengthMatrix[0][counterSecondInput] = 0;
        }

        LongestSubsequenceHelper(firstInput, secondInput, lengthMatrix);
        return lengthMatrix;
    }

    /**
     * Function to compute Length of the longest subsequence
     * @param firstInput First Input String
     * @param secondInput Second Input String
     */
    public int LengthLongestSubsequence(String firstInput,
                                        String secondInput){
        return LengthOfSubsequenceMatrix(firstInput, secondInput)
                [firstInput.length()][secondInput.length()];
    }

    /**
     * Function to recover longest subsequence
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @param lengthMatrix Length matrix
     * @param indexX row of the length matrix
     * @param indexY column of length matrix
     * @return
     */
    public Set<String> SubsequenceHelper(String firstInput, String secondInput,
                                         int[][] lengthMatrix,
                                         int indexX, int indexY){
        Set<String> previousSet;
        Set<String> currentSet = new HashSet<>();
        if (lengthMatrix[indexX][indexY]==0){
            currentSet.add("");
        }else if(lengthMatrix[indexX][indexY] ==
                (lengthMatrix[indexX-1][indexY-1]+1)
                && firstInput.charAt(indexX-1) == secondInput.charAt(indexY-1)){
            previousSet = SubsequenceHelper(firstInput, secondInput,
                    lengthMatrix, indexX-1, indexY-1);
            for(String sequence: previousSet){
                String newSequence = sequence + firstInput.charAt(indexX-1);
                currentSet.add(newSequence);
            }
        }else{
            int upperValue = lengthMatrix[indexX][indexY-1];
            int leftValue =  lengthMatrix[indexX-1][indexY];
            if(upperValue > leftValue){
                previousSet = SubsequenceHelper(firstInput, secondInput,
                        lengthMatrix, indexX, indexY-1);

            }else if(upperValue < leftValue){
                previousSet = SubsequenceHelper(firstInput, secondInput,
                        lengthMatrix, indexX-1, indexY);
            }else{
                previousSet = SubsequenceHelper(firstInput, secondInput,
                        lengthMatrix, indexX-1, indexY);
                previousSet.addAll(SubsequenceHelper(firstInput, secondInput,
                        lengthMatrix, indexX, indexY-1));
            }
            currentSet = previousSet;

        }
        return currentSet;
    }

    /**
     * Function to recover longest subsequence
     * @param firstInput First Input String
     * @param secondInput Second Input String
     * @return
     */
    public Set<String> LongestSubsequence(String firstInput, String secondInput){
        numberOfRecursiveCall = 0;
        int[][] lengthMatrix = LengthOfSubsequenceMatrix(firstInput, secondInput);
        return SubsequenceHelper(firstInput, secondInput,
                lengthMatrix, firstInput.length(), secondInput.length());
    }

    /**
     * Main Function
     * @param args: Command Line Arguments (ignored)
     */
    public static void main(String[] args){
        MemoizationImplementation m = new MemoizationImplementation();
        Set<String> result = m.LongestSubsequence("abcbdab","bdcaba");
        System.out.println(result);

    }
}
