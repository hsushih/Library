/**
 * 
 */
package library;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Matuszek
 */
public class LibraryTest {
    private Book contact;
    private Book contact2;
    private Book equalRites;
    private Book sisters;
    private Book witches;
    private Book nightly;
    private Book time;
    private Book rings;
    private ArrayList<Book> books;
    private Library library;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        contact = new Book("Contact", "Carl Sagan");
        equalRites = new Book("Equal Rites", "Terry Pratchett");
        sisters = new Book("Weird Sisters", "Terry Pratchett");
        witches = new Book("Witches Abroad", "Terry Pratchett");
        nightly = new Book("Disappearing Nightly", "Laura Resnick");
        contact2 = new Book("Contact", "Carl Sagan");
        time = new Book("Nick of Time", "Ted Bell");
        rings = new Book("Lord of the rings", "J. R. R. Tolkien");
        books = new ArrayList<Book>();
        books.add(contact);
        books.add(witches);
        books.add(sisters);
        books.add(equalRites);
        books.add(nightly);
        books.add(contact2);
        books.add(time);
        books.add(rings);
        library = new Library(books);
        Patron dave1; 
        
        
    }

    /**
     * Test method for {@link library.Library#open()}.
     */
    @Test
    public void testOpen() {
        // The open() method doesn't make any change that we can
        // readily test, but we can at least make sure it returns
        // an ArrayList<Book>.
        assertEquals(new ArrayList<OverdueNotice>(), library.open());
    }
    
    private Patron openAndServeDave() {
        library.open();
        Patron dave = library.issueCard("Dave");
        library.serve("Dave");
        return dave;
    }
    
    private Patron openAndServeStanley(){
    	library.open();
        Patron stanley = library.issueCard("Stanley");
        library.serve("Stanley");
        return stanley;
    }

    /**
     * Test method for {@link library.Library#createOverdueNotices()}.
     */
    @Test
    public void testCreateOverdueNotices() {
        ArrayList<OverdueNotice> notices;
        
        openAndServeDave();
        ArrayList<Book> foundBooks = library.search("Equal Rites");
        assertEquals(equalRites, foundBooks.get(0));
        library.checkOut(1);
        int dueDate = 7;
        // Don't send an overdue notice during the next seven days
        for (int i = 0; i < dueDate; i++) {
            library.close();
            notices = library.open();
            assertTrue(notices.isEmpty());
        }
        library.close();
         //Send a notice on the 8th day
        notices = library.open();
        assertFalse(notices.isEmpty());
        library.close();
//        // Don't send another notice after that
        notices = library.open();
        assertTrue(notices.isEmpty());
    }

    /**
     * Test method for {@link library.Library#issueCard(java.lang.String)}.
     */
    @Test
    public void testIssueCard() {
    	//issue a libray card
        library.open();
        Patron dave = library.issueCard("Dave");
        assertTrue(dave instanceof Patron);
    }
  
    /**
     * Test method for {@link library.Library#serve(java.lang.String)}.
     */
    @Test
    public void testServe() {
        // We can test if the correct Patron is returned, but not if
        // it's being saved. The tests for checkIn and checkOut can
        // determine this.
        library.open();
        Patron dave = library.issueCard("Dave");
        Patron paula = library.issueCard("Paula");
        assertEquals(dave, library.serve("Dave"));
        assertEquals(paula,library.serve("Paula"));
    }

  
    /**
     * Test method for {@link library.Library#checkOut(int[])}.
     */
    @Test
    public void testCheckOutOneBook() {
    	Patron dave = openAndServeDave();
        library.search("Time");
        ArrayList<Book> booksCheckedOut = library.checkOut(1);
        assertTrue(dave.getBooks().contains(time));
        assertEquals(dave.getBooks(), booksCheckedOut);
        // Book shouldn't still be in library
        ArrayList<Book> booksFound = library.search("Time");
        assertTrue(booksFound.isEmpty());
    }
    
    /**
     * Test method for Check out method
     */
    public void testCheckOutThreeBoooks(){
    	// check out three books
        Patron stanley =openAndServeStanley();
        library.search("nder");
        ArrayList<Book>booksCheckedOut2 = library.checkOut(1,2,3);
        assertTrue(stanley.getBooks().contains("nder"));
        assertEquals(stanley.getBooks().size(),3);
        assertEquals(stanley.getBooks(),booksCheckedOut2);
    }
    

    /**
     * Test method for {@link library.Library#checkOut(int[])}.
     */
    @Test
    public void testCheckOutOneOfMultipleCopies() {
        Patron dave = openAndServeDave();
        ArrayList<Book> booksFound = library.search("Carl Sagan");
        library.checkOut(1);
        // There should still be another copy in the library
        booksFound = library.search("Carl Sagan");
        assertEquals(1, booksFound.size());
    }

  
    /**
     * Test method for {@link library.Library#checkOut(int[])}.
     */
    @Test
    public void testCheckOutBooksInRandomOrder() {
        Patron dave = openAndServeDave();
        library.search("Terry Pratchett");
        library.checkOut(1);
        library.checkOut(3);
        library.checkOut(2);
        ArrayList<Book> davesBooks = dave.getBooks();
        assertTrue(davesBooks.contains(witches));
        assertTrue(davesBooks.contains(sisters));
        assertTrue(davesBooks.contains(equalRites));
        assertEquals(davesBooks.size(),3);
    }
    
    /**
     * Check out more than three books
     */
    public void testCheckOutBooksMoreThanThreeBooks(){
    	Patron stanley =openAndServeStanley();
        library.search("nder");
        ArrayList<Book>booksCheckedOut2 = library.checkOut(1,2,3,4);
        assertEquals(booksCheckedOut2,null);
    }
    
    /**
     * No Patron exists
     */
    public void testCheckedOutBooksNoPattron(){
    	ArrayList<Book>booksCheckedOut2 = library.checkOut(1,2,3);
    	assertEquals(booksCheckedOut2,null);
    }
    
    
    /**
     * Negative input
     */
    public void testCheckedOutNegativeNuumbers(){
    	Patron stanley =openAndServeStanley();
        library.search("nder");
        ArrayList<Book>booksCheckedOut2 = library.checkOut(-1);
        assertEquals(booksCheckedOut2,null);
    }
    
    /**
     * Search First
     */
    public void testCheckedOutSearchFirst(){
    	// before search
    	ArrayList<Book>booksCheckedOut2 = library.checkOut(1,2,3);
    	assertEquals(booksCheckedOut2,null);
    	//after search
    	library.search("nder");
    	booksCheckedOut2 = library.checkOut(1);
    	assertTrue(booksCheckedOut2.contains("nder"));
    }
    

    /**
     * Test method for {@link library.Library#checkIn(int[])}.
     */
    @Test
    public void testCheckInOneBook() {
        Patron dave = openAndServeDave();
        ArrayList<Book> foundBooks = library.search("Disappearing Nightly");
        // Checking out a book moves it from the library to the patron
        library.checkOut(1);
        assertTrue(dave.getBooks().contains(nightly));
        assertTrue(library.search("Disappearing Nightly").isEmpty());
        // Checking in a book moves it back from the patron to the library
        library.serve("Dave");
        library.checkIn(1);
        assertFalse(library.search("Disappearing Nightly").isEmpty());
    }

  
    /**
     * Test method for {@link library.Library#checkIn(int[])}.
     */
    @Test
    public void testCheckInManyBooks() {
        openAndGiveBooksToDave();
        Patron dave = library.serve("Dave");
        ArrayList<Book> davesBooks = dave.getBooks();
        assertEquals(3, davesBooks.size());
        Book someBook = davesBooks.get(1); // Can't be sure which book this is
        library.search(someBook.getTitle()).isEmpty();
    }
    
    private void openAndGiveBooksToDave() {
        Patron dave = openAndServeDave();
        library.search("Terry");
        library.checkOut(1);
        library.checkOut(2);
        library.checkOut(3);
    }
    
    /*
     * No patron exists for CheckIn functions
     */
    @Test
    public void testCheckInBooksNoPatron(){
    	// no patron exists
    	assertEquals(library.checkIn(1,2),null);
    }
    
    /**
     * Neative input for CheckIn functions
     */
    @Test
    public void testCheckInNativeInput(){
    	Patron dave = openAndServeDave();
        library.search("Terry Pratchett");
        library.checkOut(1);
        library.checkOut(3);
        library.checkOut(2);
        ArrayList<Book> davesBooks = dave.getBooks();
        assertEquals(library.checkIn(-1),null);
        assertEquals(davesBooks.size(),3);
    }
    
    /**
     * The number of books you checked in is more than the books you have in check out list
     */
    @Test
    public void testCheckInMoreBooks(){
    	Patron dave = openAndServeDave();
        library.search("Terry Pratchett");
        library.checkOut(1);
        library.checkOut(3);
        library.checkOut(2);
        ArrayList<Book> davesBooks = dave.getBooks();
        assertEquals(library.checkIn(1,2,3,4),null);
        assertEquals(davesBooks.size(),3);
    }

    /**
     * Test method for {@link library.Library#search(java.lang.String)}.
     */
    @Test
    public void testSearchTitle() {
        library.open();
        ArrayList<Book> foundBooks = library.search("appear");
        assertEquals(1, foundBooks.size());
        assertEquals("Disappearing Nightly", foundBooks.get(0).getTitle());
        foundBooks = library.search("xyzzy");
        assertEquals(0, foundBooks.size());
    }

    /**
     * Test method for {@link library.Library#search(java.lang.String)}.
     */
    @Test
    public void testSearchAuthor() {
        library.open();
        ArrayList<Book> foundBooks = library.search("Resnick");
        assertEquals(1, foundBooks.size());
        assertEquals("Laura Resnick", foundBooks.get(0).getAuthor());
    }

    /**
     * Test method for {@link library.Library#search(java.lang.String)}.
     */
    @Test
    public void testSearchWithMixedCase() {
        library.open();
        ArrayList<Book> foundBooks = library.search("laura");
        assertEquals(1, foundBooks.size());
        foundBooks = library.search("NIGHTLY");
        assertEquals(1, foundBooks.size());
        foundBooks = library.search("Nick");
        assertEquals(2, foundBooks.size());
    }

    /**
     * Test method for {@link library.Library#search(java.lang.String)}.
     */
    @Test
    public void testSearchAndIgnoreDuplicates() {
        library.open();
        ArrayList<Book> foundBooks = library.search("Contact");
        assertEquals(1, foundBooks.size());
    }
    
    /**
     * No search results matched
     */
    @Test
    public void testSearchResultsNotMatched(){
    	ArrayList<Book> searched = library.search("lll");
    	assertEquals(searched,null);
    }
    
}
