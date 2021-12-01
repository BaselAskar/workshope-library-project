package com.baselcode.wrokshoplibraryspring.models;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;
    private String isbn;
    private String title;
    private int maxLoanDays;
    private boolean available = true;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "writtenBooks"
    )
    private Set<Author> authors;

    public Book() {
    }


    public Book(int bookId, String isbn, String title, int maxLoanDays) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.maxLoanDays = maxLoanDays;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        this.maxLoanDays = maxLoanDays;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Set<Author> getAuthors() {
        if(authors == null) return new HashSet<>();
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        if(authors == null) authors = new HashSet<>();
        if(authors.isEmpty()){
            if(this.authors != null){
                this.authors.forEach(a -> a.getWrittenBooks().remove(this));
            }
        }else {
            authors.forEach(a -> a.getWrittenBooks().add(this));
        }

        this.authors = authors;
    }

    public void addAuthor(Author author){
        if (author == null) throw new IllegalArgumentException("author is null!!");
        if (!authors.contains(author)) throw new RuntimeException("author is not found");

        authors.remove(author);
        author.getWrittenBooks().remove(this);
    }
}
