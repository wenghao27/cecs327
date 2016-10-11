package lab2;

import java.util.Random;

public class StringArrayGeneration {
	
		//declare an array with 100 strings in length
		private String[] mPickedArray = new String[100];
		//declare an array with 125 strings in length
		private String[] mPool = new String[125];
		private static char[] UpperCaseLetters = {'A', 'B','C','D', 'E','F','G', 'H','I','J', 'K',
			'L','M', 'N','O','P', 'Q','R','S', 'T','U','V', 'W','X','Y', 'Z',};
	
		public StringArrayGeneration(){
			generateStringPool();
			generateStringArray();
		}
		
		//fill up pickedArray with 100 Strings that are randomly picked from the pool
		public void generateStringArray() {
			for(int i = 0; i < mPickedArray.length; i++) {
				int index = generateRandomPosInt(125);
				mPickedArray[i] = mPool[index];
			}
		}
				
		//fill up the pool string array with combinations of randomly picked upper case letters
		public void generateStringPool() {			
			for(int i = 0; i < mPool.length; i++) {
				mPool[i] = generateOneString();		
			}	
		}

		//get a random string from the pool
		public String getOneStringInPool () {
			int index = generateRandomPosInt(mPool.length);
			String s = mPool[index];
			return s;
		}
		
		//generate a random positive integer within a range input
		public static int generateRandomPosInt (int range) {
			Random rand = new Random();
			int randPosInt;
			if(range > 0)
				randPosInt = rand.nextInt(range);
			else
				return -1;
			return randPosInt;
		}
		
		//generate a string with length from 1 - 10 from [A - Z]
		public String generateOneString(){
			//length will be in the range of 1 to 7
			int length = generateRandomPosInt(7) + 1;
			String s = "";
			for(int i = 0; i < length; i++) {
				//generate index from 0 - 25 
				int index = generateRandomPosInt(26);
				//Append a char to string
				s = s.concat(Character.toString(UpperCaseLetters[index]));		
			}
			return s;		
		}

		//count the number of occurrences of a string in an array that is randomly pick from the pool
		public int searchAndCount () {
			String searchingItem = getOneStringInPool();
			int count = 0;
			for(int i = 0; i < mPickedArray.length; i++) {
				if(searchingItem == mPickedArray[i])
					count++;
			}	
			return count;
		}
		
		//search a string in the array and replace it with another string, both strings come from pool array
		public boolean searchAndReplace () {
			String searchString = getOneStringInPool();
			String replaceString = getOneStringInPool();
			boolean found = false;
			//int index = -1;
			for(int i = 0; i < mPickedArray.length; i++) {
				if(searchString == mPickedArray[i]) {
					mPickedArray[i] = replaceString;
					found = true;
					//index = i;
				}
			}	
			//System.out.println("index: "  + index);
			return found;
		}
		
		
		public String[] getPickedArray() {
			return mPickedArray;
		}
		public String[] getPool() {
			return mPool;
			
		}
		
		//display an array in different types
		public static <E> void displayArray(E[] array){
			for(int i = 0; i < array.length; i++) {
				System.out.print( i + ": " + array[i] + "\n " );
			}		
		}

}
