package by.trepam.karotki.ht16_2.fork;

public class Fork {
	private boolean taken = false; // флаг взята ли вилка философом
	private int owner = -1; // id философа, взявшего вилку, значение -1 значит,
							// что вилка лежит на столе

	// Метод взятия вилки философом, если она доступна
	public synchronized boolean take(int id) {
		if (!taken) {
			owner = id;
			taken = true;
			System.out.println("philosoph" + owner + " took fork");
			return true;
		}
		return false;
	}

	// Метод освобождения вилки
	public synchronized void drop() {
		if (taken) {
			System.out.println("philosoph" + owner + " droped fork");
			taken = false;
			owner = -1;
		}

	}

	public boolean isTaken() {
		return taken;
	}

	public int getOwnerId() {
		return owner;
	}

}
