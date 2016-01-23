package library;

import java.util.*;

public class OverdueNotice {
	
	Patron nameOfPatron;
	int DateOfToday;
	
    
	/**
	 * Constructor
	 * @param patron
	 * @param todaysDate
	 */
	public OverdueNotice(Patron patron, int todaysDate ) {
		this.nameOfPatron = patron;
		this.DateOfToday = todaysDate;
	}

	/**
	 * If the books checked out are not overdue a list of books checked out will be printed
	 * If the books checked out are overdue an overdue message will be printed
	 */
	@Override
	public String toString(){
		String s = "";
		for(Book books: this.nameOfPatron.getBooks()){
			if( books.getDueDate() < this.DateOfToday){
				s = s+ books.toString()+ "------ The Due Date is :" + books.getDueDate() + " -------This book is OVERDUE already" + "\n";
			}
			else{
				s += "";
			}
		}
        return this.nameOfPatron +" has checked out the following books"+"\n"+this.nameOfPatron.getBooks() +"\n"+"\n"+s;
	}

}
