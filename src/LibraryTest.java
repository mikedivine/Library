import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

  Library library1 = new Library("library1");

  @BeforeEach
  void setUp() {
    System.out.println("Before Each");
    assertNotNull(library1);
  }

  @AfterEach
  void tearDown() {
    System.out.println("After Each");
  }

  @Test
  void init() {
    assertTrue(library1.init("Library00.csv") == Code.SUCCESS);
    //library1.listReaders();
    //library1.listBooks();
    //library1.listShelves(true);
  }

  @Test
  void addBook() {
  }

  @Test
  void returnBook() {
  }

  @Test
  void testReturnBook() {
  }

  @Test
  void listBooks() {
  }

  @Test
  void checkOutBook() {
  }

  @Test
  void getBookByISBN() {
  }

  @Test
  void listShelves() {
  }

  @Test
  void addShelf() {
  }

  @Test
  void testAddShelf() {
  }

  @Test
  void getShelf() {
  }

  @Test
  void testGetShelf() {
  }

  @Test
  void listReaders() {
  }

  @Test
  void testListReaders() {
  }

  @Test
  void getReaderByCard() {
  }

  @Test
  void addReader() {
  }

  @Test
  void removeReader() {
  }

  @Test
  void convertInt() {
  }

  @Test
  void convertDate() {
  }

  @Test
  void getLibraryCardNumber() {

  }

  @Test
  void errorCode() {
    System.out.println(library1.errorCode(-1));
    System.out.println(library1.errorCode(-2));
    System.out.println(library1.errorCode(-3));
    System.out.println(library1.errorCode(5));
  }
}