package pdp.nazarevych.concurency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import counters.Counter;
import counters.MultiThreadCounter;
import counters.SimpleCounter;

public class Start {
	private static Set <Counter> counters  = new HashSet<Counter>(){{
		add(new SimpleCounter());
		add(new MultiThreadCounter());
	}};
	
	//private static ExecutorService executor = Executors.newFixedThreadPool(10);

	public static void main(String[] args) {
		//startCounters("c:\\Test\\");
		//startCounters("C:\\");
		startCounters("d:\\Private\\IT\\");
	}
	
	private static void startCounters(String directoryPath){
		int filesNumber;
		for(Counter counter: counters){
			counter.countFilesNumber(directoryPath);
			counter.showStatistic();
		}
	}

}
