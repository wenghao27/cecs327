package lab2;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StringArrayGenerationRunnable  implements Runnable{

	//declare an object of StringArrayGeneration Class
	StringArrayGeneration mStrArrays;
	//number of operations a thread is going to perform
	private int mRepetitions;
	//number of thread
	private int mThreadNum;
	//total waiting time of a thread
	private long mTotalWaitTime = 0;
	//this array will store waiting time of each operation in nanoseconds
	private long[] mWaitTimeInNano;

	private Lock mArrayLocks[];
	private Lock currentLock;

	public StringArrayGenerationRunnable(StringArrayGeneration s, int threadNum, int count) {

		mStrArrays = s;
		mRepetitions = count;
		mThreadNum = threadNum;
		mWaitTimeInNano = new long[count];
		mArrayLocks = new ReentrantLock[s.getPoolLength()];

	}

	@Override
	public void run(){

		System.out.println("Start thread number: " + mThreadNum);

		for(int i = 0; i < mRepetitions; i++){

			int operation = getOperationNumber();


			//System.out.println("Thread #" + mThreadNum + " with operation "  + i  + " waiting " + duration + "nanoseconds");


			if(operation < 2000){
				long time = mStrArrays.searchAndCount();
				mWaitTimeInNano[i] = time;
				mTotalWaitTime += time;
				//System.out.println("count: " + count + " ");
			}
			else {
				long time = mStrArrays.searchAndReplace();
				mWaitTimeInNano[i] = time;
				mTotalWaitTime += time;
			}
		}
			//if a thread finish all the operations, calculate the average and standard deviation
		System.out.println("total time: " + mTotalWaitTime);
		long average = mTotalWaitTime / mRepetitions;
		double standardDeviation = compute_standard_deviation(mWaitTimeInNano, average, mRepetitions);
		//for(int j = 0; j < mRepetitions; j++){
			//System.out.println("Thread " + mThreadNum + " " + mWaitTimeInNano[j] + "nanoseconds");
		//}
		System.out.println("Thread " + mThreadNum + " Average waiting time is: " + average);
		System.out.println("Thread " + mThreadNum +  " Standard deviation is : " + standardDeviation);



	}

	//get operation number randomly
	public int getOperationNumber() {
		Random rand = new Random();
		int operationNum = rand.nextInt(2501);//generate int from 0 to 2500 inclusive
		return operationNum;
	}

	public double compute_standard_deviation(long[] array, long mean, int length) {
		   long square_sum = 0;
		   for (int i = 0; i < length; i++) {
		      square_sum += (array[i] - mean) * (array[i] - mean);
		   }
		   double standard_deviation = Math.sqrt(square_sum / length);
		   return standard_deviation;
		}
}
