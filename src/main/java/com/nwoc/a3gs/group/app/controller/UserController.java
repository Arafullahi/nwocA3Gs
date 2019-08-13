package com.nwoc.a3gs.group.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jfilter.filter.FieldFilterSetting;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.services.UserDetailsServiceImpl;

import javassist.NotFoundException;



@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserDetailsServiceImpl userService;
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		try {
			user = userService.save(user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		Optional<User> user = userService.findOne(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(user.get());
	}

	@PutMapping(value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
		User userUpdate = null;
		try {
			userUpdate = userService.update(user, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(userUpdate);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
		Optional<User> user = userService.findOne(id);
		if (!user.isPresent()) {
			// return ResponseEntity.notFound().build();
			return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
		}

		userService.delete(user.get());
		return ResponseEntity.ok().body(user.get().getName() + "  Successfully Deleted");
	}

	@GetMapping("/userslist")
	public ResponseEntity<Page<User>> listUserByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<User> userPages = userService.findUserByPages(page, size);
			return ResponseEntity.ok(userPages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
