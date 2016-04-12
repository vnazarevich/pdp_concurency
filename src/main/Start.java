package main;

import java.util.ArrayList;
import java.util.List;

import counters.Counter;
import counters.ForkJoinCounter;
import counters.MultiThreadCounter;
import counters.SimpleCounter;

public class Start {
	private static List <Counter> counters  = new ArrayList<Counter>(){{
		add(new SimpleCounter());
		add(new MultiThreadCounter());
		add(new ForkJoinCounter());
	}};

	public static void main(String[] args) {
		startCounters("d:\\Курси\\Projects\\");
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
