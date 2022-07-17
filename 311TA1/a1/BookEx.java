package a1;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

// part a: modify the Book class to implement Comparable<Book>
class Book implements Comparable<Book> {
    private String title;
    private int publicationYear;
    
    public Book(String title, int publicationYear) {
        this.title = title;
        this.publicationYear = publicationYear;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getPublicationYear() {
        return publicationYear;
    }
    
    @Override
    public String toString() {
        return title + " (" + publicationYear + ")";
    }
    
    public int compareTo(Book other) {
        return this.toString().compareTo(other.toString());
    }
}

public class BookEx {
    public static void main(String[] args) {
        var books = Arrays.asList(
           new Book("Core Java Volume I--Fundamentals", 2018),
           new Book("Java How to Program, Early Objects", 2017),
           new Book("Introduction to Java Programming and Data Structures",
2017),
           new Book("Core Java Volume I--Fundamentals", 2015),
           new Book("Operating System Concepts", 2021));
        System.out.println(books);
        Collections.sort(books);
        System.out.println(books);
        //Part(b)
        Collections.sort(books, Comparator.comparing(Book::getPublicationYear).thenComparing(Book::getTitle));
        System.out.println(books);
    }
}