/**
 * Title: Shelf Test
 * Abstract: Purpose of this program is to test the Shelf Class
 * Author: Mike Divine
 * Date: 11/7/2022
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

  Shelf shelfTest1 = null;
  Shelf shelfTest2 = null;
  Shelf shelfTest3 = null;

  Book bookTest1 = null;
  Book bookTest2 = null;

  private HashMap<Book, Integer> books = new HashMap<>();

  @BeforeEach
  void setUp() {
    System.out.println("Before Each");
    assertNull(shelfTest1);
    shelfTest1 = new Shelf(1,"Science");
    shelfTest2 = new Shelf(1,"Science");
    shelfTest3 = new Shelf(2,"History");
    assertNotNull(shelfTest1);
    bookTest1 = new Book("555-555","TEST-Book","Science",4789,"Big Daddy", LocalDate.now());
    bookTest2 = new Book("444-444","2TEST-Book","History",5567,"someone", LocalDate.now());
    books.put(bookTest1,1);
    books.put(bookTest2,1);
  }

  @AfterEach
  void tearDown() {
    System.out.println("After Each");
  }

  @Test
  void addBook() {
    assertEquals(shelfTest1.addBook(bookTest1), Code.SUCCESS);
    assertEquals(shelfTest1.getBookCount(bookTest1), 1);

    assertEquals(shelfTest1.addBook(bookTest1), Code.SUCCESS);
    assertEquals(shelfTest1.getBookCount(bookTest1), 2);
    System.out.println(shelfTest1.getBookCount(bookTest1));
    assertEquals(shelfTest1.addBook(bookTest2), Code.SHELF_SUBJECT_MISMATCH_ERROR);
  }

  @Test
  void removeBook() {
    assertNotEquals(shelfTest1.removeBook(bookTest1), Code.SUCCESS);
    assertEquals(shelfTest1.removeBook(bookTest1), Code.BOOK_NOT_IN_INVENTORY_ERROR);
    shelfTest1.addBook(bookTest1);
    assertEquals(shelfTest1.getBookCount(bookTest1), 1);
    shelfTest1.removeBook(bookTest1);
    assertEquals(shelfTest1.getBookCount(bookTest1), 0);
  }

  @Test
  void getBookCount() {
    Random rando = new Random();
    int bookAmt = rando.nextInt(20);
    bookAmt++;

    for (int i = 0; i < bookAmt; i++) {
      shelfTest1.addBook(bookTest1);
    }
    assertEquals(shelfTest1.getBookCount(bookTest1),bookAmt);
    shelfTest1.removeBook(bookTest1);
    assertEquals(shelfTest1.getBookCount(bookTest1),bookAmt-1);

    for (int i = 0; i < bookAmt-1; i++) {
      shelfTest1.removeBook(bookTest1);
    }
    assertEquals(shelfTest1.getBookCount(bookTest1),0);
    assertEquals(shelfTest1.getBookCount(bookTest2),-1);
  }

  @Test
  void getBooks() {
    shelfTest1.setBooks(books);
    assertEquals(shelfTest1.getBooks(), books);
  }

  @Test
  void setBooks() {
    shelfTest1.setBooks(books);
    assertEquals(shelfTest1.getBooks(), books);
  }

  @Test
  void setShelfNumber() {
    shelfTest1.setShelfNumber(2);
    assertEquals(shelfTest1.getShelfNumber(), 2);
  }

  @Test
  void setSubject() {
    shelfTest1.setSubject("Computer Science");
    assertEquals(shelfTest1.getSubject(), "Computer Science");
  }

  @Test
  void getSubject() {
    shelfTest1.setSubject("Computer Science");
    assertEquals(shelfTest1.getSubject(), "Computer Science");
  }

  @Test
  void listBooks() {
    shelfTest1.addBook(bookTest1);
    shelfTest1.addBook(bookTest1);
    shelfTest1.addBook(bookTest2);
  }

  @Test
  void getShelfNumber() {
    shelfTest1.setShelfNumber(2);
    assertEquals(shelfTest1.getShelfNumber(), 2);
  }

  @Test
  void testEquals() {
    shelfTest1.setBooks(books);
    shelfTest2.setBooks(books);
    shelfTest3.setBooks(books);
    assertTrue(shelfTest1.equals(shelfTest2));
    assertFalse(shelfTest2.equals(shelfTest3));
  }

  @Test
  void testHashCode() {
    shelfTest1.setBooks(books);
    shelfTest2.setBooks(books);
    shelfTest3.setBooks(books);
    assertEquals(shelfTest1.hashCode(),shelfTest2.hashCode());
    assertNotEquals(shelfTest2.hashCode(),shelfTest3.hashCode());
  }

  @Test
  void testToString() {
    shelfTest1.setBooks(books);
    assertEquals(shelfTest1.toString(),"1 : Science");
  }
}