package org.ikov.client;

public class MemoryMonitor implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				System.gc();
				System.runFinalization();
				Thread.sleep(15000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
