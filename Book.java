package library;

public class Book {
	
	private String title;
	private String author;
	private int dueDate;
	private boolean checkIn;
    
	/**
	 * Constructor
	 * @param title
	 * @param author
	 */
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.checkIn = false;
	}
	
	/**
	 * Get the book's title and return it
	 * @return
	 */
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * Get the book's author and return it
	 * @return
	 */
	public String getAuthor(){
		return this.author;
	}
	
	/**
	 * If the books are checked in already the due date will be -1 and be returned it
	 * If the books are not returned due date of the book will be returned
	 * @return
	 */
	public int getDueDate(){
		if(this.checkIn == true){
			return -1;
		}
		return this.dueDate;
		
	}
	
	/**
	 * Set the due date for the book checked out
	 * @param date
	 */
	public void checkOut(int date){
		this.dueDate = date;
	}
	
	/**
	 * The due date will be set to -1 once the book is checked in
	 */
	public void checkIn(){
		this.dueDate = -1;
	}
	
	/**
	 * print out the book's title and author
	 */
	@Override
	public String toString(){
		return this.title + " , by "  + this.author;
	}

}
