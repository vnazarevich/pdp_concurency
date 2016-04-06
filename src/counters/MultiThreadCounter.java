package counters;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pdp.nazarevych.concurency.Result;

public class MultiThreadCounter extends Counter implements Callable {
	private static ExecutorService executor = Executors.newFixedThreadPool(1100);
	// private static ConcurrentHashMap<String, Integer> countResults = new
	// ConcurrentHashMap<>();
	private File rootFile;

	public MultiThreadCounter(File rootFile) {
		this.rootFile = rootFile;
	}

	public MultiThreadCounter() {
		super();
	}

	@Override
	protected void countFiles(File file) {
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				executor.submit(new MultiThreadCounter(f));
			} else {
				addToCountResults(f.getName());

			}
		}
	}

	@Override
	public Object call() throws Exception {
		countFiles(rootFile);
		return null;
	}

	// @Override
	// public void addToCountResults(String fileName){
	// System.out.println("add file "+ fileName);
	// if (countResults.containsKey(fileName)){
	// countResults.put(fileName, countResults.get(fileName) + 1);
	// }
	// else{
	// countResults.put(fileName, 1);
	// }
	// }

	@Override
	public void countFilesNumber(String path) {
		directoryPath = path;
		File file = new File(path);
		checkFile(file);
		countFiles(file);
		checkExecutor();

		createdSortedCountResults();
	}

	private void checkExecutor() {
		try {
			while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
				System.out.println("stil work");
				try {

					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// while (!executor.isTerminated()){
		// try {
		// Thread.currentThread().sleep(100);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }}

	}

}
