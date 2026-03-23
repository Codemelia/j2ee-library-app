package sg.edu.nus.bookdemo.repository;

import org.springframework.stereotype.Repository;

import sg.edu.nus.bookdemo.model.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	// SELECT * FROM books WHERE genre = ?
	List<Book> findByGenre(String genre);
	
	// SELECT * FROM books WHERE LOWER(author)
	// LIKE LOWER(CONCAT('%', '?', '%'))
	List<Book> findByAuthorContainingIgnoreCase(String author);

	/*
		SELECT * FROM books
			WHERE id = ?
			OR publication_year = ?
	*/
	List<Book> findAllByIdOrPublicationYear(Long idOrYear1, Long idOrYear2);
	
	/* 
		SELECT * FROM books
			WHERE title = ?
			OR author = ?
			OR genre = ?
	*/
	List<Book> findAllByTitleOrAuthorOrGenre(String query);
	
}
