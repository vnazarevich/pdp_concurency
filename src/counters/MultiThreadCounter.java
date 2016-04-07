package counters;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadCounter extends Counter implements Runnable {
	private static AtomicLong countWork = new AtomicLong(0);
	private static ExecutorService executor = Executors.newFixedThreadPool(5);
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
	public void run() {
		countWork.incrementAndGet();
		countFiles(rootFile);
		countWork.decrementAndGet();
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
		if (!(countWork.get() == 0)) {
			try {
				Thread.currentThread().sleep(50);
				checkExecutor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			executor.shutdown();
		}
	}

}
