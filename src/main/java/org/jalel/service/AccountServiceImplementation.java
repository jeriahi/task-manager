package org.jalel.service;

import org.jalel.dao.RoleRepository;
import org.jalel.dao.UserRepository;
import org.jalel.entities.AppRole;
import org.jalel.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImplementation implements AccountService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public AppUser saveUser(AppUser user) {
		String hashPW=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashPW);
		return userRepository.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {

		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user= userRepository.findByUsername(username);
		AppRole role=roleRepository.findByRoleName(roleName);
		user.getRoles().add(role);
		
	}

	@Override
	public AppUser findUserByUsername(String username) {
 
		return userRepository.findByUsername(username);
	}

}
