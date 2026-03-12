package com.leandro.workshopmongo.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandro.workshopmongo.domain.User;
import com.leandro.workshopmongo.dto.UserDTO;
import com.leandro.workshopmongo.exception.ObjectNotFoundException;
import com.leandro.workshopmongo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findByID(String id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}
	
	public User insert(User obj) {
		return userRepository.insert(obj);
	}
	
	public void delete(String id) {
		findByID(id);
		userRepository.deleteById(id);
	}
	
	public User update(User newUser) {
		User user = findByID(newUser.getId());
		updateData(user, newUser);
		return userRepository.save(user);
	}
	
	public void updateData(User user, User newUser) {
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
	}
	
	public User fromDTO(UserDTO dto) {
		return new User(dto.getId(), dto.getName(), dto.getEmail());
	}
	
	
	
}

