package sg.edu.nus.bookdemo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

import sg.edu.nus.bookdemo.model.Book;

@Service
public class BookService {

	private final List<Book> books = new ArrayList<>();
	private final AtomicLong idCounter = new AtomicLong(1); // id start from 1
	
	// construct on object instantiation
	// preload book data
	public BookService() {
		books.add(new Book(idCounter.getAndIncrement(),
			"Clean Code", "Robert C. Martin", 2008, "Technology"));
		books.add(new Book(idCounter.getAndIncrement(),
			"The Pragmatic Programmer", "Hunt & Thomas", 1999, "Technology"));
		books.add(new Book(idCounter.getAndIncrement(),
				"Poopoopoo", "George Orwell", 1949, "Fiction"));
		books.add(new Book(idCounter.getAndIncrement(),
				"Peepeepee", "Pyre Martin", 1989, "Fiction"));
	}
	
	// retrieve all books
	public List<Book> findAll() {
		return books;
	}
	
	// retrieve book by id
	public Optional<Book> findById(Long id) {
		return books.stream()
				.filter(b -> id.equals(b.getId()))
				.findFirst();
	}
	
	// save a book
	public boolean save(Book b) {
		b.setId(idCounter.getAndIncrement());
		return books.add(b);
	}
	
	// search book
	public List<Book> searchBooks(String q) {
		if (q == null || q.isBlank()) return books;
		String query = q.trim().toLowerCase();
		
		// try convert to num
		// if conversion fails, treat as string and search string fields
		// else search num fields
		try {
			int qNum = Integer.parseInt(query);
			return books.stream()
				.filter(b ->
					(b.getId() != null && b.getId().equals((long) qNum)) ||
					(b.getYear() != 0 && b.getYear() == qNum)
				).toList();
		} catch (NumberFormatException e) {
			return books.stream()
				.filter(b ->
					containsIgnoreCase(b.getAuthor(), query) ||
					containsIgnoreCase(b.getTitle(), query) ||
					containsIgnoreCase(b.getGenre(), query)
				).toList();
		}
	}
	
	// helper to filter searches
	private boolean containsIgnoreCase(String field, String query) {
		return field != null && field.toLowerCase().contains(query);
	}
	
 	// delete book by id
	public boolean deleteById(Long id) {
		if (id == null || id <= 0) return false;
		return books.removeIf(b -> id.equals(b.getId()));
	}
	
}
