package com.digitalbooks.author.publish;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.author.constants.ResponseConstants;
import com.digitalbooks.author.entitys.AuthorDetails;
import com.digitalbooks.author.entitys.BookDetails;
import com.digitalbooks.author.entitys.ResponseEntity;
import com.digitalbooks.author.exceptionhandler.AuthorExceptionHandler;

@CrossOrigin("*")
@RestController
@RequestMapping("/author")
public class AuthorController {
	Logger logger = LoggerFactory.getLogger(AuthorController.class);
	@Autowired
	private AuthorService service;

	@PostMapping("/register")
	public ResponseEntity registerAuthor(@RequestBody AuthorDetails authorDetails) throws AuthorExceptionHandler {
		ResponseEntity response = new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.FAILMESSAGE);
		try {
			response = service.registerAuthor(authorDetails);
		} catch (Exception e) {
			logger.info("failed to register", e);
		}
		return response;
	}

	@GetMapping("/login")
	public ResponseEntity loginAuthor(@RequestParam String emailId, @RequestParam String password)
			throws AuthorExceptionHandler {
		ResponseEntity response = new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.LOGINFAIL);
		try {
			response = service.loginAuthor(emailId, password);
		} catch (Exception e) {
			logger.info("failed to login", e);
		}
		return response;
	}

	@PostMapping("/publishbook")
	public ResponseEntity publishBook(@RequestBody BookDetails bookDetails) throws AuthorExceptionHandler {
		ResponseEntity response = new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.FAILMESSAGE);
		try {
			response = service.publishBook(bookDetails);
		} catch (Exception e) {
			logger.info("failed to publish the book", e);
		}
		return response;
	}

	@GetMapping("/getallbooksforauthor")
	public List<BookDetails> getAllBooksForAuthor(@RequestParam int authorProfileId) throws AuthorExceptionHandler {
		try {
			return service.getAllBooksForAuthor(authorProfileId);
		} catch (Exception e) {
			logger.info("failed to get  books", e);
		}
		return null;
	}

	@PutMapping("/editorblockbook")
	public ResponseEntity editOrBlockBook(@RequestBody BookDetails bookDetails) throws AuthorExceptionHandler {
		ResponseEntity response = new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.FAILMESSAGE);
		try {
			response = service.editOrBlockBook(bookDetails);
		} catch (Exception e) {
			logger.info("failed to block a book", e);
		}
		return response;
	}



}


