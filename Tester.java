package lab2;


public class Tester {
		
	public static void main(String args[]) {
		
	
		StringArrayGeneration arrays = new StringArrayGeneration();
		final int REPETITIONS = 10;//500 operations
		final int NUM_THREADS = 10;//20 threads
		
		//StringArrayGeneration.displayArray(arrays.getPool());
		//StringArrayGeneration.displayArray(arrays.getPickedArray());
		
		for(int i = 0; i < NUM_THREADS; i++) {
			StringArrayGenerationRunnable runArrays = new StringArrayGenerationRunnable(arrays, i, REPETITIONS);
			Thread t = new Thread(runArrays);
			t.start();
		}
	
		//test 1
	}
	

}
