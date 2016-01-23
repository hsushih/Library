package library;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class OverdueNoticeTest {
	private Patron stanley;
	private Patron Jack;
	private Library library;
	private Book book1;
	private Book book2;
	private Book book3;
	private OverdueNotice overdue;
	private OverdueNotice overdue2;

	@Before
	public void setUp() throws Exception {
		library = new Library();
		stanley = new Patron("Stanley",library);
		Jack = new Patron("Jack",library);
		book1 = new Book("Contact","Stanley");
		book2 = new Book("War","Jack");
		book3 = new Book("Star War", "Lou");
		book1.checkOut(7);
		book2.checkOut(7);
		book3.checkOut(7);
		stanley.take(book1);
		stanley.take(book2);
		Jack.take(book3);
		overdue = new OverdueNotice(stanley,0);	
		overdue2 = new OverdueNotice(Jack,8);
	}
	
	@Test
	public void testOverdueNotice(){
		assertTrue(overdue instanceof OverdueNotice);
		assertTrue(overdue2 instanceof OverdueNotice);
	} 

	@Test
	public void testToString(){
		//No books overdue
		assertEquals(overdue.toString(),"Stanley has checked out the following books\n[Contact , by Stanley, War , by Jack]\n\n");
	
		//Books overdue
	    assertEquals(overdue2.toString(),"Jack has checked out the following books\n[Star War , by Lou]\n\nStar War , by Lou------ The Due Date is :7 -------This book is OVERDUE already\n");
		
	}

}
