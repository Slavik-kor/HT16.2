package by.trepam.karotki.ht16_2.main;

import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.trepam.karotki.ht16_2.philosoph.Philosoph;

public class PhilosophDemo {
	public static final Logger LOG = LogManager.getLogger();
	private static int sValue = 5;
	private static int philCount = 5;

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(sValue);
		Thread[] ph = new Thread[philCount];
		for (int i = 0; i < philCount; i++) {
			ph[i] = new Thread(new Philosoph(semaphore));
		}

		for (Thread i : ph) {
			i.start();
		}

	}

}
