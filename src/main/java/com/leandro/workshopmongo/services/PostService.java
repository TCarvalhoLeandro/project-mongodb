package com.leandro.workshopmongo.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandro.workshopmongo.domain.Post;
import com.leandro.workshopmongo.exception.ObjectNotFoundException;
import com.leandro.workshopmongo.repository.PostRepository;

@Service
public class PostService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PostRepository postRepository;
	
	public Post findByID(String id) {
		Optional<Post> obj = postRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Post não encontrado."));
	}
	
	public List<Post> findByTitle(String text) {
		return postRepository.findByTitleContainingIgnoreCase(text);
		
	}

}
