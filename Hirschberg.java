import java.util.Arrays;
import java.util.HashSet;
import java.lang.Math;
import java.util.Set;

/**
 * File Name: Hirschberg.java
 * Authors: Ameya Shringi(as6520@g.rit.edu)
 *          Vishal Garg(vg3660@g.rit.edu)
 * Created on: 04/15/2016
 * Description: Finding LCS using Hirschberg Algorithm
 */

public class Hirschberg implements LCSInterface{
    int numberOfRecursiveCall = 0;

    public int getNumberOfRecursiveCallLength(){
        return numberOfRecursiveCall;
    }
	/**
	 * algHirschberg2 is the implementation of second algorithm of Hirschberg
	 * and it only return  the length of the LCS
	 *@param	length1			Length of the first string
	 *@param	length2			Length of the second string
	 *@param	firstString		First string to be compared
	 *@param	secondString 	Second string to be compared
	 */
	int algHirschberg2(int length1, int length2, String firstString, String secondString){
		//Initializing
		int[] curRow = new int[length2+1];
		for(int i = 0; i<length2+1; i++){
			curRow[i] = 0;
		}
		int[] oldRow = new int[length2+1];
		
		//Base case
		if(length1 == 0 || length2 == 0){
			return 0;
		}
		
		//Processing
		for(int i = 1;i<length1+1;i++){
			//Copying K1 into K0
			oldRow = Arrays.copyOf(curRow, curRow.length);
			for(int j= 1; j<length2+1; j++){
				if(firstString.charAt(i-1) == secondString.charAt(j-1)){
					curRow[j] = oldRow[j-1] + 1;
				}else{
					curRow[j] = Math.max(curRow[j-1], oldRow[j]);
				}
			}
		}
		
		return curRow[length2];
	}
	
	/**
     * algHirsch is the implementation of third Hirschberg algorithm
	 *which returns LCS and not just the length
	 *@param	length1			Length of the first string
	 *@param	length2			Length of the second string
	 *@param	firstString		First string to be compared
	 *@param	secondString 	Second string to be compared
	 *@return	Longest Common Subsequence for the two input strings
	 */
	String algHirsch(int length1, int length2, String firstString, String secondString){
		String output = "";
		if(length2 == 0 || length1==0){
			return output;
		}else if(length2 == 1){
			int index = firstString.indexOf(secondString);
			if(index != -1){
				return secondString;
			}else{
				return output;
			}
		}else if(length1 == 1){
			int index = secondString.indexOf(firstString);
			if(index != -1){
				return firstString;
			}else{
				return output;
			}
		}else{
			int midM = length1/2;
			int bestJ = -1;
			int maxCSVlen = 0;
			String A1 = firstString.substring(0,midM);
			String A2 = new StringBuilder(firstString.substring(midM)).reverse().toString();
			for(int j = 0; j<length2; j++){
				String B1 = secondString.substring(0,j);
				String B2 = new StringBuilder(secondString.substring(j)).reverse().toString();
				int L1 = algHirschberg2(midM, j, A1, B1);
				int L2 = algHirschberg2(length1 - midM, length2 - j, A2, B2);
                numberOfRecursiveCall += 2;
				if(L1 +L2 > maxCSVlen){
					bestJ = j;
					maxCSVlen = L1 + L2;
				}
			}
			if(maxCSVlen == 0){
				return "";
			}
			String output1 = algHirsch(midM, bestJ, firstString.substring(0,midM), secondString.substring(0,bestJ));
			String output2 = algHirsch(length1 - midM, length2 - bestJ, firstString.substring(midM), secondString.substring(bestJ));
			if(output1 == null){
				output = output2;
			}else if(output2 == null){
				output = output1;
			}else{
				output = output1 + output2;
			}
		}
		
		
		return output;
	}
	
	/**
     * LongestSubsequence functions is a helper function which in turn calls
	 * algHirsch function to find the LCS
	 * @param	firstString 	This is the first string out of the two to be compared
	 * @param	secondString	This is the second string to be compared
	 * @return	Longest Common Subsequence of the two strings	
	 */
	public Set<String> LongestSubsequence(String firstString, String secondString){
		numberOfRecursiveCall = 0;
		String result = algHirsch(firstString.length(), secondString.length(), firstString, secondString);
        HashSet<String> r = new HashSet<>();
        r.add(result);
        return  r;
	}
	
	/**
     * LengthLongestSubsequence functions is a helper function which in turn calls
	 * algHirschberg2 function to find the length of LCS
	 * @param	firstString 	This is the first string out of the two to be compared
	 * @param	secondString	This is the second string to be compared
	 * @return	Length of Longest Common Subsequence of the two strings	
	 */
	public int LengthLongestSubsequence(String firstString, String secondString){
		return algHirschberg2(firstString.length(), secondString.length(), firstString, secondString);
	}

	
	public static void main(String args[]){
		//Sample code for testing purpose
		Hirschberg h = new Hirschberg();
        int result = h.LengthLongestSubsequence("abcbdab","bdcaba");
        System.out.println(result);

	}
}
