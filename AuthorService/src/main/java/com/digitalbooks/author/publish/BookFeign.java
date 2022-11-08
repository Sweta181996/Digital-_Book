package com.digitalbooks.author.publish;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.digitalbooks.author.entitys.BookDetails;
import com.digitalbooks.author.entitys.ResponseEntity;

@FeignClient(name = "BOOK-SERVICE", url = "localhost:8090/book")
public interface BookFeign {

	@PostMapping("/publishbook")
	public ResponseEntity publishBook(@RequestBody BookDetails bookDetails);

	@GetMapping("/getallbooksforauthor")
	public List<BookDetails> getAllBooksForAuthor(@RequestParam String emailId);

	@PutMapping("/editorblockbook")
	public ResponseEntity editOrBlockBook(@RequestBody BookDetails bookDetails);

	@GetMapping("/getallbooksforauthor")
	public List<BookDetails> getAllBooksForAuthor(@RequestParam int authorProfileId);
}
