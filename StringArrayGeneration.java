/**
 * Object of this class will contain a 125-length string array(pool) that its elements are randomly generated from upper case letter [A - Z].
 * And another string array with 100 length that its elements are randomly chose from the pool array. Each element is contained at most 7 characters.
 * This class also provide some functionalities with these two arrays.
 *
 */


package lab2;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StringArrayGeneration {

		private final int POOL_LENGTH = 125;
		private final int PICKED_ARRAY_LENGTH = 100;
		private static char[] UpperCaseLetters = {'A', 'B','C','D', 'E','F','G', 'H','I','J', 'K',
			'L','M', 'N','O','P', 'Q','R','S', 'T','U','V', 'W','X','Y', 'Z',};

		//declare an array with 100 strings in length
		private String[] mPickedArray = new String[PICKED_ARRAY_LENGTH];
		//declare an array with 125 strings in length
		private String[] mPool = new String[POOL_LENGTH];

		private Lock[] locks = new ReentrantLock[POOL_LENGTH];




		public StringArrayGeneration(){
			generateStringPool();
			generateStringArray();
			generateLocks();
		}


		//fill up the pool string array with combinations of randomly picked upper case letters
		public void generateStringPool() {
			for(int i = 0; i < mPool.length; i++) {
				mPool[i] = generateOneString();
			}
		}

		public void generateLocks() {
			for(int i = 0; i < mPool.length; i++) {
				locks[i] = new ReentrantLock(true);
			}
		}

		public void lockIndex(int i) {
			locks[i].lock();
		}

		public void unlockIndex(int i) {
			locks[i].unlock();
		}

		//fill up pickedArray with 100 Strings that are randomly picked from the pool
		public void generateStringArray() {
			for(int i = 0; i < mPickedArray.length; i++) {
				int index = generateRandomPosInt(125);
				mPickedArray[i] = mPool[index];
			}
		}

		//generate a string with length from 1 - 10 from [A - Z]
		public String generateOneString(){
			//length will be in the range of 1 to 7
			int length = generateRandomPosInt(7) + 1;
			String word = "";
			for(int i = 0; i < length; i++) {
				//generate index from 0 - 25
				int index = generateRandomPosInt(26);
				//Append a char to string
				word = word.concat(Character.toString(UpperCaseLetters[index]));
			}
			return word;
		}


		//generate a random positive integer within a range input
		public int generateRandomPosInt (int range) {
			Random rand = new Random();
			int randPosInt;
			if(range > 0)
				randPosInt = rand.nextInt(range);
			else
				return -1;
			return randPosInt;
		}

		//randomly choose a string from the pool
		public String getOneStringInPool () {
			int index = generateRandomPosInt(mPool.length);
			String s = mPool[index];
			return s;
		}


		//count the number of occurrences of a string in an array that is randomly pick from the pool
		public long searchAndCount () {

			String searchingItem = getOneStringInPool();
			int count = 0;
			long startTime = System.nanoTime();
			for(int i = 0; i < mPickedArray.length; i++) {
				lockIndex(i);
				if(searchingItem == mPickedArray[i])
					count++;
				unlockIndex(i);
			}
			return (System.nanoTime() - startTime);
		}

		//search a string in the array and replace it with another string, both strings come from pool array
		public long searchAndReplace () {
			String searchString = getOneStringInPool();
			String replaceString = getOneStringInPool();
			boolean found = false;
			//int index = -1;
			long startTime = System.nanoTime();
			for(int i = 0; i < mPickedArray.length; i++) {
				if(searchString == mPickedArray[i]) {
					mPickedArray[i] = replaceString;
					found = true;
					//we can stop after first occurence
					break;
					//index = i;
				}
			}
			//System.out.println("index: "  + index);
			return (System.nanoTime() - startTime);
		}


		public String[] getPickedArray() {
			return mPickedArray;
		}

		public String[] getPool() {
			return mPool;

		}

		public int getPoolLength() {
			return POOL_LENGTH;
		}
		//display an array
		public static <E> void displayArray(E[] array){
			for(int i = 0; i < array.length; i++) {
				System.out.print( i + ": " + array[i] + "\n " );
			}
		}

}