package org.learn.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface BookRepository extends JpaRepository<Book, Integer> {
    void deleteByName(String bookName);
}

//	public static List<Book> BOOKS = new ArrayList<>(5);
//	public static Map<Integer, Book> BOOKMAP = new HashMap<>(5);
//
//	static {
//		BOOKS.add(new Book(1001, "Death: An Inside Story"));
//		BOOKS.add(new Book(1002, "The power of your Subconscious Mind"));
//		BOOKS.add(new Book(1003, "Ikigai"));
//		BOOKS.add(new Book(1004, "The things we cannot say"));
//		BOOKS.add(new Book(1005, "Think like a Monk"));
//
//		for(Book book : BOOKS) {
//			BOOKMAP.put(book.getId(), book);
//		}
//
//	}
	
	

