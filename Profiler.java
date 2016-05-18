import java.io.FileWriter;
import java.io.IOException;

/**
 * File Name: Profiler.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 04/02/2016
 * Description: To display the time taken to calculate the length of
 *              sub-sequence and set of sub-sequence
 */
public class Profiler {

    /**
     * Experiment 1 to find time taken by Hirschberg to compute longest subsequences
     * @param method LCS Interface of Hirschberg
     * @param numberOfTestCases Number of test cases over which data is averaged.
     */
    public  void Expermient1(LCSInterface method, int numberOfTestCases){
        TestCaseGenerator t = new TestCaseGenerator(numberOfTestCases,
                401, 206, 40000, 40010);
        String[][] testCases = t.GenerateTestCases("alphanumeric");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("exp1_alphanumeric.csv");
        }catch (IOException e){
            System.err.println("Unable to create file");
            System.exit(1);
        }
        try{
            fileWriter.append("Length of Sequence,Time taken\n");
        }catch (IOException e){
            System.out.println("Unable to write the headers");
        }
        for(int i=0;i<numberOfTestCases;i++){
            long startTime = System.currentTimeMillis();
            int lengthofSequence = method.
                    LengthLongestSubsequence(testCases[i][0], testCases[i][1]);
            long endTime = System.currentTimeMillis();
            try{
                fileWriter.append(Integer.
                        valueOf(lengthofSequence).toString()+","+
                        Long.valueOf((endTime-startTime)/1000) + "\n");
            }catch (IOException e){
                System.out.println("Unable to write the observation");
            }
            System.out.println("Test Case: " + (i+1) + " Done");

        }
        try{
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    /**
     * Helper function to determine the time taken be different algorithm
     * based on the test cases and number of test cases
     * @param method LCS Interface
     * @param testCases Input data
     * @param numberOfTestCases Size of Input
     * @return
     */
    public long Experiment2Helper(LCSInterface method, String[][] testCases,
                                  int numberOfTestCases){
        long average = 0;
        for(int i=0;i<numberOfTestCases;i++){
            long startTime = System.currentTimeMillis();
            method.LongestSubsequence(testCases[i][0],
                    testCases[i][1]);
            long endTime = System.currentTimeMillis();
            average += (endTime-startTime)/1000;

        }
        average /= numberOfTestCases;
        return average;
    }

    /**
     * Largest Input that can be processed in 10 seconds
     * @param allMethods LCS Interface Array
     * @param numberOfTestCases number of test cases to average the time
     */
    public void Experiment2(LCSInterface[] allMethods, int numberOfTestCases){
        TestCaseGenerator t = new TestCaseGenerator(numberOfTestCases,
                102, 204, 29, 30);
        String[][] testCasesBinary = t.GenerateTestCases("binary");

        long averageNaiveBinary =
                Experiment2Helper(allMethods[0], testCasesBinary, numberOfTestCases);

        System.out.println(averageNaiveBinary);
        t = new TestCaseGenerator(numberOfTestCases, 102, 204, 97, 98);
        testCasesBinary = t.GenerateTestCases("binary");
        long averageMemoized =
                Experiment2Helper(allMethods[1], testCasesBinary, numberOfTestCases);
        System.out.println(averageMemoized);
        t = new TestCaseGenerator(numberOfTestCases, 102, 204, 111, 112);
        testCasesBinary = t.GenerateTestCases("binary");
        long average_dynamic =
                Experiment2Helper(allMethods[2], testCasesBinary, numberOfTestCases);
        System.out.println(average_dynamic);
        t = new TestCaseGenerator(numberOfTestCases, 102, 204, 2000, 2100);
        testCasesBinary = t.GenerateTestCases("binary");
        long average_hirschberg =
                Experiment2Helper(allMethods[3], testCasesBinary, numberOfTestCases);
        System.out.println(average_hirschberg);
    }

    /**
     * Helper function to compute experiment 3 and experiment 4
     * @param method LCS interface method
     * @param stride Repeatation factor when time for one iteration is 0
     * @param numberOfTestCases Number of test cases over which time is averaged
     * @param lowerBound Lower bound for test case generation
     * @param upperBound Upper bound for test case generation
     * @param FileName File in which result is saved
     */
    private void AlgorithmHelper(LCSInterface method, int stride,
                                 int numberOfTestCases, int lowerBound,
                                     int upperBound, String FileName){

        int lengthSeed = 102;
        int stringSeed = 204;
        TestCaseGenerator t = null;
        FileWriter fileWriter = null;
        // Create File Writer and write header
        try{
            fileWriter = new FileWriter(FileName);
        }catch (IOException e){
            System.out.println("Failed To Create File");
            System.exit(1);
        }
        try{
            fileWriter.append("String Size,Recursion Binary,Time Binary, " +
                    "Recursion Alphabet, Time Alphabet," +
                    " Recursion Alphanumeric, Time Alphanumeric\n");
        }catch(IOException e){
            System.err.println("Unable to write headers");
        }

        String[][][] allTestCases = new String[3][numberOfTestCases][2];
        int[][] numberOfRecursion = new int[3][numberOfTestCases];
        double[][] methodTime = new double[3][numberOfTestCases];
        int[] averageRecursion = new int[3];
        double[] averageMethodTime = new double[3];

        // Generate test case for every value between a range
        for(int i=lowerBound;i<upperBound;i++){
            t = new TestCaseGenerator(numberOfTestCases, stringSeed, lengthSeed,
                    i, i+1);
            allTestCases[0] = t.GenerateTestCases("binary");
            allTestCases[1] = t.GenerateTestCases("alphabet");
            allTestCases[2] = t.GenerateTestCases("alphanumeric");
            try{
                fileWriter.append(Integer.valueOf(i).toString());
            }catch (IOException e){
                System.err.println("Unable to write Input Size");
            }
            // For every test case, calculate time for three different character sets
            for(int j=0;j<3;j++){
                for(int k=0;k<numberOfTestCases;k++){
                    long startTime = System.currentTimeMillis();
                    method.LongestSubsequence(allTestCases[j][k][0],
                            allTestCases[j][k][1]);
                    long endTime = System.currentTimeMillis();
                    numberOfRecursion[j][k] = method.getNumberOfRecursiveCallLength();
                    double time = (double)endTime-startTime;
                    if((startTime - endTime) == 0){
                        startTime = System.currentTimeMillis();
                        for(int l=0;l<stride;l++){
                            method.LongestSubsequence(allTestCases[j][k][0],
                                    allTestCases[j][k][1]);
                        }
                        endTime = System.currentTimeMillis();
                        time = ((double)(endTime-startTime))/stride;
                    }
                    methodTime[j][k] = time;
                    averageRecursion[j] += numberOfRecursion[j][k];
                    averageMethodTime[j] += methodTime[j][k];
                }
                // Average over the test cases and write the result
                averageMethodTime[j] = averageMethodTime[j]/numberOfTestCases;
                averageRecursion[j] = averageRecursion[j]/ numberOfTestCases;
                try{
                    fileWriter.append("," +
                            Integer.valueOf(averageRecursion[j]).toString() +
                            "," +(averageMethodTime[j]));
                }catch (IOException e){
                    System.out.println("Unable to write time and recursion");
                    System.exit(1);
                }
            }
            try{
                fileWriter.append("\n");
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println((i+1) + " Case Done");
        }
        try{
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            System.out.println("Unable to Close the file");
        }

    }

    /**
     * Experiment to correlate number recursive call with time and
     * length of the input string with time
     * @param allMethods LCS Interface array
     * @param stride Repetition factor if time is 0
     * @param numberOfTestCases number of test cases
     */
    public void Experiment3(LCSInterface[] allMethods, int stride,
                            int numberOfTestCases){
        for(int i=0;i<4;i++){
            if(i==0){
                AlgorithmHelper(allMethods[0],stride,1,9,19,
                        Integer.valueOf(i).toString()+".csv");
            }else if(i==1){
                System.out.println(i);
                AlgorithmHelper(allMethods[i],stride,1,1,53,
                        Integer.valueOf(i).toString()+".csv");
            }else if(i==2){
                System.out.println(i);
                AlgorithmHelper(allMethods[i],stride,1,1,90,
                        Integer.valueOf(i).toString()+".csv");
            }else if(i==3){
                System.out.println(i);
                AlgorithmHelper(allMethods[i],stride,1,1,900,
                        Integer.valueOf(i).toString()+".csv");
            }

        }
    }



    /**
     * Function that calculates the time taken to calculate sub-sequence
     * @param stride Number of test cases considered in
     *               one time measuring cycle
     */
    public void TimeAnalysis(int stride){
        LCSInterface[] allMethods = new LCSInterface[4];
        allMethods[0] = new NaiveImplementation();
        allMethods[1] = new MemoizationImplementation();
        allMethods[2] = new DynamicImp();
        allMethods[3] = new Hirschberg();
        Expermient1(allMethods[3],10);
        Experiment2(allMethods, 1);
        Experiment3(allMethods,stride,10);
    }

    /**
     * Main Method
     * @param args Command Line Arguments(ignored)
     */
    public static void main(String[] args){
        Profiler profiler = new Profiler();
        profiler.TimeAnalysis(100);
    }
}
