package com.digitalbooks.author.publish;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.author.constants.ResponseConstants;
import com.digitalbooks.author.entitys.AuthorDetails;

import com.digitalbooks.author.entitys.BookDetails;
import com.digitalbooks.author.entitys.ResponseEntity;
import com.digitalbooks.author.exceptionhandler.AuthorExceptionHandler;
import com.digitalbooks.author.jwt.api.util.JwtUtil;
import com.digitalbooks.author.utils.PasswordEncDec;

@Service
public class AuthorService {

	@Autowired
	private AuthorDao dao;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BookFeign fegin;

	public ResponseEntity registerAuthor(AuthorDetails authorDetails) throws AuthorExceptionHandler {
		authorDetails.setPassword(PasswordEncDec.encryptingPassword(authorDetails.getPassword()));
		AuthorDetails details = null;
		try {
			if (authorDetails != null) {
				if (dao.findByEmailId(authorDetails.getEmailId()).size() > 0)
					return new ResponseEntity(ResponseConstants.SUCCESS, ResponseConstants.EMAILALREADYREGISTERED);
				details = dao.save(authorDetails);
			}
		} catch (Exception e) {
			throw new AuthorExceptionHandler("Exception occured while registration");
		}
		return details != null ? new ResponseEntity(ResponseConstants.SUCCESS, ResponseConstants.SUCCESSMESSAGE)
				: new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.FAILMESSAGE);
	}

	public ResponseEntity loginAuthor(String emailId, String password) throws AuthorExceptionHandler {
		try {
			List<AuthorDetails> authorDetails = dao.findByEmailId(emailId);
			if (authorDetails.size() > 0) {
				AuthorDetails author = authorDetails.get(0);
				return PasswordEncDec.bCrypter(password, author.getPassword())
						? new ResponseEntity(ResponseConstants.SUCCESS, ResponseConstants.LOGINSUCCESS,
								new AuthorDetails(author.getAuthorProfileId(), author.getAuthorName(),
										author.getEmailId(), jwtUtil.generateToken(author.getEmailId())))
						: new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.PASSWORDWRONG);
			}
		} catch (Exception e) {
			throw new AuthorExceptionHandler("Exception occured while login");
		}
		return new ResponseEntity(ResponseConstants.FAIL, ResponseConstants.EMAILWRONG);
	}

	public ResponseEntity publishBook(BookDetails bookDetails) throws AuthorExceptionHandler {
		ResponseEntity details = null;
		try {
			if (bookDetails != null)
				details = fegin.publishBook(bookDetails);
		} catch (Exception e) {
			throw new AuthorExceptionHandler("Exception occured while publishing a book");
		}
		return details;
	}

	public ResponseEntity editOrBlockBook(BookDetails bookDetails) throws AuthorExceptionHandler {
		try {
			return fegin.editOrBlockBook(bookDetails);
		} catch (Exception e) {
			throw new AuthorExceptionHandler("Exception occured while editing or updating the book", e);
		}
	}

	public List<BookDetails> getAllBooksForAuthor(int authorProfileId) throws AuthorExceptionHandler {
		try {
			return fegin.getAllBooksForAuthor(authorProfileId);
		} catch (Exception e) {
			throw new AuthorExceptionHandler("Exception occured while fetching  the books for author", e);
		}
	}

	public static String hello() {
		return "sweta";
	}
}


