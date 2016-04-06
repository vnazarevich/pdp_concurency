package counters;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadCounter extends Counter implements Callable{
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	private static ConcurrentHashMap<String, Integer> countResults = new ConcurrentHashMap<>();
	private File rootFile;
	
	public MultiThreadCounter(File rootFile) {
		this.rootFile = rootFile;
	}
	public MultiThreadCounter() {
	}

	@Override
	protected void countFiles(File file) {
		System.out.println("f = " + file.getName());
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()){
				executor.submit(new MultiThreadCounter(f));
			} else{
				addToCountResults(f.getName());
			}
		}
		
	}

	@Override
	public Object call() throws Exception {
		countFiles(rootFile);
		return null;
	}
	
	@Override
	public void addToCountResults(String fileName){
		System.out.println("size = " + countResults.size());
		if (countResults.containsKey(fileName)){
			countResults.put(fileName, countResults.get(fileName) + 1);
		}
		else{
			countResults.put(fileName, 1);
		}
	}

}
