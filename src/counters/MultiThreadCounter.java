package counters;

import java.io.File;
import java.util.concurrent.Callable;

public class MultiThreadCounter extends Counter implements Callable{
	private File rootFile;
	
	public MultiThreadCounter(File rootFile) {
		this.rootFile = rootFile;
	}

	@Override
	protected void countFiles(File file) {
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()){
				countFiles(f);
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

}
