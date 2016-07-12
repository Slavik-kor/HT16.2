package by.trepam.karotki.ht16_2.main;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Philosoph implements Runnable {
	public static ArrayList<Fork> forks = new ArrayList<Fork>();
	private Semaphore semaphore;
	private Fork leftFork, rightFork;
	public static int num = 0;
	private int id;
	private boolean placeForks = false;;

	public Philosoph(Semaphore semaphore) {
		this.semaphore = semaphore;
		forks.add(new Fork());
		id = num;
		num++;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				try {
					initForks();
					semaphore.acquire();
				} catch (InterruptedException e) {
					// log
				}
				leftFork.take(id);
				rightFork.take(id);
				if (eat()){
					
					rightFork.drop();
					rightFork.drop();
				}
				think();
			} finally {

				semaphore.release();
			}
		}
		System.out.println("Philisoph number"+id+" is finished");
	}

	public boolean eat() {
		if (rightFork.getOwnerId() == id && leftFork.getOwnerId() == id) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// log
			}
			System.out.println("philosoph number"+id+" is eating");
			return true;
		}
		return false;
	}

	public void think() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// log
		}
	}

	private void initForks() {
		if (placeForks)
			return;
		placeForks = true;
		leftFork = forks.get(id);
		if (forks.size() <= id) {
			rightFork = forks.get(id + 1);
		} else {
			rightFork = forks.get(0);
		}
	}
}
