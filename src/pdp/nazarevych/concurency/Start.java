package pdp.nazarevych.concurency;

import java.util.HashSet;
import java.util.Set;

import counters.Counter;
import counters.SimpleCounter;

public class Start {
	private static Set <Counter> counters = new HashSet<Counter>(){{
		add(new SimpleCounter());
	}};

	public static void main(String[] args) {
		//startCounters("c:\\Test\\");
		startCounters("C:\\");
		//startCounters("G:\\");
	}
	
	private static void startCounters(String directoryPath){
		int filesNumber;
		for(Counter counter: counters){
			counter.countFilesNumber(directoryPath);
			counter.showStatistic();
		}
		
	}

}
