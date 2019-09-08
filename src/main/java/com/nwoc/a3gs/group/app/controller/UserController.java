package com.nwoc.a3gs.group.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfilter.filter.FieldFilterSetting;
import com.nwoc.a3gs.group.app.dto.PasswordResetTokenDTO;
import com.nwoc.a3gs.group.app.dto.ResetPasswordDTO;
import com.nwoc.a3gs.group.app.dto.UserDTO;
import com.nwoc.a3gs.group.app.message.response.ResponseMessage;
import com.nwoc.a3gs.group.app.model.PasswordResetToken;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.services.UserDetailsServiceImpl;

import javassist.NotFoundException;



@RestController
@RequestMapping(value = "/api/users",produces=MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	UserDetailsServiceImpl userService;
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
		try {
			if(userService.save(userDTO)) {		
				return ResponseEntity.ok("User created successfully.");	
			}
              else {
            	  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Registration Failed.");
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	//@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	//@FieldFilterSetting(className = Role.class, fields = {"id"})
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	//@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	//@FieldFilterSetting(className = Role.class, fields = {"id"})
	@GetMapping(value = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		Optional<User> user = userService.findOne(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(user.get());
	}

	//@FieldFilterSetting(className = User.class, fields = {"id", "password"})
	//@FieldFilterSetting(className = Role.class, fields = {"id"})
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
	
	@PatchMapping(value="/resetpassword",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> PasswordReset(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO){
			try {
				 userService.reset(resetPasswordDTO);
				return new ResponseEntity<>(new ResponseMessage("Password Reset Successfully Completed!"), HttpStatus.OK);
				}
			catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				System.out.println(e.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
	}

	@DeleteMapping(value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
		Optional<User> user = userService.findOne(id);
		if (!user.isPresent()) {
			// return ResponseEntity.notFound().build();
			return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
		}

		userService.delete(user.get());
		return ResponseEntity.ok().body(user.get().getName() + "  Successfully Deleted");
	}

	@GetMapping(value="/userslist",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<User>> listUserByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<User> userPages = userService.findUserByPages(page, size);
			return ResponseEntity.ok(userPages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PostMapping(value="/forgotpassword",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ForgotPassword(@Valid @RequestBody PasswordResetTokenDTO passwordResetTokenDTO){
			try {
				  User user = userService.findUserByEmail(passwordResetTokenDTO.getEmail());
				   if (user == null) {
						return ((BodyBuilder) ResponseEntity.notFound()).body("We didn't find an account for that e-mail address.");
					}
				   String token = UUID.randomUUID().toString();
				  if(userService.createPasswordResetTokenForUser(user, token) != null) {
					  if(userService.sendForgotMail(token, user)) {
						  return ResponseEntity.ok("PassWord Reset Link Successfully Send Your Inbox Please Check.......");	
					  }
					  else
					  {
						  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unexpexted Error happen when sending password reset mail link please generate onece more.....");
					  }
				  }
				    
				return new ResponseEntity<>(new ResponseMessage("Password Reset Link Successfully Sended!"), HttpStatus.OK);
				}
			catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				System.out.println(e.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unexpected Error during Reset password link");
			}
	}
	
	@GetMapping(value = "/resetpass/reset",produces=MediaType.APPLICATION_JSON_VALUE)
	public String getForgotPassLink(@RequestParam(value = "token") String token, RedirectAttributes redirectAttributes, HttpServletResponse resp) throws IOException {
		if(token != null) {
			try {
				
				 PasswordResetToken passwordResetToken = userService.findUserByToken(token);
				 if (passwordResetToken == null) {
						//return ((BodyBuilder) ResponseEntity.notFound()).body("We didn't find an account for that token.");
					  resp.sendError(HttpServletResponse.SC_NOT_FOUND, "We didn't find an account for that token. "); // explicitely put error message in request
					return null;
				 }
				 else if(passwordResetToken.isExpired())
				 {
					 userService.deleteById(passwordResetToken.getId());
					 resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Token has expired, please request a new password reset."); // explicitely put error message in request
						return null;
				 }
				 
				 return "redirect:/resetpass/changepass?token=" + token + "&user=" + passwordResetToken.getUser().getUsername() + "&id=" + passwordResetToken.getId() ;
				 
			}
			catch(Exception e) {
				LOGGER.error(e.getMessage(), e);
				System.out.println(e.getMessage());
				//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unexpected Error during Reset password generating");		
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected Error during Reset password generating"); // explicitely put error message in request
				return null;
			}
		}
		return null;
		
	}
	
	@PatchMapping(value="/resetpass/changepass",produces=MediaType.APPLICATION_JSON_VALUE)
	public String ResetPassword(@RequestParam(value = "token") String token, @RequestParam(value = "user") String user, @RequestParam(value = "id") Long id, @Valid @RequestBody ResetPasswordDTO resetPasswordDTO, RedirectAttributes redirectAttributes, HttpServletResponse resp) throws IOException{
		try {
			 userService.resetPass(user, resetPasswordDTO);
			 userService.deleteById(id);
			//return new ResponseEntity<>(new ResponseMessage("Password Reset Successfully Completed!"), HttpStatus.OK);
			 //resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Password Reset Successfully Completed!"); // explicitely put error message in request
			return "redirect:/api/auth/signin";
			}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unexpected Error during Reset password generating"); // explicitely put error message in request
			return null;
		}
		
	}
	
}
