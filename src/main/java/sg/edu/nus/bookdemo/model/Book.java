package sg.edu.nus.bookdemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private Integer year;
	private String genre;
	
	// Constructors
	public Book() {} // no-arg constructor required for @ModelAttribute
	public Book(Long id, String title, String author, Integer year, String
	genre) {
	this.id = id;
	this.title = title;
	this.author = author;
	this.year = year;
	this.genre = genre;
	}
	
	// Getters and Setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	public Integer getYear() { return year; }
	public void setYear(int year) { this.year = year; }
	public String getGenre() { return genre; }
	public void setGenre(String genre) { this.genre = genre; }
	
}