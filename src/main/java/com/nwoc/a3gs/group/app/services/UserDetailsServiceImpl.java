package com.nwoc.a3gs.group.app.services;

import com.nwoc.a3gs.group.app.message.request.SignUpForm;
import com.nwoc.a3gs.group.app.model.Role;
import com.nwoc.a3gs.group.app.model.RoleName;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.repository.RoleRepository;
import com.nwoc.a3gs.group.app.repository.UserRepository;

import javassist.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

		return UserPrinciple.build(user);
	}
	
	
	@Autowired
	RoleRepository roleRepository;

	@Transactional
	public User save(SignUpForm signUpRequest) {
       /* user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);*/
		User user = new User();
		BeanUtils.copyProperties(signUpRequest, user);
		 user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "pm":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			case "client":
				Role clientRole = roleRepository.findByName(RoleName.ROLE_CLIENT)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(clientRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		return userRepository.save(user);	
		
	}
	
	public User update(SignUpForm signUpRequest, Long id) throws NotFoundException {
		Optional<User> usrOpt =findOne(id);
		if(usrOpt.isPresent()){
			User usr = usrOpt.get();
			if(signUpRequest.getPassword().equals("") || signUpRequest.getPassword() == null ) {
				usr.setPassword(new BCryptPasswordEncoder().encode(usr.getPassword()));
			}
			else
			{
				usr.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
			}
			usr.setName(signUpRequest.getName());
			usr.setPhone(signUpRequest.getPhone());
			usr.setEmail(signUpRequest.getEmail());
			usr.setAge(signUpRequest.getAge());
			usr.setLocation(signUpRequest.getLocation());
			//usr.setUpdateddAt(user.getUpdateddAt());
			//usr.setRoles(signUpRequest.getRole());
			
			Set<String> strRoles = signUpRequest.getRole();
			Set<Role> roles = new HashSet<>();

			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(adminRole);

					break;
				case "pm":
					Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(pmRole);

					break;
				case "client":
					Role clientRole = roleRepository.findByName(RoleName.ROLE_CLIENT)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(clientRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(userRole);
				}
			});

			usr.setRoles(roles);

			return userRepository.saveAndFlush(usr);
		}else{
			throw new NotFoundException("User not found exception");
		}
		
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findOne(Long id) {
		return userRepository.findById(id);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public Page<User> findUserByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return userRepository.findAll(pageable);
	}
}