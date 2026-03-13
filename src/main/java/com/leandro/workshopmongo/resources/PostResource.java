package com.leandro.workshopmongo.resources;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.workshopmongo.domain.Post;
import com.leandro.workshopmongo.resources.util.URL;
import com.leandro.workshopmongo.services.PostService;


@RestController
@RequestMapping(value="/posts")
public class PostResource implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PostService postService;
	
	@GetMapping(value="/{id}")
	public  ResponseEntity<Post> findByID(@PathVariable String id){
		Post obj = postService.findByID(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
	@GetMapping(value="/titlesearch")
	public  ResponseEntity<List<Post>> findByTitle(@RequestParam(defaultValue = "") String text){
		text = URL.decodeParam(text);// Decodifica meu parametro
		List<Post> list = postService.findByTitle(text);
		return ResponseEntity.ok().body(list);

	}
	
	
	@RequestMapping(value="/fullsearch", method=RequestMethod.GET)
 	public ResponseEntity<List<Post>> fullSearch(
 			@RequestParam(value="text", defaultValue="") String text,
 			@RequestParam(value="minDate", defaultValue="") String minDate,
 			@RequestParam(value="maxDate", defaultValue="") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Post> list = postService.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}

}
