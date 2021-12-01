package com.baselcode.wrokshoplibraryspring.models;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int authorId;
    private String firstName;
    private String lastName;


    @ManyToMany(
            cascade = {CascadeType.DETACH,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "fk_author_id",table = "author_book"),
            inverseJoinColumns = @JoinColumn(name = "fk_book_id",table = "author_book")
    )
    private Set<Book> writtenBooks;

    public Author(int authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author() {
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getWrittenBooks() {
        return writtenBooks;
    }

    public void setWrittenBooks(Set<Book> writtenBooks) {
        if(writtenBooks == null) writtenBooks = new HashSet<>();

        if(writtenBooks.isEmpty()){
            if(this.writtenBooks != null){
                this.writtenBooks.forEach(wrb -> wrb.getAuthors().remove(this));
            }
        }else {
            writtenBooks.forEach(wrb -> wrb.getAuthors().add(this));
        }

        this.writtenBooks = writtenBooks;
    }
}
