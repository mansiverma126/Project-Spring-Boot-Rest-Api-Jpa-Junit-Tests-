package org.learn.java;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/rest")
public class BookRestController {
	
//	Map<Integer, Book> bookMap = BookRepository.BOOKMAP;

	@Autowired
	BookRepository bookRepository;

	@ApiOperation(value="Get all the book item, returns an array of book")
	@GetMapping(value="/books", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Book>> getAll(){
		return new ResponseEntity<Collection<Book>>(bookRepository.findAll(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Get one book by id, returns a book")
	@GetMapping(value="/books/{id}", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	//@RequestMapping(value="/books/{id}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Book> getOne(@PathVariable Integer id){
		Book book = bookRepository.findOne(id);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	
	@ApiOperation(value="Create a book, add book in rest body, returns void")
	@PostMapping(value="/books", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> createOrAdd(@RequestBody(required =true) Book book){
		bookRepository.save(book);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@ApiOperation(value="Update an existing book, add book for update in the request body, returns updated book")
	@PutMapping(value="/books", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@RequestBody(required =true) Book book){
		if(book.getId() != null) {
			book = bookRepository.save(book);
		}else {
			return createOrAdd(book);
		}
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	
	@ApiOperation(value="Delete a book by id, returns void")
	@DeleteMapping(value="/books/{id}", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	//@RequestMapping(value="/books/{id}", method = RequestMethod.DELETE, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		bookRepository.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
