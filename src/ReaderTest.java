/**
 * Title: Reader Test
 * Abstract: Purpose of this program is to test the Reader Class
 * Author: Mike Divine
 * Date: 10/29/2022
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

  Reader readerTest1 = null;
  Reader readerTest2 = null;
  Reader readerTest3 = null;
  Book bookTest1 = null;
  Book bookTest2 = null;

  @BeforeEach
  void setUp() {
    System.out.println("Before Each");
    assertNull(readerTest1);
    readerTest1 = new Reader(1234,"Mike Divine","555-555-5555");
    assertNotNull(readerTest1);
    bookTest1 = new Book("555-555","TEST-Book","Under-water basket weaving",4789,"Big Daddy", LocalDate.now());
    bookTest2 = new Book("444-444","2TEST-Book","2 baskets",5567,"someone", LocalDate.now());
  }

  @AfterEach
  void tearDown() {
    System.out.println("After Each");
  }

  @Test
  void addBook() {
    assertEquals(readerTest1.addBook(bookTest1), Code.SUCCESS);
    assertNotEquals(readerTest1.addBook(bookTest1), Code.SUCCESS);
    assertEquals(readerTest1.addBook(bookTest1), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
  }

  @Test
  void getBookCount() {
    readerTest2 = new Reader(1234,"Mike Divine","555-555-5555");
    assertEquals(readerTest2.getBookCount(), 0);
    readerTest2.addBook(bookTest1);
    assertEquals(readerTest2.getBookCount(), 1);
    readerTest2.removeBook(bookTest1);
    assertEquals(readerTest2.getBookCount(), 0);
  }

  @Test
  void removeBook() {
    assertEquals(readerTest1.removeBook(bookTest1), Code.READER_DOESNT_HAVE_BOOK_ERROR);
    assertNotEquals(readerTest1.removeBook(bookTest1), Code.SUCCESS);
    assertEquals(readerTest1.addBook(bookTest1), Code.SUCCESS);
    assertEquals(readerTest1.removeBook(bookTest1), Code.SUCCESS);
  }

  @Test
  void hasBook() {
    readerTest2 = new Reader(1234,"Mike Divine","555-555-5555");
    assertFalse(readerTest2.hasBook(bookTest2));
    readerTest2.addBook(bookTest2);
    assertTrue(readerTest2.hasBook(bookTest2));
  }

  @Test
  void getCardNumber() {
    assertEquals(1234, readerTest1.getCardNumber());
  }

  @Test
  void setCardNumber() {
    readerTest1.setCardNumber(3456);
    assertEquals(3456, readerTest1.getCardNumber());
  }

  @Test
  void getName() {
    assertEquals("Mike Divine", readerTest1.getName());
  }

  @Test
  void setName() {
    readerTest1.setName("Jesse Ivantosh");
    assertEquals("Jesse Ivantosh", readerTest1.getName());
  }

  @Test
  void getPhone() {
    assertEquals("555-555-5555", readerTest1.getPhone());
  }

  @Test
  void setPhone() {
    readerTest1.setPhone("444-444-4444");
    assertEquals("444-444-4444", readerTest1.getPhone());
  }

  @Test
  void testEquals() {
    readerTest2 = new Reader(1234,"Mike Divine","555-555-5555");
    readerTest3 = new Reader(3456,"Dean Ivantosh","444-444-4444");
    assertFalse(readerTest2.equals(readerTest3));
    assertTrue(readerTest1.equals(readerTest2));
  }

  @Test
  void testHashCode() {
    readerTest2 = new Reader(1234,"Mike Divine","555-555-5555");
    readerTest3 = new Reader(3456,"Dean Ivantosh","444-444-4444");
    int hash1 = readerTest1.hashCode();
    int hash2 = readerTest2.hashCode();
    int hash3 = readerTest3.hashCode();
    assertNotEquals(String.valueOf(hash2),String.valueOf(hash3));
    assertEquals(String.valueOf(hash1),String.valueOf(hash2));
    System.out.println("Hash 1: " + hash1);
    System.out.println("Hash 2: " + hash2);
    System.out.println("Hash 3: " + hash3);
  }

  @Test
  void testToString() {
    readerTest3 = new Reader(3456,"Dean Ivantosh","444-444-4444");
    readerTest1.addBook(bookTest1);
    readerTest1.addBook(bookTest2);
    readerTest3.addBook(bookTest2);
    readerTest3.addBook(bookTest1);
    System.out.println("Test toString Method");
    System.out.println(readerTest1.toString());
    System.out.println(readerTest3.toString());
  }
}