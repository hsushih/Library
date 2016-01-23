package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	
	private Book contact;
	private Book equalRites;
	private Book empty; 

	@Before
	public void setUp() throws Exception {
		 contact = new Book("Contact", "Carl Sagan");
	     equalRites = new Book("Equal Rites", "Terry Pratchett");
	     empty = new Book("","");
	}

	@Test
	public void testGetTitle() {
		assertEquals(contact.getTitle().toString(),"Contact");
		assertEquals(empty.getTitle().toString(),"");
	}
	
	@Test
	public void testGetAuthor(){
		assertEquals(contact.getAuthor().toString(),"Carl Sagan");
		assertEquals(empty.getTitle().toString(),"");
	}
	
	@Test
	public void testGetDueDate(){
		contact.checkOut(7);
		assertEquals(contact.getDueDate(),7);
		contact.checkOut(8);
		assertEquals(contact.getDueDate(),8);
	}
	
	@Test
	public void testCheckOut(){
		contact.checkOut(7);
		assertEquals(contact.getDueDate(),7);
		contact.checkOut(8);
		assertEquals(contact.getDueDate(),8);	
	}
	
	@Test
	public void testCheckIn(){
		contact.checkIn();
		assertEquals(contact.getDueDate(),-1);
		equalRites.checkIn();
		assertEquals(equalRites.getDueDate(),-1);
	}
	
	@Test
	public void testToString(){
		assertEquals(contact.toString(),"Contact , by Carl Sagan");
		
	}

}
