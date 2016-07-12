package by.trepam.karotki.ht16_2.main;

public class Fork {
	private boolean taken = false;
	private int owner = -1;
	
	public synchronized boolean take(int id){
		if(!taken){
			owner = id;
			taken = true;
			return true;
		}
		return false;
	}
	
	public synchronized void drop(){
		taken = false;
		owner = -1;
	}
	
	public boolean isTaken(){
		return taken;
	}
	
	public int getOwnerId(){
		return owner;
	}

}
