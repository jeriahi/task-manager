package org.jalel.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Task {

	@Id 
	@GeneratedValue
	private Long id;
	private String taskname;
	public Task(String taskname) {
		super();
		this.taskname = taskname;
	}
	
	
	
}
