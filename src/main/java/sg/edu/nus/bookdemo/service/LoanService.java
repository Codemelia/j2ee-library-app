package sg.edu.nus.bookdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.bookdemo.repository.BookRepository;
import sg.edu.nus.bookdemo.repository.LoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository lRepo;
	
	@Autowired
	private BookRepository bRepo;
	
	@Autowired

}
