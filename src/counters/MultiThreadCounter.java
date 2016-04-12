package counters;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadCounter extends Counter implements Runnable {
	private static AtomicLong countWork = new AtomicLong(0);
	private static ExecutorService executor = Executors.newFixedThreadPool(5);
	private File rootFile;
	private  static Map <String, Integer> countResults = new ConcurrentHashMap<String, Integer>();

	public MultiThreadCounter(File rootFile) {
		this.rootFile = rootFile;
	}

	public MultiThreadCounter() {
		super();
	}

	@Override
	protected void countFiles(File file) {
		System.out.println("file "+ file.getName());
		File[] files = file.listFiles();
		System.out.println("files = " + files.toString());
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				executor.submit(new MultiThreadCounter(f));
				
			} else {
				addToCountResults(f.getName());
				System.err.println("add " + f.getName());
			}
		}
		System.out.println("______"+countResults.toString());
	}

	@Override
	public void run() {
		countWork.incrementAndGet();
		System.out.println("countWork = " + countWork);
		countFiles(rootFile);
		countWork.decrementAndGet();
		System.out.println("countWork = " + countWork);
	}

	@Override
	public void countFilesNumber(String path) {
		directoryPath = path;
		File file = new File(path);
		checkFile(file);
		System.out.println("Multi 2");
		new MultiThreadCounter(file).run();

		checkExecutor();
		System.out.println("Multi 3");
		createdSortedCountResults(countResults);
	}

	private void checkExecutor() {
		if (!(countWork.get() == 0)) {
			try {
				Thread.currentThread().sleep(150);
				checkExecutor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			executor.shutdown();			
		}
	}
	public void addToCountResults(String fileName){
		if (countResults.containsKey(fileName)){
			countResults.put(fileName, countResults.get(fileName) + 1);
		}
		else{
			countResults.put(fileName, 1);
		}
	}

}
