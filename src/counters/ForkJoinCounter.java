package counters;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ForkJoinCounter extends Counter {
	
	private class CountFilesAction extends RecursiveAction {

		private static final long serialVersionUID = -196522408291343951L;
		private final File file;

		public CountFilesAction(final File file) {
			this.file = Objects.requireNonNull(file);
		}

		@Override
		protected void compute() {

			if (file.isFile()) {
				addToCountResults(file.getName());
			} else {
				final File[] children = file.listFiles();
				if (children != null) {
					for (final File child : children) {
						ForkJoinTask.invokeAll(new CountFilesAction(child));
					}
				}
			}
		}
	}

	@Override
	protected void countFiles(File file) {
		final ForkJoinPool pool = new ForkJoinPool();
		try {
			pool.invoke(new CountFilesAction(file));
		} finally {
			pool.shutdown();
		}
	}
	
	

}
