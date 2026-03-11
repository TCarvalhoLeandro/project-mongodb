package com.leandro.workshopmongo.resources;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}

//.strem() -> Transforma os dados da list em uma coleção compativel com expressões lambda
//.map() -> Pega cada Objeto obj da lista e transforma em um novo UserDto passando obj como argumento
//.collect() -> Transforma de volta em uma lista