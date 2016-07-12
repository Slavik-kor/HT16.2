package by.trepam.karotki.ht16_2.main;

import java.util.concurrent.Semaphore;

public class PhilosophDemo {
	
	public static void main(String[] args){
		Semaphore semaphore = new Semaphore(4);
		Thread ph1 = new Thread(new Philosoph(semaphore));
		Thread ph2 = new Thread(new Philosoph(semaphore));
		Thread ph3 = new Thread(new Philosoph(semaphore));
		Thread ph4 = new Thread(new Philosoph(semaphore));
		Thread ph5 = new Thread(new Philosoph(semaphore));
		
		ph1.start();
		ph2.start();
		ph3.start();
		ph4.start();
		ph5.start();
	}

}
