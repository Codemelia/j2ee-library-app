package sg.edu.nus.bookdemo.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
//import sg.edu.nus.bookdemo.validator.MaxYear;

@Entity
@Table(name="books")
public class Book implements Serializable {
	
	// default serializable UID
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Title is required")
	@Column(nullable=false, length=150)
	private String title;
	
	@NotBlank(message="Author is required")
	@Column(nullable=false, length=100)
	private String author;
	
	@Min(value=1000, message="Year cannot be earlier than 1000")
//	@MaxYear // using Spring Validator (binded to Controller)
	private int publicationYear;
	
	@NotBlank(message="Genre is required")
	private String genre;
	
	@OneToMany(mappedBy="book")
	private List<LoanRecord> records;
	
	// Constructors
	public Book() {} // no-arg constructor required for @ModelAttribute
	public Book(String title, String author, int year, String
	genre) {
		this.title = title;
		this.author = author;
		this.publicationYear = year;
		this.genre = genre;
	}
	
	// Getters and Setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	public int getYear() { return publicationYear; }
	public void setYear(int year) { this.publicationYear = year; }
	public String getGenre() { return genre; }
	public void setGenre(String genre) { this.genre = genre; }
	public List<LoanRecord> getLoanRecords() { return records; }
	public void setLoanRecords(List<LoanRecord> records) { this.records = records; }
	
}