package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Result;
import com.nwoc.a3gs.group.app.dto.ResetPasswordDTO;
import com.nwoc.a3gs.group.app.dto.UserDTO;
import com.nwoc.a3gs.group.app.model.Role;
import com.nwoc.a3gs.group.app.model.RoleName;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.repository.RoleRepository;
import com.nwoc.a3gs.group.app.repository.UserRepository;

import javassist.NotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	BraintreeGateway gateWay;
	private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
	
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
	public User save(UserDTO userDTO) throws NotFoundException {
		userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User usr =new User();
        BeanUtils.copyProperties(userDTO, usr);
        if(userDTO.getRoles()!=null){
        	try{
        		Set<Role> roles= userDTO.getRoles().stream().map(x->roleRepository.findByName(RoleName.ROLE_USER).get()).collect(Collectors.toSet());
        		usr.setRoles(roles);
        	}catch (NoSuchElementException e) {
				throw new NotFoundException("Role not found");
			}
			
		}
        if(createCustomerInBrainTree(usr)){
        	LOGGER.info("brain tree Customer has been create for user {}",usr.getUsername());
        }else{
        	LOGGER.warn("Brain tree customer creation failed for user {}",usr.getUsername());
        	
        }
        
		return userRepository.save(usr);
	}
	
	public User update(UserDTO userDTO, Long id) throws NotFoundException {
		Optional<User> usrOpt =findOne(id);
		if(usrOpt.isPresent()){
			User usr = usrOpt.get();
			if((userDTO.getPassword() == "") || (userDTO.getPassword() == null )) {
				usr.setPassword(usr.getPassword());
			}
			else
			{
				usr.setPassword(encoder.encode(userDTO.getPassword()));
			}
			usr.setName(userDTO.getName());
			usr.setPhone(userDTO.getPhone());
			usr.setAge(userDTO.getAge());
			usr.setLocation(userDTO.getLocation());
			//usr.setUsername(userDTO.getUsername());
			if(userDTO.getRoles()!=null){
				try{
					Set<Role> roles= userDTO.getRoles().stream().map(x->roleRepository.findByName(x.getName()).get()).collect(Collectors.toSet());
					usr.setRoles(roles);
	        	}catch (NoSuchElementException e) {
					throw new NotFoundException("Role not found");
				}	
			}
			if((userDTO.getEmail()).equals(usr.getEmail()) )
			{
				usr.setEmail(userDTO.getEmail());	
			}
			else
			{
				throw new NotFoundException("Email Cannot modify....");
			}
			if((userDTO.getUsername()).equals(usr.getUsername()) )
			{
				usr.setUsername(userDTO.getUsername());	
			}
			else
			{
				throw new NotFoundException("UserName Cannot modify....");
			}
			
			return userRepository.saveAndFlush(usr);
		}else{
			throw new NotFoundException("User not found exception");
		}
		
	}
	
    public User reset(ResetPasswordDTO resetPasswordDTO) throws NotFoundException {
		
		Optional<User> userOpt= userRepository.findByUsername(resetPasswordDTO.getUserName());
		if(!userOpt.isPresent()){
			throw new NotFoundException("User not found");
		}
		User usr = userOpt.get();
		String pass = resetPasswordDTO.getOldpassWord();
			if(encoder.matches(pass, usr.getPassword())) {
				usr.setPassword(encoder.encode(resetPasswordDTO.getNewPassword()));
				usr= userRepository.saveAndFlush(usr);
				return usr;
			}else{
				throw new NotFoundException("Old PassWord is not correct");
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
	
	public Boolean createCustomerInBrainTree(User user){
		try {
			CustomerRequest request = new CustomerRequest()
					  .firstName(user.getName())
					  .email(user.getEmail())
					  .phone(user.getPhone());
					Result<Customer> result = gateWay.customer().create(request);

					if(result.isSuccess()){
						user.setBtCusId(result.getTarget().getId());
						return true;
					}
		} catch (Exception e) {
			LOGGER.error("Customer creation in brain tree failed",e);
		}
		
				return false;
	}
}