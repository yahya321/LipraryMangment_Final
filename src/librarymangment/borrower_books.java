package librarymangment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 *
 * @author yahya
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "borrower_books.findAll",
            query = "Select b From borrower_books b"),
    @NamedQuery(name = "borrower_books.findBookunReturned",
            query = "Select b From borrower_books b where b.book_id = :bookid and b.return_date is Null ")
//                query = "Select bo.name as book_name,concat(bb.first_name,' ',bb.last_name) as borrower_name , b.* From borrower_books b join Books bo on bo.id = b.book_id join Borrowers bb on bb.id = b.borrower_id"),

})
public class borrower_books implements Serializable {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     	 	 	 
    private Integer book_id;
    private Integer borrower_id;
    private Timestamp borrowers_date;
    private Timestamp return_date;
//    @ManyToOne
//    @JoinColumn(name = "book_id")
//    Books book;


    public borrower_books() {
    }

    public borrower_books(Integer book_id, Integer borrower_id, Timestamp borrowers_date) {
        this.book_id = book_id;
        this.borrower_id = borrower_id;
        this.borrowers_date = borrowers_date;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public Integer getBorrower_id() {
        return borrower_id;
    }

    public Timestamp getBorrowers_date() {
        return borrowers_date;
    }

    public Timestamp getReturn_date() {
        return return_date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public void setBorrower_id(Integer borrower_id) {
        this.borrower_id = borrower_id;
    }

    public void setBorrowers_date(Timestamp borrowers_date) {
        this.borrowers_date = borrowers_date;
    }

    public void setReturn_date(Timestamp return_date) {
        this.return_date = return_date;
    }
        
}
