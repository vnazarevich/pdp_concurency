package counters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import main.Result;

public abstract class Counter {
	Logger log = Logger.getLogger(Counter.class.getName());
	protected String directoryPath;
	private  static Map <String, Integer> countResults = new ConcurrentHashMap<String, Integer>();
	private  List <Result> sortedCountResults= new ArrayList<Result>();
	private static int  MAX_NUMBER_OF_POPULAR_FILES = 10;
	
	abstract protected void countFiles(File file);
	
	public void countFilesNumber(String path){
			directoryPath = path;
			File file = new File(path);
			checkFile(file);
			countFiles(file);
			createdSortedCountResults(); 
	}
	
	protected void checkFile(File file) {
		if (!file.exists()) {
			log.warning("File, path = " + file.getAbsolutePath() + " - doesn`t exist");
		}
	}

	protected void createdSortedCountResults() {
			sortedCountResults = new ArrayList<Result>();
		 for(String fileName: countResults.keySet()){
			 sortedCountResults.add(new Result(fileName, countResults.get(fileName)));
		 }
		 Collections.sort(sortedCountResults);
	}
	
	protected void createdSortedCountResults(Map <String, Integer> countResults) {
		sortedCountResults = new ArrayList<Result>();
	 for(String fileName: countResults.keySet()){
		 sortedCountResults.add(new Result(fileName, countResults.get(fileName)));
	 }
	 Collections.sort(sortedCountResults);
}

	
	public void addToCountResults(String fileName){
		if (countResults.containsKey(fileName)){
			countResults.put(fileName, countResults.get(fileName) + 1);
		}
		else{
			countResults.put(fileName, 1);
		}
	}
	
	/*
	 * show all results
	 * also
	 * show most popular files names (number of founds > 1)
	 */
	
	public void showStatistic(long timeDuration){
		if ( !sortedCountResults.isEmpty() ){
			System.out.println();
			System.out.println("========= Results from " + this.getClass().getName()+"===============");
			System.out.println("Directory " + directoryPath + " includes " + sortedCountResults.size() + " files"  );
			System.out.println("Duration Of Counting = " + timeDuration + " ms ");
			System.out.println("The most popular files are :");
			int maxNumberOfPopularFiles = (sortedCountResults.size() < MAX_NUMBER_OF_POPULAR_FILES)? sortedCountResults.size():MAX_NUMBER_OF_POPULAR_FILES;
			for (int i = 0; i < maxNumberOfPopularFiles-1; i++){
				if(sortedCountResults.get(i).getNumber() > 1){
					System.out.println(sortedCountResults.get(i).toString());
				} else {
					break;
				}
			}
		}
		countResults.clear();
		sortedCountResults.clear(); 
	}
}
