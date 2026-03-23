package sg.edu.nus.bookdemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.bookdemo.model.AuditEntry;
import sg.edu.nus.bookdemo.model.Book;
import sg.edu.nus.bookdemo.repository.AuditRepository;
import sg.edu.nus.bookdemo.repository.BookRepository;

@Service
public class AuditService {
	
	@Autowired
	private AuditRepository aRepo;
	
	@Autowired
	private BookRepository bRepo;
	
	// REQUIRES_NEW: always runs in its own independent transaction
	// even if caller's transaction rolls back, audit entry is saved
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void logAction(String action, Long bookId) {
		Optional<Book> optBook = bRepo.findById(bookId);
		if (optBook.isEmpty()) return; // exit if null
		AuditEntry entry = new AuditEntry(action, optBook.get());
		aRepo.save(entry);
	}

}
