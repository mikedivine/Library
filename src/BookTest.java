/**
 * Title: Book Test
 * Abstract: Purpose of this program is to test the Book Class
 * Author: Mike Divine
 * Date: 10/29/2022
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

  Book bookTest1 = null;
  Book bookTest2 = null;
  Book bookTest3 = null;



  @BeforeEach
  void setUp() {
    System.out.println("Before Each");
    assertNull(bookTest1);
    bookTest1 = new Book("555-555","TEST-Book","Under-water basket weaving",4789,"Big Daddy", LocalDate.now());
    assertNotNull(bookTest1);
    bookTest2 = new Book("777-777","2nd Book","2nd Subject",2,"2nd Author", LocalDate.now());
    bookTest3 = new Book("777-777","2nd Book","2nd Subject",2,"2nd Author", LocalDate.now());
  }

  @AfterEach
  void tearDown() {
    System.out.println("After Each");
  }

  @Test
  void getIsbn() {
    System.out.println("ISBN: " + bookTest1.getIsbn());
    assertEquals("555-555",bookTest1.getIsbn());
  }

  @Test
  void setIsbn() {
    bookTest1.setIsbn("444-444");
    assertEquals("444-444",bookTest1.getIsbn());
    System.out.println("New ISBN: " + bookTest1.getIsbn());
  }

  @Test
  void getTitle() {
    System.out.println("Title: " + bookTest1.getTitle());
    assertEquals("TEST-Book",bookTest1.getTitle());
  }

  @Test
  void setTitle() {
    bookTest1.setTitle("Newer Title");
    assertEquals("Newer Title",bookTest1.getTitle());
    System.out.println("New Title: " + bookTest1.getTitle());
  }

  @Test
  void getSubject() {
    System.out.println("Subject: " + bookTest1.getSubject());
    assertEquals("Under-water basket weaving",bookTest1.getSubject());
  }

  @Test
  void setSubject() {
    bookTest1.setSubject("Newer Subject");
    assertEquals("Newer Subject",bookTest1.getSubject());
    System.out.println("Newer Subject: " + bookTest1.getSubject());
  }

  @Test
  void getPageCount() {
    System.out.println("Page Count: " + String.valueOf(bookTest1.getPageCount()));
    assertEquals(4789,bookTest1.getPageCount());
  }

  @Test
  void setPageCount() {
    bookTest1.setPageCount(5555);
    assertEquals(5555, bookTest1.getPageCount());
    System.out.println("Newer Page Count: " + String.valueOf(bookTest1.getPageCount()));
  }

  @Test
  void getAuthor() {
    System.out.println("Author: " + bookTest1.getAuthor());
    assertEquals("Big Daddy",bookTest1.getAuthor());
  }

  @Test
  void setAuthor() {
    bookTest1.setAuthor("Newer Author");
    assertEquals("Newer Author",bookTest1.getAuthor());
    System.out.println("Newer Author: " + bookTest1.getAuthor());
  }

  @Test
  void getDueDate() {
    System.out.println("Due Date: " + bookTest1.getDueDate());
    LocalDate testDate = LocalDate.now();
    assertEquals(testDate,bookTest1.getDueDate());
  }

  @Test
  void setDueDate() {
    LocalDate yesterday = LocalDate.now();
    LocalDate yesterday2 = LocalDate.now();
    yesterday = yesterday.minusDays(1);
    yesterday2 = yesterday2.minusDays(1);
    bookTest1.setDueDate(yesterday);
    assertEquals(yesterday2,bookTest1.getDueDate());
    System.out.println("Newer Due Date: " + bookTest1.getDueDate());
  }

  @Test
  void testEquals() {
    assertFalse(bookTest1.equals(bookTest2));
    assertTrue(bookTest2.equals(bookTest3));
    System.out.println("Book 2 and 3 are equal.");

  }

  @Test
  void testHashCode() {
    int hash1 = bookTest1.hashCode();
    int hash2 = bookTest2.hashCode();
    int hash3 = bookTest3.hashCode();
    assertNotEquals(String.valueOf(hash1),String.valueOf(hash2));
    assertEquals(String.valueOf(hash2),String.valueOf(hash3));
    System.out.println("Hash 1: " + hash1);
    System.out.println("Hash 2: " + hash2);
    System.out.println("Hash 3: " + hash3);
  }

  @Test
  void testToString() {
    System.out.println("Test toString Method");
    System.out.println(bookTest1.toString());
    System.out.println(bookTest2.toString());
  }
}