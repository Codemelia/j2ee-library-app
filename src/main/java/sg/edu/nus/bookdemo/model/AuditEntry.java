package sg.edu.nus.bookdemo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="audits")
public class AuditEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String action;
	
	@Column(nullable=false)
	private LocalDateTime entryDate;
	
	// AuditEntry : Book -> OneToOne
	// audit entry as owning side
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="book_id")
	private Book book;

	public Long getId() { return id; }
//	public void setId(Long id) { this.id = id; }
	public String getAction() { return action; }
	public void setAction(String action) { this.action = action; }
	public LocalDateTime getEntryDate() { return entryDate; }
	public void setEntryDate(LocalDateTime entryDate) { this.entryDate = entryDate; }
	public Book getBook() { return book; }
	public void setBook(Book book) { this.book = book; }
	
	public AuditEntry() {}
	public AuditEntry(String action, Book book) {
		this.action = action;
		this.book = book;
		this.entryDate = LocalDateTime.now();
	}
	
}
