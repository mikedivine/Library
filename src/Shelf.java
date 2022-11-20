/**
 * Title: Shelf
 * Abstract: Purpose of this program is to create a Shelf program that stores books on Shelves
 * Author: Mike Divine
 * Date: 11/5/2022
 */

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Shelf {
  final int SHELF_NUMBER_ = 0;
  final int SUBJECT_ = 1;

  private int shelfNumber;
  private String subject;

  private HashMap<Book, Integer> books = new HashMap<>();

  public Shelf(int shelfNumber, String subject) {
    this.shelfNumber = shelfNumber;
    this.subject = subject;
  }

  public Code addBook(Book book) {

    if (books.containsKey(book)) {
      books.replace(book,books.get(book)+1);
      System.out.println(book.toString() + " added to shelf " + this.toString());
      return Code.SUCCESS;
    }

    if ((book.getSubject()).equals(subject)) {
      books.put(book,1);
      System.out.println(book.toString() + " added to shelf " + this.toString());
      return Code.SUCCESS;
    }

    return Code.SHELF_SUBJECT_MISMATCH_ERROR;
  }

  public Code removeBook(Book book) {
    if (!books.containsKey(book)) {
      System.out.println(book.getTitle() + " is not on shelf " + this.subject);
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }
    if (books.get(book) == 0) {
      System.out.println("No copies of " + book.getTitle() + " remain on shelf " + this.subject);
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    if (books.get(book) > 0) {
      books.replace(book,books.get(book)-1);
      System.out.println(book.getTitle() + " successfully removed from shelf " + this.subject);
      return Code.SUCCESS;
    }
    return Code.UNKNOWN_ERROR;
  }

  public int getBookCount(Book book){
    if (books.containsKey(book)) {
      return books.get(book);
    }
    return -1;
  }

  public HashMap<Book, Integer> getBooks() {
    return this.books;
  }

  public void setBooks(HashMap<Book, Integer> books) {
    this.books = books;
  }

  public void setShelfNumber(int shelfNumber) {
      this.shelfNumber = shelfNumber;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSubject() {
    return this.subject;
  }

  public String listBooks() {
    int amountOnShelf = 0;

    for (int i : this.books.values()) {
      amountOnShelf = amountOnShelf + i;
    }

    for (int i : this.books.values()) {
      amountOnShelf = amountOnShelf + i;
    }

    return amountOnShelf + " books on shelf: " +
      this.toString() + " " + books;
  }

  public int getShelfNumber() {
    return this.shelfNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Shelf shelf = (Shelf) o;
    return shelfNumber == shelf.shelfNumber && Objects.equals(subject, shelf.subject) && Objects.equals(books, shelf.books);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shelfNumber, subject, books);
  }

  @Override
  public String toString() {
    return this.shelfNumber + " : " +
      this.subject;
  }

}
