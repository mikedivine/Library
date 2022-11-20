/**
 * Title: Reader
 * Abstract: Purpose of this program is to create a Reader program that checks out books to the Reader
 * Author: Mike Divine
 * Date: 10/30/2022
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader {

  final int CARD_NUMBER_ = 0;
  final int NAME_ = 1;
  final int PHONE_ = 2;
  final int BOOK_COUNT_ = 3;
  final int BOOK_START_ = 4;

  private int cardNumber;
  private String name;
  private String phone;
  private List<Book> books = new ArrayList<>();

  public Reader(int cardNumber, String name, String phone) {
    this.cardNumber = cardNumber;
    this.name = name;
    this.phone = phone;
  }

  public Code addBook(Book book) {

    if (hasBook(book)) {
      return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
    }
    books.add(book);
    return Code.SUCCESS;
  }

  public Code removeBook(Book book) {
     if (hasBook(book)) {
        books.remove(book);
        return Code.SUCCESS;
     }
    return Code.READER_DOESNT_HAVE_BOOK_ERROR;
  }

  public boolean hasBook(Book book) {
    for (int i = 0; i < books.size(); i++) {
      if (book == books.get(i)) {
       return true;
      }
    }
    return false;
  }

  public int getBookCount() {
    return books.size();
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reader reader = (Reader) o;
    return cardNumber == reader.cardNumber && name.equals(reader.name) && phone.equals(reader.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardNumber, name, phone);
  }

  @Override
  public String toString() {
    return name + "(#" +
      cardNumber + ") has checked out" +
      books + "}";
  }
}
