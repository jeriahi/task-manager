package org.jalel.service;

import org.jalel.entities.AppRole;
import org.jalel.entities.AppUser;

public interface AccountService {

	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username,String roleName);
	public AppUser findUserByUsername(String username);
	
}
