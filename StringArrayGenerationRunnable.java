package lab2;

import java.util.ArrayList;
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

	//total search waiting time of a thread
	private long mTotalSearchWaitTime = 0;
	//total search and replace waiting time of a thread
	private long mTotalReplaceWaitTime = 0;
	//this arrayList will store operations of searching waiting time in nanoseconds
	private ArrayList<Long> mSearchWaitTime;
	//this arrayList will store operations of replacing and searching waiting time in nanoseconds
	private ArrayList<Long> mReplaceWaitTime;
	
	private Lock mArrayLocks[];
	
	
	public StringArrayGenerationRunnable(StringArrayGeneration s, int threadNum, int count) {
		mStrArrays = s;
		mRepetitions = count;
		mThreadNum = threadNum;	
		mSearchWaitTime = new ArrayList<>();
		mReplaceWaitTime = new ArrayList<>();
		mArrayLocks = new ReentrantLock[s.getPoolLength()];


	@Override

	public void run() {
		
		System.out.println("Start thread number: " + mThreadNum);

		for(int i = 0; i < mRepetitions; i++){
			
			int operation = getOperationNumber();

			if(operation < 2000){		
				long time = mStrArrays.searchAndCount();
				mTotalSearchWaitTime += time;
				mSearchWaitTime.add(time);
			}
			else {	
				long time = mStrArrays.searchAndCount();
				mTotalReplaceWaitTime += time;
				mReplaceWaitTime.add(time);
				
			}
			//if a thread finish all the operations, display the each waiting time of the operation
			// calculate the average and standard deviation
			if(i == mRepetitions - 1) {
				

				double avergeSearchingTime = compute_Average(mSearchWaitTime);
				double SD_search_Time = compute_standard_deviation(mSearchWaitTime, avergeSearchingTime);
				double avergeReplaceTime = compute_Average(mReplaceWaitTime);
				double SD_Repalce_Time = compute_standard_deviation(mReplaceWaitTime, avergeReplaceTime);
				
				//in order to output the result in order
				try {
					Thread.sleep(mThreadNum * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
								
					System.out.println("Thread #" + mThreadNum + ": Search waiting time list: \n");
					
					displayArrayList(mSearchWaitTime);
					
					System.out.println("Average searching waiting Time: " + Math.round(avergeReplaceTime) + 
							" nanoseconds \n" + "Average Standard Deviation: " + Math.round(SD_search_Time) + " nanoseconds \n");
					
					System.out.println("Thread #" + mThreadNum + ": Search and  Replace waiting time list: \n");
					
					displayArrayList(mReplaceWaitTime);
					 
					System.out.println("Average searching waiting Time: : " + Math.round(avergeReplaceTime) + 
							" nanoseconds \n" + "Average Standard Deviation: " + Math.round(SD_Repalce_Time) + " nanoseconds \n");
					
				}
								
		}							

	
	//get operation number randomly
	public int getOperationNumber() {
		Random rand = new Random();
		int operationNum = rand.nextInt(2501);//generate int from 0 to 2500 inclusive
		return operationNum;
	}
	
	//compute average of given arraylist
	public double compute_Average(ArrayList<Long> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
		      sum += list.get(i);
		}
		return sum/list.size();
		
	}

	//compute standard deviation
	public double compute_standard_deviation(ArrayList<Long> list, double average) {
			if(list.size() == 0)
				return 0;
		   long square_sum = 0;
		   for (int i = 0; i < list.size(); i++) {
		      square_sum += (list.get(i) - average) * (list.get(i) - average);
		   }
		   double standard_deviation = Math.sqrt(square_sum / list.size());
		   return standard_deviation;
	}
	//display the content of given array list
	public static void displayArrayList(ArrayList<Long> list){
		 for (int i = 0; i < list.size(); i++) {
		      System.out.println("idx " + i + " :" + list.get(i) + " nanoseconds");
		   }
	}
}






