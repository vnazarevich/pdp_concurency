package main;

import java.util.HashSet;
import java.util.Set;
import counters.Counter;
import counters.MultiThreadCounter;
import counters.SimpleCounter;

public class Start {
	private static Set <Counter> counters  = new HashSet<Counter>(){{
		add(new MultiThreadCounter());
		add(new SimpleCounter());
	}};
	
	//private static ExecutorService executor = Executors.newFixedThreadPool(10);

	public static void main(String[] args) {
		//startCounters("c:\\Test\\");
	//	startCounters("C:\\");
		//startCounters("d:\\Private\\IT\\");
		startCounters("d:\\Курси\\");
	}
	
	private static void startCounters(String directoryPath){
		for(Counter counter: counters){
			long startTime = System.currentTimeMillis();
			counter.countFilesNumber(directoryPath);
			long timeDuration = System.currentTimeMillis() - startTime ;
			counter.showStatistic(timeDuration);
		}
	}

}
