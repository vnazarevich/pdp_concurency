package counters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import pdp.nazarevych.concurency.Result;

public abstract class Counter {
	Logger log = Logger.getLogger(Counter.class.getName());
	private String directoryPath;
	private Map <String, Integer> countResults = new HashMap<String, Integer>();
	private List <Result> sortedCountResults = new ArrayList<Result>();
	
//	private Map <String, Integer> sortedCountResults;
	private static int  MAX_NUMBER_OF_POPULAR_FILES = 10;
	
	public void countFilesNumber(String path){
			directoryPath = path;
			File file = new File(path);
			checkFile(file);
			countFiles(file);
			createdSortedCountResults(); 
	}
	
	private void checkFile(File file) {
		
		if (!file.exists()) {
			log.warning("File, path = " + file.getAbsolutePath() + " - doesn`t exist");
		}
	}

	private void createdSortedCountResults() {
		 for(String fileName: countResults.keySet()){
			 sortedCountResults.add(new Result(fileName, countResults.get(fileName)));
		 }
		 Collections.sort(sortedCountResults);
		 System.out.println(sortedCountResults.toString());
	}

	abstract protected void countFiles(File file);

 	public Map<String, Integer> getCountResults() {
		return countResults;
	}

	public void setCountResults(Map<String, Integer> countResults) {
		this.countResults = countResults;
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
	
	public void showStatistic(){
		if ( !countResults.isEmpty() ){
			System.out.println("Directory " + directoryPath + " includes " + countResults.size() + " files"  );
			System.out.println("The most popular files are :");
			int maxNumberOfPopularFiles = (sortedCountResults.size() < MAX_NUMBER_OF_POPULAR_FILES)? sortedCountResults.size():MAX_NUMBER_OF_POPULAR_FILES;
			for (int i = 0; i < maxNumberOfPopularFiles-1; i++){
				if(sortedCountResults.get(i).getNumber() > 1){
					System.out.println(sortedCountResults.get(i).toString());
				} else {
					return;
				}
			}
		}
	}
}
