import java.util.Random;

/**
 * File Name: TestCaseGenerator.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 04/03/16
 * Description: Generate test cases to test longest common sub-sequence
 */
public class TestCaseGenerator{
    private int numberOfTestCases;
    private int maxLength;
    private int minLength;
    private Random randomLength;
    private Random randomString;
    /**
     * Constructor for the test case generator
     * @param numberOfTestCases Number of test cases to be generated
     * @param stringSeed Seed to generate random strings
     * @param lengthSeed Seed to generate random length of string
     * @param maxLength Maximum length of test case that can be
     *                  generated
     */
    public TestCaseGenerator(int numberOfTestCases, int stringSeed,
                             int lengthSeed, int minLength,int maxLength){
        this.numberOfTestCases = numberOfTestCases;
        this.maxLength = maxLength;
        this.minLength = minLength;
        randomLength = new Random(lengthSeed);
        randomString = new Random(stringSeed);
    }
    /**
     * Helper function to generate random number in a certain range
     * @return a random integer
     */
    private int RandomLength(){
        return (randomLength.nextInt(maxLength-minLength) + (minLength) +1);
    }
    /**
     * Generate random string based on the characters and length of the string
     * @param length Length of the random string
     * @param sequenceChar set of characters from which string is generated
     * @return new string generated from the character sequence
     */
    private  String RandomString(int length, char[] sequenceChar){
        char[] stringArray = new char[length];
        for(int index=0;index<length;index++){
            stringArray[index] = sequenceChar[randomString.
                    nextInt(sequenceChar.length)];
        }
        return new String(stringArray);
    }
    /**
     * Generate random string pairs
     * @param charSequence character sequence for generating random string
     * @return randomly generated string pair
     */
    private String[] RandomPair(char[] charSequence){
        String[] sequencePair = new String[2];
        int lengthOfFirstSequence = RandomLength();
        int lengthOfSecondSequence = RandomLength();
        sequencePair[0] = RandomString(lengthOfFirstSequence, charSequence);
        sequencePair[1] = RandomString(lengthOfSecondSequence, charSequence);
        return sequencePair;
    }
    /**
     * Generate set of test cases
     * @param type type of test case
     * @return set of test cases
     */
    public String[][] GenerateTestCases(String type){
        String[][] testCases = new String[numberOfTestCases][2];
        char[] charSequence = null;
        if(type.equals("binary")){
            charSequence = "01".toCharArray();
        }else if(type.equals("alphabet")){
            charSequence = "ACGT".toCharArray();
        }else if(type.equals("alphanumeric")){
            charSequence = "ACGT01".toCharArray();
        }
        for(int index=0;index<numberOfTestCases;index++){
            testCases[index] = RandomPair(charSequence);
        }
        return testCases;
    }
    /**
     * Main function to test the class
     * @param args Command line arguments (ignored)
     */
    public static void main(String[] args){
        TestCaseGenerator testCaseGenerator = new TestCaseGenerator(1,
                101, 201, 500,600);
        String[][] testCases = testCaseGenerator.GenerateTestCases("alphanumeric");
        for(int outerIndex=0;outerIndex<1;outerIndex++){
            System.out.println("String 1:" + testCases[outerIndex][0] +
                    "\n String 2:" + testCases[outerIndex][1]);
        }
    }
}
