package org.jalel.web;


import org.jalel.entities.AppUser;
import org.jalel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm registerForm) {
		
		if(!registerForm.getPassword().equals(registerForm.getRepassword()))
			throw new RuntimeException("You must confirme your password");
		
		AppUser user=accountService.findUserByUsername(registerForm.getUsername());
		if(!(user==null)) throw new RuntimeException("This User alredy exist");
		AppUser appUser=new AppUser();
		appUser.setUsername(registerForm.getUsername());
		appUser.setPassword(registerForm.getPassword());
		accountService.saveUser(appUser);
		accountService.addRoleToUser(registerForm.getUsername(), "USER");
		return appUser;
		
	}
}
