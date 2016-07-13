package by.trepam.karotki.ht16_2.philosoph;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.trepam.karotki.ht16_2.fork.Fork;

public class Philosoph implements Runnable {
	public static final Logger LOG = LogManager.getLogger();
	public static ArrayList<Fork> forks = new ArrayList<Fork>(); //список вилок, лежащих на столе
	public static int num = 0; 									
	private static int cycles = 1000;
	private Semaphore semaphore;
	private Fork leftFork, rightFork;							// ссылки на левую и правую вилки
	private final int id;
	private boolean initializedForks = false;					// флаг состояния: определены правая и левая вилки для философа
	private int amountEat = 0;									// количество принятий пищи философом

	public Philosoph(Semaphore semaphore) {
		this.semaphore = semaphore;
		forks.add(new Fork());
		id = num;
		num++;
	}

	@Override
	public void run() {
		initForks();											// определение какие вилки для философа будут являться правой и левой  
		for (int i = 0; i < cycles; i++) {
			try {
				try {
					semaphore.acquire();						//запрос разрешения у семафора на попытку взять вилки и принять пищу
				} catch (InterruptedException e) {
					LOG.warn("Error getting semaphore by philosoph" + id);
				}
				if (!leftFork.take(id)) {												//попытка взять левую вилку
					LOG.warn("Trying to get left fork was failed by philosoph" + id);
				}

				if (!rightFork.take(id)) {												//попытка взять правую вилку
					LOG.warn("Trying to get right fork was failed by philosoph" + id);
				}

				if (eat()) {          				// если удается принять пищу, то
					rightFork.drop();				// вилки кладутся на стол
					rightFork.drop();
				}

			} finally {
				if (rightFork.getOwnerId() == id) {       // если у философа одна вилка, то значит он не смог принять пищу
					rightFork.drop();                     // и ему необходимо положить вилку на стол
				}
				if (leftFork.getOwnerId() == id) {
					leftFork.drop();
				}
				semaphore.release();
				think();                              // Когда у философа не остается вилок, он размышляет
			}
		}
		System.out.println("Philisoph" + id + " is finished with " + amountEat + " times eating");
	}
   
	
	public boolean eat() {
		if (rightFork.getOwnerId() == id && leftFork.getOwnerId() == id) {
			try {
				Thread.sleep(1);
				System.out.println("philosoph" + id + " is eating");
				amountEat++;
			} catch (InterruptedException e) {
				LOG.warn("Error during eating by philosoph" + id);
			}

			return true;
		}
		return false;
	}

	public void think() {
		try {
			Thread.sleep(1);
			System.out.println("philosoph" + id + " is thinking");
		} catch (InterruptedException e) {
			LOG.warn("Error during thinking by philosoph" + id);
		}
	}

	private void initForks() {
		if (initializedForks) {
			return;
		}
		initializedForks = true;
		leftFork = forks.get(id);
		if (forks.size() - 1 > id) {
			rightFork = forks.get(id + 1);
		} else {
			rightFork = forks.get(0);
		}
	}

	public int getAmountEat() {
		return amountEat;
	}

}
