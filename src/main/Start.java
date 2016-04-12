package main;

import java.util.ArrayList;
import java.util.List;

import counters.Counter;
import counters.ForkJoinCounter;
import counters.MultiThreadCounter;
import counters.SimpleCounter;

public class Start {
	private static List <Counter> counters  = new ArrayList<Counter>(){{
	//	add(new SimpleCounter());
		//add(new ForkJoinCounter());
		add(new MultiThreadCounter());
	}};
	
	//private static ExecutorService executor = Executors.newFixedThreadPool(10);

	public static void main(String[] args) {
		startCounters("c:\\Test\\");
	//	startCounters("C:\\");
		//startCounters("d:\\Private\\IT\\");
//		startCounters("d:\\Курси\\");
		//startCounters("d:\\Курси\\Projects\\");
	}
	
	private static void startCounters(String directoryPath){
		for(Counter counter: counters){
			System.out.println("1");
			long startTime = System.currentTimeMillis();
			counter.countFilesNumber(directoryPath);
			long timeDuration = System.currentTimeMillis() - startTime ;
			counter.showStatistic(timeDuration);
		}
	}

}
