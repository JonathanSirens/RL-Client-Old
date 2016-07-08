package org.runelive.task;

public abstract class Task implements Runnable {

	public abstract void execute();

	@Override
	public void run() {
		this.execute();
	}

}
