package library;

public class Calendar {
	
	private int date;
    
	/**
	 * Constructor
	 */
	public Calendar() {
		this.date = 0;
	}
	
	/**
	 * Get today's date and return it
	 * @return
	 */
	public int getDate(){
		return this.date;
	}
	
	/**
	 * Advance the date
	 */
	public void advance(){
		 this.date += 1;
	}

}