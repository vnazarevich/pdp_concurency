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
	
	/*
	 * recursive count
	 * 
	 * if directory does not exist - return -1
	 * else return number of files
	 */
//	protected int getFilesNumber(File file) {
//		File[] files = file.listFiles();
//		int number = 0;
//		if (files == null) {
//			return 0;
//		}
//		for (File f : files) {
//			if (f.isDirectory()){
//				number += getFilesNumber(f);
//			} else{
//				addToCountResults(f.getName());
//				number++;
//			}
//		}
//		return number;
//	}

}
