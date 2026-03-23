package sg.edu.nus.bookdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.bookdemo.model.Book;
import sg.edu.nus.bookdemo.repository.BookRepository;

@Transactional(readOnly=true) // put class level default
@Service
public class BookService {
	
	@Autowired
	private BookRepository bRepo;

//	private final List<Book> books = new ArrayList<>();
//	private final AtomicLong idCounter = new AtomicLong(1); // id start from 1
//	
//	// construct on object instantiation
//	// preload book data
//	public BookService() {
//		books.add(new Book(idCounter.getAndIncrement(),
//			"Clean Code", "Robert C. Martin", 2008, "Technology"));
//		books.add(new Book(idCounter.getAndIncrement(),
//			"The Pragmatic Programmer", "Hunt & Thomas", 1999, "Technology"));
//		books.add(new Book(idCounter.getAndIncrement(),
//				"Poopoopoo", "George Orwell", 1949, "Fiction"));
//		books.add(new Book(idCounter.getAndIncrement(),
//				"Peepeepee", "Pyre Martin", 1989, "Fiction"));
//	}
	
	// now combined with search book function
//	// retrieve all books
//	public List<Book> findAll() {
////		return books;
//		return bRepo.findAll();
//	}
	
	// retrieve book by id
	public Optional<Book> findById(Long id) {
//		return books.stream()
//				.filter(b -> id.equals(b.getId()))
//				.findFirst();
		return bRepo.findById(id);
	}
	
	// save a book
	// Override default Transaction
	// Propagation.REQUIRED, readOnly=false
	// Allows creating new transaction OR joining existing one
	@Transactional(rollbackFor=Exception.class) // reverse save for any exception
	public Book save(Book b) {
//		b.setId(idCounter.getAndIncrement());
//		return books.add(b);
		return bRepo.save(b);
	}
	
	// search book
	public List<Book> findBooks(String q) {
		if (q == null || q.isBlank())
			return bRepo.findAll();
		String query = q.trim().toLowerCase();
		
		// try convert to num
		// if conversion fails, treat as string and search string fields
		// else search num fields
		try {
			Long idOrYear = Long.parseLong(query);
//			return books.stream()
//				.filter(b ->
//					(b.getId() != null && b.getId().equals((long) qNum)) ||
//					(b.getYear() != 0 && b.getYear() == qNum)
//				).toList();
			return bRepo.findAllByIdOrPublicationYear(idOrYear, idOrYear);
		} catch (NumberFormatException e) {
//			return books.stream()
//				.filter(b ->
//					containsIgnoreCase(b.getAuthor(), query) ||
//					containsIgnoreCase(b.getTitle(), query) ||
//					containsIgnoreCase(b.getGenre(), query)
//				).toList();
			return bRepo.findAllByTitleOrAuthorOrGenre(query);
		}
	}
	
//	// helper to filter searches
//	private boolean containsIgnoreCase(String field, String query) {
//		return field != null && field.toLowerCase().contains(query);
//	}
	
 	// delete book by id
	// override default Transaction
	// delete modifies data, so transaction is required
	@Transactional()
	public void deleteById(Long id) {
		if (id == null || id <= 0) return;
//		return books.removeIf(b -> id.equals(b.getId()));
		bRepo.deleteById(id);
	}
	
}
