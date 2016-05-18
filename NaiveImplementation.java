import java.util.HashSet;
import java.util.Set;

/**
 * File Name: NaiveImplementation.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 04/02/2016
 * Description: To determine the length and all possible longest sub-sequence
 *              given 2 input strings
 */

public class NaiveImplementation implements LCSInterface{
    int numberOfRecursiveCallLength;
    /**
     * Constructor of the class
     */
    public NaiveImplementation(){
        numberOfRecursiveCallLength = 0;
    }

    public int getNumberOfRecursiveCallLength() {
        return numberOfRecursiveCallLength;
    }
    /**
     * Function to determine the length of the longest subsquence
     * @param firstInput First input string
     * @param secondInput Second input string
     * @return length of the longest subsequence
     */
    @Override
    public int LengthLongestSubsequence(String firstInput, String secondInput){
        if(firstInput.length()==0 || secondInput.length()==0){
            return 0;
        }else if(firstInput.substring(firstInput.length()-1).
                equals(secondInput.substring(secondInput.length()-1))){
            numberOfRecursiveCallLength += 1;
            return LengthLongestSubsequence(firstInput.
                    substring(0,firstInput.length()-1),
                    secondInput.substring(0,secondInput.length()-1)) + 1;
        }else{
            numberOfRecursiveCallLength +=2;
            return Math.max(LengthLongestSubsequence(firstInput,
                    secondInput.substring(0,secondInput.length()-1)),
                    LengthLongestSubsequence(firstInput.substring(0,
                            firstInput.length()-1),secondInput));
        }

    }
    /**
     * Calculating all the subsets once length has been determined
     * @param firstInput First input string
     * @param secondInput Second input string
     * @return Set of subsequences
     */
    public Set<String> SubsequenceHelper(String firstInput, String secondInput){
        int lengthFirstInput = firstInput.length();
        int lengthSecondInput = secondInput.length();
        if (lengthFirstInput==0 || lengthSecondInput==0){
            Set<String> emptySet = new HashSet<>();
            emptySet.add("");
            return emptySet;
        }else if(firstInput.substring(lengthFirstInput-1).
                equals(secondInput.substring(lengthSecondInput-1))){
            Set<String> previousSet = SubsequenceHelper(firstInput.
                    substring(0,lengthFirstInput-1),
                    secondInput.substring(0,lengthSecondInput-1));
            Set<String> currentSet = new HashSet<>();
            for (String sequence:
                 previousSet) {
                String newSequence = sequence +
                        firstInput.substring(lengthFirstInput-1);
                currentSet.add(newSequence);
            }
            return currentSet;
        }else{
            Set<String> topSet = SubsequenceHelper(firstInput.
                    substring(0,lengthFirstInput-1),secondInput);
            Set<String> leftSet = SubsequenceHelper(firstInput,
                    secondInput.substring(0,lengthSecondInput-1));
            for (String sequence:topSet) {
                leftSet.add(sequence);
            }
            return leftSet;
        }
    }
    /**
     * Calculation of length and subsequence of 2 string
     * @param firstInputString first input string
     * @param secondInputString second input string
     * @return subsequence with its attributes
     */
    @Override
    public Set<String> LongestSubsequence(String firstInputString,
                                          String secondInputString){
        numberOfRecursiveCallLength = 0;
        int lengthLongestSubSequence = LengthLongestSubsequence(firstInputString,
                secondInputString);
        Set<String> allSubSequence = SubsequenceHelper(firstInputString,
                secondInputString);
        Set<String> longestSubSequence = new HashSet<>();
        for (String sequence: allSubSequence) {
            if(sequence.length()==lengthLongestSubSequence){
                longestSubSequence.add(sequence);
            }
        }
        return longestSubSequence;
    }
    /**
     * Main Method
     * @param args Command line Argument(Not used)
     */
    public static void main(String[] args){
        NaiveImplementation n = new NaiveImplementation();
        TestCaseGenerator t = new TestCaseGenerator(1, 102, 204, 5, 6);
        String[][] a = t.GenerateTestCases("binary");
        while(true){
            Set<String> result = n.LongestSubsequence(a[0][0],a[0][1]);

        }

    }
}
