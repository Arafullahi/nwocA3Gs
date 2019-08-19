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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nwoc.a3gs.group.app.dto.ResetPasswordDTO;
import com.nwoc.a3gs.group.app.dto.UserDTO;
import com.nwoc.a3gs.group.app.message.response.ResponseMessage;
import com.jfilter.filter.FieldFilterSetting;
import com.nwoc.a3gs.group.app.model.Role;
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
	public @ResponseBody ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
		try {
			userService.save(userDTO);
			return ResponseEntity.ok("User created successfully.");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	@FieldFilterSetting(className = Role.class, fields = {"id"})
	@GetMapping
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	@FieldFilterSetting(className = Role.class, fields = {"id"})
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		Optional<User> user = userService.findOne(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(user.get());
	}

	@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	@FieldFilterSetting(className = Role.class, fields = {"id"})
	@PutMapping(value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDTO userDTO) {
		User userUpdate = null;
		try {
			userUpdate = userService.update(userDTO, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(userUpdate);
	}
	
	@PatchMapping("/resetpassword")
	public ResponseEntity<?> PasswordReset(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO){
			try {
				User resetpass = userService.reset(resetPasswordDTO);
				return new ResponseEntity<>(new ResponseMessage("Password Reset Successfully Completed!"), HttpStatus.OK);
				}
			catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				System.out.println(e.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
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
