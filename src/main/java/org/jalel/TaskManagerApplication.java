
package org.jalel;

import java.util.stream.Stream;

import org.jalel.dao.TaskRepository;
import org.jalel.entities.AppRole;
import org.jalel.entities.AppUser;
import org.jalel.entities.Task;
import org.jalel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Jalel Eddine
 *
 */

@SpringBootApplication
public class TaskManagerApplication implements CommandLineRunner{

	@Autowired
	private AccountService accountService;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);	
	}
	

	@Override
	public void run(String... args) throws Exception {
		
		accountService.saveUser(new AppUser(null, "admin", "123", null));
		accountService.saveUser(new AppUser(null, "user", "123", null));
		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.saveRole(new AppRole(null, "USER"));
		
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		accountService.addRoleToUser("user", "USER");
		
		Stream.of("T1","T2","T3").forEach(t->{
			taskRepository.save(new Task(t));
		});
		
		System.out.println("-------------------------------------------") ;
		taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskname());
		});
		
	}
}












