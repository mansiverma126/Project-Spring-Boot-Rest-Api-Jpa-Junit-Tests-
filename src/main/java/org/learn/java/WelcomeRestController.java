package org.learn.java;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class WelcomeRestController {

	@GetMapping(value="/welcome", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> welcome(){
		String welcome = "hello from Rest Controller";
		return new ResponseEntity<String>(welcome, HttpStatus.OK);
	}
}
