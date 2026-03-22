package sg.edu.nus.bookdemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import sg.edu.nus.bookdemo.model.Book;
import sg.edu.nus.bookdemo.service.BookService;
import sg.edu.nus.bookdemo.validator.SpringYearValidator;

@Controller // tells spring this class handles web requests
public class BookController {
	
	// logger
//	private static final Logger logger = LoggerFactory
//		.getLogger(BookController.class.getName());
	
	// Binding custom Spring validator
	@InitBinder("book")// tells Spring to run this method before data binding to model attr named "book"
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new SpringYearValidator()); // register custom validator
	}
	
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
			Model m, HttpSession s) {
		List<Book> books = new ArrayList<>();
		
		if (s.getAttribute("genre") != null)
			books = bSvc.searchBooks((String) s.getAttribute("genre"));
		else if (q != null && !q.isEmpty()) {
			books = bSvc.searchBooks(q); // searches and filters books
		} else {
			books = bSvc.findAll(); 
		}
		
		m.addAttribute("books", books); // add list of books to model
		return "books"; // return view name
		
	}
	
	// 3. book detail
	@SuppressWarnings("unchecked")
	// GET /books/{id}
	@GetMapping("/books/{id}")
	public String bookDetail(
		@PathVariable Long id, // grabs id from path
		Model m,
		HttpServletResponse r,
		HttpSession s) {
		Optional<Book> optBook = bSvc.findById(id);
		if (optBook.isEmpty()) {
			r.setStatus(404);
			return "error"; // resolves to error page
		}
		
		// book is present from here
		Book book = optBook.get();
		m.addAttribute("book", book);
		
		List<Long> recentIds = new ArrayList<>();
		
		// retrieve recent views list
		if (s.getAttribute("recentIds") != null)
			recentIds = ((List<Long>) s.getAttribute("recentIds"));
		
		// add curr book to front of list
		if (recentIds.contains(id)) recentIds.remove(id); // ensure no dupes
		recentIds.add(0, id); // add curr book to the front
		
		// if list size > 5, remove ids after index 4
		if (recentIds.size() > 5)
			recentIds = new ArrayList<>(recentIds.subList(0, 5));
		
		// save updated list back into session
		s.setAttribute("recentIds", recentIds);
		
		
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
	public String saveBook(@Valid
		@ModelAttribute Book b,
		BindingResult br,
		RedirectAttributes re,
		Model m,
		HttpSession s) {
		
		// validation failed
		if (br.hasErrors()) {
//			m.addAttribute("book", b); // not needed, spring will map current user values
			return "add-book";
		}
		
		// retrieve count of saves in current session
		Integer saves = 0;
		if (s.getAttribute("saves") != null)
			saves = (Integer) s.getAttribute("saves");
		
		// validation passed
		// save failed
		boolean success = bSvc.save(b);
		if (!success) {
			m.addAttribute("book", b);
			return "add-book";
		}
		
		// save passed
		// update save count
		s.setAttribute("saves", ++saves);
		re.addFlashAttribute("success",
			String.format("""
				Book %s added successfully!
				You have added %d book(s) this session.
			""", 
			b.getTitle(), saves));
		return "redirect:/books";
	}
	
	// delete book by id
	// POST /books/{id}/delete
	@PostMapping("/books/{id}/delete")
	public String deleteBook(@PathVariable Long id,
		RedirectAttributes re) {
		boolean success = bSvc.deleteById(id);
		if (!success) re.addFlashAttribute("fail", "Delete was unsuccessful.");
		else re.addFlashAttribute("success", "Delete was successful!");
		
		return "redirect:/books";
	}
	
	// add default genre
	// POST /genre/add
	@PostMapping("/genre/add")
	public String addDefaultGenre(@RequestParam String genre,
		HttpSession s) {
		s.setAttribute("genre", genre);
		return "index";
	}
	
	// clear default genre
	// POST /genre/clear
	@PostMapping("/genre/clear")
	public String clearDefaultGenre(HttpSession s, 
		RedirectAttributes re) {
		if (s.getAttribute("genre") != null) {
			s.removeAttribute("genre");
			re.addFlashAttribute("success", "Default genre cleared!");
		}
		return "redirect:/";
	}
	
	// clear session data
	@PostMapping("/session/clear")
	public String clearSession(HttpSession s,
		RedirectAttributes re) {
		if (s != null) {
			s.invalidate(); // invalidate entire session
			re.addFlashAttribute("success", "Session cleared!");
		}
		return "redirect:/";
	}
	
}
