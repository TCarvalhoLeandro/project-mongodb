package com.leandro.workshopmongo.resources;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leandro.workshopmongo.domain.Post;
import com.leandro.workshopmongo.domain.User;
import com.leandro.workshopmongo.dto.UserDTO;
import com.leandro.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		
		List<User> list = userService.findAll();// recebe uma lista de User que vem la do banco
		List<UserDTO> listDTO = list.stream().map(obj -> new UserDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value="/{id}")
	public  ResponseEntity<UserDTO> findByID(@PathVariable String id){
		User obj = userService.findByID(id);
		UserDTO userDTO = new UserDTO(obj);
		return ResponseEntity.ok().body(userDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO dto){ // Retorna um objeto vazio (Void) e recebe como argumento um UserDTO 
		User obj = userService.fromDTO(dto);// Converte um UserDTO em User
		obj = userService.insert(obj);// Inseri o User no banco
		
		// retorna a resposta com o cabeçalho do novo recurso criado
		// pega o enreço do no objeto que inseri
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public  ResponseEntity<Void> delete(@PathVariable String id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO dto, @PathVariable String id){
		User obj = userService.fromDTO(dto);
		obj.setId(id);
		obj = userService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping(value="/{id}/posts")
	public  ResponseEntity<List<Post>> findPost(@PathVariable String id){
		User obj = userService.findByID(id);
		return ResponseEntity.ok().body(obj.getPosts());
	}
	
}

//.strem() -> Transforma os dados da list em uma coleção compativel com expressões lambda
//.map() -> Pega cada Objeto obj da lista e transforma em um novo UserDto passando obj como argumento
//.collect() -> Transforma de volta em uma lista