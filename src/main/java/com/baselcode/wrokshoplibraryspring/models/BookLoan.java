package com.baselcode.wrokshoplibraryspring.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookLoanId;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private boolean returned;


    @ManyToOne(
            cascade = {CascadeType.DETACH,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_book_id")
    private Book book;

    @ManyToOne(
            cascade = {CascadeType.DETACH,CascadeType.REFRESH}
            ,fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_app_user_id")
    private AppUser borrower;

    public BookLoan(int bookLoanId, LocalDate loanDate,
                    LocalDate dueDate, boolean returned,
                    AppUser borrower, Book book) {

        this.bookLoanId = bookLoanId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = returned;
        this.borrower = borrower;
        this.book = book;
    }

    public BookLoan() {
    }

    public int getBookLoanId() {
        return bookLoanId;
    }

    public void setBookLoanId(int bookLoanId) {
        this.bookLoanId = bookLoanId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public AppUser getBorrower() {
        return borrower;
    }

    public void setBorrower(AppUser borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
