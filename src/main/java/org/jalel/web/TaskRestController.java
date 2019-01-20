package org.jalel.web;

import java.util.List;

import org.jalel.dao.TaskRepository;
import org.jalel.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskRestController {

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("tasks")
	public List<Task> listTasks(){
		return taskRepository.findAll();
	}
	
	@PostMapping("tasks")
	public Task save(@RequestBody Task t) {
		return taskRepository.save(t);
	}
	
}
