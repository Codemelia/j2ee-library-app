package sg.edu.nus.bookdemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import sg.edu.nus.bookdemo.model.Book;
import sg.edu.nus.bookdemo.service.BookService;

@Controller // tells spring this class handles web requests
public class BookController {
	
	// logger
//	private static final Logger logger = LoggerFactory
//		.getLogger(BookController.class.getName());
	
	@Autowired
	private BookService bSvc;
	
	// 1. home page
	// GET /
	@GetMapping("/")
	public String home() {
		return "index";
	}

	// 2. book list
	// GET /books
	// include search results function
	@GetMapping("/books")
	public String listBooks(
			@RequestParam(required=false) String q,
			Model m) {
		List<Book> books = new ArrayList<>();
		
		if (q == null || q.isEmpty()) {
			books = bSvc.findAll(); // fetch data from svc
		} else {
			books = bSvc.searchBooks(q); // searches and filters books
		}
		
		m.addAttribute("books", books); // add list of books to model
		return "books"; // return view name
		
	}
	
	// 3. book detail
	// GET /books/{id}
	@GetMapping("/books/{id}")
	public String bookDetail(
		@PathVariable Long id, // grabs id from path
		Model m,
		HttpServletResponse r) {
		Optional<Book> book = bSvc.findById(id);
		if (book.isEmpty()) {
			r.setStatus(404);
			return "error"; // resolves to error page
		}
		
		// book is present from here
		m.addAttribute("book", book.get());
		return "book-detail";
	}
	
	// 4. show add book form
	// GET /books/add
	@GetMapping("/books/add")
	public String showAddBookForm(Model m) {
		m.addAttribute("book", new Book()); // add new book so thymeleaf can bind to form
		return "add-book";
	}
	
	// process add book submission
	// POST /books/add
	@PostMapping("/books/add")
	public String saveBook(@ModelAttribute Book b,
		RedirectAttributes re,
		Model m) {
		boolean success = bSvc.save(b);
		if (!success) {
			m.addAttribute("book", b);
			return "add-book";
		}
		
		re.addFlashAttribute("success",
			String.format("Book %s added successfully!", 
				b.getTitle()));
		return "redirect:/books";
	}
	
	// POST /books/{id}/delete
	@PostMapping("/books/{id}/delete")
	public String deleteBook(@PathVariable Long id,
		RedirectAttributes re) {
		boolean success = bSvc.deleteById(id);
		if (!success) re.addAttribute("fail", "Delete was unsuccessful.");
		else re.addAttribute("success", "Delete was successful!");
		
		return "redirect:/books";
	}
	
}
