package sg.edu.nus.bookdemo.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	@Size(max=150, message="Title must contain 150 characters or less")
	private String title;
	
	@NotBlank(message="Author is required")
	private String author;
	
	@Min(value=1000, message="Year cannot be earlier than 1000")
//	@MaxYear // default message: Year must be current year or earlier
	private int publicationYear;
	
	@NotBlank(message="Genre is required")
	private String genre;
	
	// Constructors
	public Book() {} // no-arg constructor required for @ModelAttribute
	public Book(Long id, String title, String author, int year, String
	genre) {
	this.id = id;
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
	
}