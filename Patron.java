package library;

import java.util.ArrayList;

public class Patron {
	
	private String name;
	private Library library;
	private ArrayList<Book> bookListCheckOut = new ArrayList<Book>();
	
    /**
     * Constructor
     * @param name
     * @param library
     */
	public Patron(String name, Library library) {
		this.name = name;
		this.library = library;
		this.bookListCheckOut = new ArrayList<Book>();
	}
	
	/**
	 * Get the patron's name and return it
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Add checked out books to the check out book list
	 * @param book
	 */
	public void take(Book book){
		this.bookListCheckOut.add(book);
	}
    
	/**
	 * Remove books from the check out book list
	 * @param book
	 */
	public void giveBack(Book book){
		this.bookListCheckOut.remove(book);
	}
	
	/**
	 * Get check out book list and return it
	 * @return
	 */
	public ArrayList<Book> getBooks(){
		return this.bookListCheckOut;
	}
	
	@Override
	public String toString(){
		return this.name;
	}


}
