package sg.edu.nus.bookdemo.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="loan-records")
public class LoanRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// loan record : book -> Many : One
	// loan record as owning side
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="book_id")
	private Book book;
	
	@Column(nullable=false)
	private String borrowerName;
	
	private LocalDate loanDate;
	private LocalDate returnDate;
	private boolean returned = false;
	
	public Long getId() { return id; }
//	public void setId(Long id) { this.id = id; }
	public Book getBook() { return book; }
	public void setBook(Book book) { this.book = book; }
	public LocalDate getLoanDate() { return loanDate; }
	public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
	public LocalDate getReturnDate() { return returnDate; }
	public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
	public boolean isReturned() { return returned; }
	public void setReturned(boolean returned) { this.returned = returned; }
	
	public LoanRecord() {}
	public LoanRecord(Book book, String borrowerName) {
		this.book = book;
		this.borrowerName = borrowerName;
		this.loanDate = LocalDate.now();
	}
	
}
