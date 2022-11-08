package com.digitalbooks.book.books;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.digitalbooks.book.entitys.ResponseEntity;

@FeignClient(name = "READER-SERVICE", url = "localhost:8092/reader")
public interface BookFegin {
	@PutMapping("/blockbookforuser")
	public ResponseEntity blockBookForUser(@RequestParam int bookId);
}
