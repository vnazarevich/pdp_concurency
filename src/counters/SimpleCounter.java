package counters;

import java.io.File;
import java.util.logging.Logger;

public class SimpleCounter extends Counter {
	Logger log = Logger.getLogger(SimpleCounter.class.getName());

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
}
