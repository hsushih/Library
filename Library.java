package library;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Library {

	private ArrayList<Book> collection;
	private boolean okToPrint ; 
	private Calendar calendar= new Calendar();
	private boolean libraryOpen = false;
	private Patron patron = null;
	private ArrayList<Book> searchedBooks;
	private HashMap<String, Patron> listOfPatrons = new HashMap<String,Patron>();
	private HashMap<Integer,Book> listOfBooksCheckedOut = new HashMap<Integer,Book>();


	/**
	 * Constructor 1
	 */
	public Library(){
		this.okToPrint = true;
		this.listOfPatrons = new HashMap<String, Patron>();
		this.collection = this.readBookCollection();
	}


	/**
	 * Constructor2
	 * @param collection
	 */
	public Library(ArrayList<Book> collection){
		this.okToPrint = false;
		this.collection = collection;
	}


	/**
	 * reading books from the file
	 * @return
	 */
	private ArrayList<Book> readBookCollection() {
		File file = new File("books.txt");
		ArrayList<Book> collection = new ArrayList<Book>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				line = line.trim();
				if (line.equals("")) continue; // ignore possible blank lines
				String[] bookInfo = line.split(" :: ");
				collection.add(new Book(bookInfo[0], bookInfo[1]));
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return collection;
	}



	/**
	 * Open the library 
	 * set the libraryOpen to true
	 * send out Overdue Notice to patrons
	 * return createOverdueNotice List
	 * @return
	 */
	public ArrayList<OverdueNotice>open(){
		calendar.advance();
		libraryOpen = true;
		this.println("Good monring !! The library is now open now !!");
		return this.createOverdueNotice();		
	}

	/**
	 * print out message input by print
	 * @param message
	 */
	public void print(String message){
		if(this.okToPrint == true){
			System.out.print(message);
		}
	}

	/**
	 * print out message input by println
	 * @param message
	 */

	public void println(String message){
		if(this.okToPrint == true){
			System.out.println(message);
		}
	}

	/**
	 * Welcome message
	 */
	public void PrintTheMessage(){
		this.println("Open -- open the library");
		this.println("IssueCard -- Issue a card to a patron");
		this.println("Serve -- Serve a patron and check his/her check out book list");
		this.println("CheckInBooks -- Help Patrons check in books");
		this.println("Search -- search library books by typing keywords");
		this.println("CheckOutBooks -- Help patrons check out books");
		this.println("Close -- close the library");
		this.println("Quit -- quit the system ");

	}

	/**
	 * option1 -- open the library
	 * option 2 -- issueCard
	 * option 3 -- serve
	 * option 4 -- Check In Books
	 * option 5 -- search
	 * option 6 -- Check Out Books
	 * option 7 -- close the library
	 * option 8 -- quit the system 
	 * Start function just basically combines every piece of function written in the program'
	 * and get user's command to activate that function
	 */
	public void start(){
		Scanner scanner;
		String option;
		scanner = new Scanner(System.in);
		this.println("welcome to the library system! Suggestions :  open the library system by typing Open first otherwise the system will not wake up");
		this.PrintTheMessage();
		while(scanner.hasNext()){
			option = scanner.next().toLowerCase();
			if(option.equals( "open")){
				ArrayList<OverdueNotice> notices;
				notices = this.createOverdueNotice();
				if(notices.isEmpty()){
					this.open();
					this.println("Now! You have to issue a card to people who don't have a library card by typing IssueCard");
					this.println("For people who have a libray card already, You have to check if how many books each person");
					this.println("has checked out by typing serve before you can do check in, check out and search books for them");
				}else{
					this.open();
					this.println("The OverdueNotice has been send " + "\n");
					for(OverdueNotice notice : notices){
						this.println(notice +"\n");
					}
					this.println("Now! You have to issue a card to people who don't have a library card by typing IssueCard");
					this.println("For people who have a libray card already, You have to check if how many books each person");
					this.println("has checked out by typing serve before you can do check in, check out and search books for them");
				}

			}

			else if(option.equals("issuecard")){
				if(this.libraryOpen == false){
					this.println(" the library is not closed! You are not able to issue a library card! You have to open the system");
				}
				else{
					this.println("please type a name ");
					String name = scanner.next();
					this.issueCard(name);
					this.println("Once the card has been issued, we have to put them in the system by typing serve");
					this.println("By doing this, we can also create a check out list for them");
				}
			}

			else if(option.equals("serve")){
				if(this.libraryOpen == false){
					this.println("You cannot serve a customer while the library is closed! you have to open the sysytem first");
				}
				else{
					this.println("Please enter the name of the person you want to serve");
					String name = scanner.next();
					this.serve(name);
					this.println("Now, if you want ot check out books, you have to search books in our system");
					this.println("by typing search and a line will pop up asking what books are you looking for");
					this.println("If you just want to check in books, you just need to type checkInBooks");
				}
			}

			else if (option.equals("checkinbooks")){
				if(this.libraryOpen == false){
					this.println("you cannot check in books while the library is closed! you have to open the system first");
				}
				else{
					this.println("Please enter the books numbers of the books you want to check in! You are allowed to check in up to three books at one time");
					this.println("you should type book numbers like 1,2,3 otherwise the system won't work! thanks your cooperation");
					String numbers = scanner.next();
					String[] bookNumbers = numbers.split(",");
					for(String booksnumbers : bookNumbers){
						this.checkIn(Integer.parseInt(booksnumbers));
					}
				}
			}

			else if (option.equals("search")){
				if(this.libraryOpen == false){
					this.println("you cannot search books while the library is closed. You have to open the system first");
				}
				else{
					this.println("please type at least four characers for searching books ");
					String words = scanner.next();
					this.search(words);
				}
			}

			else if(option.equals("checkoutbooks")){
				if(this.libraryOpen == false){
					this.println("you cannot check out books while the library is closed. You have to open the system first");
				}
				else{
					this.println("Please enter the books numbers of the books you want to check out! You are allowed to check out up to three books at one time");
					this.println("you should type book numbers like 1,2,3 otherwise the system won't work! thanks your cooperation");
					String numbers = scanner.next();
					String[] bookNumbers = numbers.split(",");
					if(bookNumbers.length > 3){
						this.println("you are allowed to check out up to 3 books at one time");
					}
					else{
						for(String books:bookNumbers){
							this.checkOut(Integer.parseInt(books));
						}
					}
				}
			}

			else if(option.equals("close")){
				this.close();
			}

			else if(option.equals("quit")){
				this.println("Enjoy your da");
				break;
			}
			else{
				System.out.println("please enter a valid option");
			}
		}
		scanner.close();

	}

	/**
	 * Send out Overdue notices to all the patrons who have checked out books overdue
	 * and also list all books checked out by that patron
	 * This function returns Overdue Notice List
	 * @return
	 */
	public ArrayList<OverdueNotice>createOverdueNotice(){
		ArrayList<OverdueNotice> overdueNoticeList = new ArrayList<OverdueNotice>();
		for(String patrons: this.listOfPatrons.keySet()){
			Patron Patrons =this.listOfPatrons.get(patrons);
			ArrayList<Book> listOfBooks = Patrons.getBooks();
			for(Book books : listOfBooks){
				OverdueNotice dueNotice = new OverdueNotice(Patrons, this.calendar.getDate());
				if(this.calendar.getDate() -1 == books.getDueDate()){
					overdueNoticeList.add(dueNotice);
					break;
				}
			}
		}
		return overdueNoticeList;
	}

	/**
	 * Issues a library card to the person with this name. (What this actually does is, it
       creates a Patron object, and saves it as the value in a HashMap, with the patron's
       name as the key. No patron can have more than one library card. The created Patron
       object is returned as the value of the method.
	 * @param nameOfPatron
	 * @return
	 */
	public Patron issueCard(String nameOfPatron){
		Patron patrons;

		// Invalid name not allowed
		if(nameOfPatron.length() <1){
			this.println("please enter the valid name");
		}

		// Check whether the patron exists
		if(this.listOfPatrons.containsKey(nameOfPatron)){
			this.println("This person has a library card already ");
			patrons = this.listOfPatrons.get(nameOfPatron);
			return patrons;
		}
		else{
			patrons = new Patron(nameOfPatron, this);
			this.listOfPatrons.put(nameOfPatron, patrons);
		}
		System.out.println("The library card has been issued!! Awesome!");
		return patrons;
	}

	/**
	 * Begin checking books out to (or in from) the named patron. The purpose of this
       method is so that the librarian doesn't have to type in the person's name again and
       again for every book that is to be checked in or checked out. What the method should
       actually do is look up the patron's name in the HashMap, and save the returned Patron
       object in an instance variable of this Library. In addition, the method should return
       the Patron object.
	 * @param nameOfPatron
	 * @return
	 */
	public Patron serve(String nameOfPatron){
		// the patron has to be created 
		if(!this.listOfPatrons.containsKey(nameOfPatron)){
			this.println("The patron does not even exist");
			return null;
		}

		this.patron = this.listOfPatrons.get(nameOfPatron);

		// check whether the patron has any book checked out
		if(this.patron.getBooks().size() < 1){
			this.print("they don't have any books checked out " + "\n");
		}
		else{
			int i =1;
			this.println(nameOfPatron + " has the following books checked out");
			for(Book books : this.patron.getBooks()){
				this.println(i + " .  " +books.toString());
				this.listOfBooksCheckedOut.put(i, books);
				i++;
			}
		}
		return this.patron;				
	}


	/**
	 * The listed books are being returned by the current Patron (there must be one!), so
       return them to the collection and remove them from the list of books currently checked
       out to the patron. The bookNumbers are taken from the list printed by the serve
       command. Checking in some books should not alter the printed book numbers of any
       other books. Checking in a Book will involve both telling the Book that it is checked
       in and returning the Book to this Library's collection of available Books. Returns a
       list of the books just checked in.
	 * @param bookNumbers
	 * @return
	 */
	public ArrayList<Book>checkIn(int... bookNumbers){
		ArrayList<Book> ListOfCheckedInBooks = new ArrayList<Book>();

		// The patron has to be created 
		if(this.patron == null){
			this.println("The patron does not exist");
			return null;
		}

		// You are only allowed to check in all the books you have in the check out list. Any number beyond that won't work
		if(this.patron.getBooks().size() < bookNumbers.length){
			this.println("either you didn't check out any book or the number of books checked out in your checked out list does not match what you have typed");
			return null;
		}

		//Check whether the input value has a negative number	
		for(int i=0; i < bookNumbers.length; i++){
			int index = bookNumbers[i] - 1;
			if(index < 0){
				this.println(" at least one of your book numbers is negatvie");
				return null;
			}
		}
		for(int bookNumber :bookNumbers ){
			int index = bookNumber ;
			//			Book returnedBook = this.patron.getBooks().get((index));
			Book returnedBook = this.listOfBooksCheckedOut.get(index);
			returnedBook.checkIn();
			this.collection.add(returnedBook);
			ListOfCheckedInBooks.add(returnedBook);
			this.println(returnedBook+"----------CHECKED IN");
		}

		for(Book books : ListOfCheckedInBooks){
			this.patron.giveBack(books);
		}

		return ListOfCheckedInBooks;
	}

	/**
	 * Prints out, and saves in an instance variable, an ArrayList<Book> of books whose
       title or author (or both) contains this string. For example, the string "tact" might
       return, among other things, the book Contact, by Carl Sagan. The search should
       be case-insensitive; that is, "saga" would also return this book. Only books which
       are currently available (not checked out) will be returned. In addition, to keep from
       returning too many books, require that the search string be at least 4 characters long.
       If multiple copies of a book are found (for instance, three copies of Contact), only one
       copy should be printed. Returns a list of the books just found.
	 * @param part
	 * @return
	 */
	public ArrayList<Book> search(String part){

		// The input value has to be at least 4-character-long
		if(part.length() < 4){
			this.println("the entered string should be at 4-character-long");
			return null;
		}

		part = part.toLowerCase();

		HashMap<String,Book>SearchedBooks = new HashMap<String,Book>();
		for(int i=0 ; i < this.collection.size(); i++){
			Book books =this.collection.get(i);
			if(books.toString().toLowerCase().contains(part)){
				if(!SearchedBooks.containsKey(books.getTitle())){
					SearchedBooks.put(books.getTitle(), books);
				}
			}
		}

		// Check whether input keywords match with any books in the library collection
		if(SearchedBooks.size() == 0){
			this.searchedBooks = new ArrayList<Book>();
			this.println("no books matched the kewrod you just entered");
			return this.searchedBooks;
		}

		else{
			this.searchedBooks = new ArrayList<Book>(SearchedBooks.values());
			int i =1;
			this.println(" Books that matched your keywords are listed below");
			for(Book books : this.searchedBooks){
				System.out.println(i + " . " + books);
				i++;
			}
		}
		this.println("Do you want to check out any books listed above! if you do please type CheckOutBooks");
		return this.searchedBooks;
	}


	/**
	 * Either checks out the book to the Patron currently being served (there must be one!),
     or tells why the operation is not permitted. The bookNumbers are one or more of the
     books returned by the most recent call to the search method. Checking out a Book 7
     will involve both telling the Book that it is checked out and removing the Book from
     this Library's collection of available Books. Returns a list of the books just checked
     out.
	 * @param bookNumbers
	 * @return
	 */
	public ArrayList<Book>checkOut(int... bookNumbers){

		// The patron has to be created first
		if(this.patron == null){
			this.println("The patron does not exist");
			return null;
		}

		// Check whether one of the input value is negative or not
		for(int i=0; i < bookNumbers.length; i++){
			int index = bookNumbers[i] - 1;
			if(index < 0){
				this.println(" at least one of your book numbers is negatvie");
				return null;
			}
		}

		// The patron has to search the books first before he/she can check out books
		if(this.searchedBooks == null){
			this.println("you have to search books first");
			return null;
		}


		// Every Patron is allow to check out 3 books the most at one time
		if(bookNumbers.length > 3){
			this.println("You are allowed to check out up to 3 books at one time");
			return null;
		}
		else{
			ArrayList<Book> booksCheckedOut = new ArrayList<Book>();
			for(int number : bookNumbers){
				Book books = this.searchedBooks.get(number - 1);
				books.checkOut(this.calendar.getDate() + 7);
				this.listOfBooksCheckedOut.put(number, books);
				this.patron.take(books);
				this.collection.remove(books);
				booksCheckedOut.add(books);
				this.println(books+ "---------CHECK OUT");
			}
			return booksCheckedOut;
		}
	}

	/**
	 * Shut down operations and go home for the night. None of the other operations (except
       quit) can be used when the library is closed.
	 */
	public void close(){
		this.println("The library is closed! Good Nignt! ");
		this.libraryOpen = false;
		this.patron = null;
		this.searchedBooks = null;
	}

	/**
	 * quit the program
	 */
	public void quit(){

	}

	public static void main(String[] args) {
		Library library = new Library();
		library.start();
	}








}
