package com.nwoc.a3gs.group.app.services;

import com.nwoc.a3gs.group.app.model.Role;
import com.nwoc.a3gs.group.app.model.RoleName;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.repository.RoleRepository;
import com.nwoc.a3gs.group.app.repository.UserRepository;

import javassist.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
	public User save(User user) throws NotFoundException {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        
        if(user.getRoles()!=null){
        	try{
        		Set<Role> roles= user.getRoles().stream().map(x->roleRepository.findByName(RoleName.ROLE_USER).get()).collect(Collectors.toSet());
    			user.setRoles(roles);
        	}catch (NoSuchElementException e) {
				throw new NotFoundException("Role not found");
			}
			
		}
		return userRepository.save(user);
	}
	
	public User update(User user, Long id) throws NotFoundException {
		Optional<User> usrOpt =findOne(id);
		if(usrOpt.isPresent()){
			User usr = usrOpt.get();
			if(user.getPassword().equals("") || user.getPassword() == null ) {
				usr.setPassword(new BCryptPasswordEncoder().encode(usr.getPassword()));
			}
			else
			{
				usr.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			}
			usr.setName(user.getName());
			usr.setPhone(user.getPhone());
			usr.setEmail(user.getEmail());
			usr.setAge(user.getAge());
			usr.setLocation(user.getLocation());
			if(user.getRoles()!=null){
				try{
					Set<Role> roles= user.getRoles().stream().map(x->roleRepository.findByName(x.getName()).get()).collect(Collectors.toSet());
					usr.setRoles(roles);
	        	}catch (NoSuchElementException e) {
					throw new NotFoundException("Role not found");
				}
				
			}
			
			
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