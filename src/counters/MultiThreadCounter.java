package counters;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import main.Result;

public class MultiThreadCounter extends Counter implements Callable {
	private static AtomicLong countWork = new AtomicLong(0);
	private static ExecutorService executor = Executors.newFixedThreadPool(1100);
	// private static ConcurrentHashMap<String, Integer> countResults = new
	// ConcurrentHashMap<>();
	private File rootFile;
	private Result r;

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
				countWork.addAndGet(1);
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
