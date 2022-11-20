import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class Library {
  public static final int LENDING_LIMIT = 5;
  private String name;
  private static int libraryCard;
  private List<Reader> readers = new ArrayList();
  private HashMap<String, Shelf> shelves = new HashMap<String, Shelf>();
  private HashMap<Book, Integer> books = new HashMap<Book, Integer>();

  public Library(String name) {
    this.name = name;
  }

  public Code init(String fileName) {
    File f = new File(fileName);
    Scanner scan;

    try {
      scan = new Scanner(f);
    } catch (FileNotFoundException e) {
      return Code.FILE_NOT_FOUND_ERROR;
    }

    int objectCount = 0;
    int rounds = 3;
    String line;


    for (int i = 0; i < rounds; i++) {
      line = scan.nextLine();
      objectCount = convertInt(line, Code.BOOK_COUNT_ERROR);

      if (objectCount < 0) {
        System.out.println(errorCode(objectCount));
      } else {

        if (i == 0) {
          initBooks(objectCount, scan);
          listBooks();  //TODO look at this and see if it's correct
        } else if (i == 1) {
          initShelves(objectCount, scan);
        } else if (i == 2) {
          initReader(objectCount, scan);
        } else {
          return Code.UNKNOWN_ERROR;
        }
      }
    }
    return Code.SUCCESS;
  }

  private Code initBooks(int bookCount, Scanner scan) {
    String line;
    String[] lineWords;
    int pageCount = 0;
    LocalDate date;

    if (bookCount <= 0) {
      return Code.LIBRARY_ERROR;
    }
    for (int i = 0; i < bookCount; i++) {
      line = scan.nextLine();
      lineWords = line.split(",");
      Book book = new Book("1","defaultISBN","defaultTitle",1,"defaultAuthor",LocalDate.now());
      book.setIsbn(lineWords[book.ISBN_]);
      book.setTitle(lineWords[book.TITLE_]);
      book.setSubject(lineWords[book.SUBJECT_]);

      pageCount = convertInt(lineWords[book.PAGE_COUNT_],Code.PAGE_COUNT_ERROR);

      if (pageCount <= 0) {
        return Code.PAGE_COUNT_ERROR;
      }
      book.setPageCount(pageCount);
      book.setAuthor(lineWords[book.AUTHOR_]);

      date = convertDate(lineWords[book.DUE_DATE_],Code.DATE_CONVERSION_ERROR);

      if (date == null) {
        return Code.DATE_CONVERSION_ERROR;
      }
      book.setDueDate(date);
      addBook(book);
    }

    return Code.SUCCESS;
  }

  private Code initShelves(int shelfCount, Scanner scan) {
    String line;
    String[] lineWords;
    int shelfNum = 0;
    Shelf shelf = new Shelf(0,"defaultSubject");

    if (shelfCount <= 0) {
      return Code.SHELF_COUNT_ERROR;
    }

    for (int i = 0; i < shelfCount; i++) {
      line = scan.nextLine();
      lineWords = line.split(",");
      shelfNum = convertInt(lineWords[shelf.SHELF_NUMBER_],Code.SHELF_COUNT_ERROR);

      if (shelfNum < 0) {
        return Code.SHELF_COUNT_ERROR;
      }
      shelf.setShelfNumber(shelfNum);
      shelf.setSubject(lineWords[shelf.SUBJECT_]);
      addShelf(shelf);
    }

    if (shelves.size() == shelfCount) {
      return Code.SUCCESS;
    }

    System.out.println("Number of shelves doesn't match expected.");
    return Code.SHELF_NUMBER_PARSE_ERROR;
  }

  private Code initReader(int readerCount, Scanner scan) {
    String line;
    String[] lineWords;
    int readerNum = 0;
    int amtBooks = 0;
    Reader reader = new Reader(0,"defaultName","defaultPhone");
    Book book = null;
    LocalDate date = null;

    if (readerCount <= 0) {
      return Code.READER_COUNT_ERROR;
    }

    for (int i = 0; i < readerCount; i++) {
      line = scan.nextLine();
      lineWords = line.split(",");
      readerNum = convertInt(lineWords[reader.CARD_NUMBER_],Code.READER_CARD_NUMBER_ERROR);

      if (readerNum < 0) {
        return Code.READER_COUNT_ERROR;
      }
      reader.setCardNumber(readerNum);
      reader.setName(lineWords[reader.NAME_]);
      reader.setPhone(lineWords[reader.PHONE_]);
      addReader(reader);
      amtBooks = convertInt(lineWords[reader.BOOK_COUNT_],Code.BOOK_COUNT_ERROR);

      if (amtBooks <= 0) {
        return Code.BOOK_COUNT_ERROR;
      }

      for (int i2 = 0; i2 < amtBooks; i2++) {
        book = getBookByISBN(lineWords[(reader.BOOK_START_) + (2 * i2)]);  // take every other entry for isbn
        date = convertDate(lineWords[(reader.BOOK_START_ + 1) + (2 * i2)],Code.DATE_CONVERSION_ERROR);  // take every other entry for Due Date

        if (book != null) {
          checkOutBook(reader,book);
        } else {
          System.out.println("ERROR");
        }
      }
    }
    return Code.SUCCESS;
  }

  public Code addBook(Book newBook) {

    if (books.containsKey(newBook)) {
      books.replace(newBook,books.get(newBook)+1);
      System.out.println(books.get(newBook) + " copies of " + newBook.getTitle() + " in the stacks.");
    } else {
      books.put(newBook,1);
      System.out.println(newBook.getTitle() + " added to the stacks.");
    }

    if (shelves.containsKey(newBook.getSubject())) {
      shelves.get(newBook.getSubject()).addBook(newBook);
    } else {
      System.out.println("No shelf for " + newBook.getSubject() + " books.");
      return Code.SHELF_EXISTS_ERROR;
    }
    return Code.SUCCESS;
  }

  public Code returnBook(Reader reader, Book book) {

    if (!reader.hasBook(book)) {
      System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out.");
      return Code.READER_DOESNT_HAVE_BOOK_ERROR;
    }

    if (!books.containsKey(book)) {
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    System.out.println(reader.getName() + " is returning " + book +".");
    Code code = reader.removeBook(book);

    if (code == Code.SUCCESS) {
      code = returnBook(book);
      return code;
    }

    System.out.println("Could not return " + book + ".");
    return code;
  }

  public Code returnBook(Book book) {

    if (!shelves.containsKey(book.getSubject())) {
      System.out.println("No Shelf for " + book + ".");
      return Code.SHELF_EXISTS_ERROR;
    }
    shelves.get(book.getSubject()).addBook(book);
    return Code.SUCCESS;
  }

  private Code addBookToShelf(Book book, Shelf shelf) {

    if (returnBook(book) == Code.SUCCESS) {
      return Code.SUCCESS;
    }
    if (!book.getSubject().equals(shelf.getSubject())) {
      return Code.SHELF_SUBJECT_MISMATCH_ERROR;
    }
    Code code = shelf.addBook(book);
    if (code == Code.SUCCESS) {
      System.out.println(book + " added to shelf.");
      return Code.SUCCESS;
    }
    System.out.println("Could not add " + book + " to shelf.");
    return code;
  }

  public int listBooks() {

    int bookCount = 0;

    for (Map.Entry<Book,Integer> mapElement : books.entrySet()) {
      System.out.println(mapElement.getValue() + " copies of " + mapElement.getKey().toString());
      bookCount += mapElement.getValue();
    }
    return bookCount;
  }

  public Code checkOutBook(Reader reader, Book book) {

    if (!readers.contains(reader)) {
      System.out.println(reader.getName() + " doesn't have an account here.");
      return Code.READER_NOT_IN_LIBRARY_ERROR;
    }

    if (reader.getBookCount() > 5) {
      System.out.println(reader.getName() + " has reached the lending limit, " + LENDING_LIMIT + ".");
      return Code.BOOK_LIMIT_REACHED_ERROR;
    }

    if (!books.containsKey(book)) {
      System.out.println("ERROR: could not find " + book + ".");
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    if (!shelves.containsKey(book.getSubject())) {
      System.out.println("No shelf for " + book.getSubject() + "books!");
      return Code.SHELF_EXISTS_ERROR;
    }

    if (shelves.get(book.getSubject()).getBookCount(book) < 1) {
      System.out.println("ERROR: no copies of " + book + " remain.");
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    Code code = reader.addBook(book);
    if (code != Code.SUCCESS) {
      System.out.println("Couldn't checkout " + book + ".");
      return code;
    }

    code = shelves.get(book.getSubject()).removeBook(book);
    if (code == Code.SUCCESS) {
      System.out.println(book + " checked out successfully.");
      return code;
    }

    return code;
  }

  public Book getBookByISBN(String isbn) {

    for (Map.Entry<Book,Integer> mapElement : books.entrySet()) {

      if (mapElement.getKey().getIsbn().equals(isbn)) {
        return mapElement.getKey();
      }
    }
    System.out.println("ERROR: Could not find a book with isbn: " + isbn);
    return null;
  }

  public Code listShelves(boolean showBooks) {

      for (Map.Entry<String,Shelf> mapElement : shelves.entrySet()) {

        if (showBooks == true) {
          mapElement.getValue().listBooks();
        } else {
          mapElement.getValue().toString();
        }
      }
    return Code.SUCCESS;
  }

  public Code addShelf(String shelf) {
    Shelf newShelf = new Shelf(shelves.size()+1,shelf);
    addShelf(newShelf);
    return Code.SUCCESS;
  }

  public Code addShelf(Shelf shelf) {

    if (shelves.containsKey(shelf.getSubject())) {
      System.out.println("ERROR: Shelf already exists " + shelf);
      return Code.SHELF_EXISTS_ERROR;
    }
    shelf.setShelfNumber(shelves.size()+1);
    shelves.put(shelf.getSubject(),shelf);

    for (Map.Entry<Book,Integer> mapElement : books.entrySet()) {

      if (mapElement.getKey().getSubject().equals(shelf.getSubject())) {
        addBookToShelf(mapElement.getKey(),shelf);
      }
    }
    return Code.SUCCESS;
  }

  public Shelf getShelf(int shelfNum) {

    for (Map.Entry<String,Shelf> mapElement : shelves.entrySet()) {

      if (mapElement.getValue().getShelfNumber() == shelfNum) {
        return mapElement.getValue();
      }
    }
    System.out.println("No shelf number " + shelfNum + " found.");
    return null;
  }

  public Shelf getShelf(String subject) {

    for (Map.Entry<String,Shelf> mapElement : shelves.entrySet()) {

      if (mapElement.getValue().getSubject().equals(subject)) {
        return mapElement.getValue();
      }
    }
    System.out.println("No shelf for " + subject + " books.");
    return null;
  }

  public int listReaders() {
    int readerCount = 0;

    for (Reader reader : readers) {
      reader.toString();
      readerCount++;
    }
    return readerCount;
  }

  public int listReaders(boolean showBooks) {
    int readerCount = 0;

    if (showBooks) {

      for (Reader reader : readers) {
        readerCount++;
        System.out.println(reader.getName() + "(#" + readerCount + ") has the following books:");
        HashMap<Book, Integer> books = new HashMap<>();

        for (Map.Entry<String,Shelf> mapElement : shelves.entrySet()) {
          books = mapElement.getValue().getBooks();

          for (Map.Entry<Book, Integer> bookElement : books.entrySet()) {

            if (reader.hasBook(bookElement.getKey())) {
              System.out.println("  " + bookElement.getKey().toString());
            }
          }
        }
      }
      return readerCount;
    } else {
      for (Reader reader : readers) {
        readerCount++;
        System.out.println(reader.toString());

      }
      return readerCount;
    }
  }

  public Reader getReaderByCard(int cardNumber) {

    for (Reader reader : readers) {

      if (reader.getCardNumber() == cardNumber) {
        return reader;
      }
    }
    System.out.println("Could not find a reader with card #" + cardNumber + ".");
    return null;
  }

  public Code addReader(Reader reader) {

    if (readers.contains(reader)) {
      System.out.println(reader.getName() + " already has an account!");
      return Code.READER_ALREADY_EXISTS_ERROR;
    }

    for (Reader allReaders : readers) {

      if (allReaders.getCardNumber() == reader.getCardNumber()) {
        System.out.println(allReaders.getName() + " and " + reader.getName() + " have the same card number!");
        return Code.READER_CARD_NUMBER_ERROR;
      }
    }
    readers.add(reader);
    System.out.println(reader.getName() + " added to the library!");

    if (reader.getCardNumber() > getLibraryCardNumber()) {
      libraryCard = reader.getCardNumber();
    }
    return Code.SUCCESS;
  }

  public Code removeReader(Reader reader) {

    if (readers.contains(reader)) {

      if (reader.getBookCount() > 0) {
        System.out.println(reader.getName() + " must return all books!");
        return Code.READER_STILL_HAS_BOOKS_ERROR;
      }
      readers.remove(reader);
      return Code.SUCCESS;
    }
    System.out.println(reader.getName() + " is not part of this Library.");
    return Code.READER_NOT_IN_LIBRARY_ERROR;
  }

  public static int convertInt(String recordCountString, Code code) {
    int i = 0;

    try {
      i = Integer.parseInt(recordCountString);
    } catch (NumberFormatException e) {

      if (code.equals(Code.BOOK_COUNT_ERROR)) {
        System.out.println("Value which caused the error: " + recordCountString);
        System.out.println("Error: Could not read number of books");
        return Code.BOOK_COUNT_ERROR.getCode();
      } else if (code.equals(Code.PAGE_COUNT_ERROR)) {
        System.out.println("Value which caused the error: " + recordCountString);
        System.out.println("Error: could not parse page count");
        return Code.PAGE_COUNT_ERROR.getCode();
      } else if (code.equals(Code.DATE_CONVERSION_ERROR)) {
        System.out.println("Value which caused the error: " + recordCountString);
        System.out.println("Error: Could not parse date component");
        return Code.DATE_CONVERSION_ERROR.getCode();
      } else {
        System.out.println("Value which caused the error: " + recordCountString);
        System.out.println("Error: Unknown conversion error");
        return Code.UNKNOWN_ERROR.getCode();
      }
    }
    return i;
  }

  public static LocalDate convertDate(String date, Code errorCode) {

    if (date.equals("0000")) {
      return LocalDate.of(1970,01,01);
    }

    String[] dateSplit;
    dateSplit = date.split("-");

    if (dateSplit.length != 3) {
      System.out.println("ERROR: date conversion error, could not parse " + date);
      System.out.println("Using default date (01-jan-1970)");
      return LocalDate.of(1970,01,01);
    }

    int testValue = 0;
    String[] dateStuff = {"Year","Month","Day"};
    int fullDate[];
    fullDate = new int[3];

    for (int i = 0; i < 3; i++) {
      testValue = convertInt(dateSplit[i],Code.DATE_CONVERSION_ERROR);
      fullDate[i] = testValue;

      if (testValue <= 0) {
        System.out.println("Error converting date: " + dateStuff[i] + testValue);
        System.out.println("Using default date (01-jan-1970)");
        return LocalDate.of(1970,01,01);
      }
    }
    return LocalDate.of(fullDate[0],fullDate[1],fullDate[2]);
  }

  public static int getLibraryCardNumber() {
    return libraryCard;
  }

  public Code errorCode(int codeNumber) {

    try {

      for(Code c : Code.values()) {
        if (c.getCode() == codeNumber) {
          return c;
        }
      }

    } catch (Exception e) {
      return Code.UNKNOWN_ERROR;
    }
    return Code.UNKNOWN_ERROR;
  }
}