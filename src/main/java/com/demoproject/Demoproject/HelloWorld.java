package com.demoproject.Demoproject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import org.springframework.mail.SimpleMailMessage;


@RestController
@CrossOrigin(origins="*")
public class HelloWorld {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MailSenderService service;
	

	//************************************* Signup ***********************************************************
	@PostMapping(path="/signup")
	 public String Signup(@RequestBody User user) {
		
		 String firstname = user.getFirstName();
		 String lastname = user.getLastName();
		 String email = user.getEmail();
		 String password = user.getPassword();
		 
		 if(!(user.verifyPassword(password))) {
			return "Password is too short";
		 }
		 
		 if(!(user.patternMatches(email))) {
			 return "Email is not valide ";
		 }
		 
		 String sql = "INSERT INTO user (FirstName, LastName, Email, Password) VALUES (?,?,?,?)";
	        
	        int result = jdbcTemplate.update(sql,firstname,lastname,email,password);
	         
	        if (result > 0) {
	            System.out.println("A new row has been inserted.");
	        }
	      return "done";
	      }
	
	
	//************************************* Login ***********************************************************
	
	@PostMapping(path="/login")
	 public  List<User> Login(@RequestBody User user) {
		
		 String email = user.getEmail();
		 String password = user.getPassword();
		 
		 String sql = "SELECT * FROM user WHERE Email = ? AND Password = ? ";
	        
		 	List<User> users = jdbcTemplate.query(sql,new UserMapper(),email,password);
		 	
		 	if(users == null) {
		 		return null;
		 	}else {
		 		return users;
		 	}
	      }
	
	//************************************* show ***********************************************************
	
		@GetMapping(path="/show")
		 public  List<User> Show() {
			
			 String sql = "SELECT * FROM user ";
		        
			 	List<User> users = jdbcTemplate.query(sql,new UserMapper());
			 	
			 	return users;
			 	
		      }
		
		//************************************* show ***********************************************************
		
			@GetMapping(path="/send")
			 public boolean Send() {
				
				 String sql = "SELECT * FROM user ";
				 String subject = "Login Alert !!";
				 String body = "Someone just logged on the site";
			        
				 	List<User> users = jdbcTemplate.query(sql,new UserMapper());
				 	
				 	 String[] emailIds = new String[users.size()];
				 	
				 	for(int i=0;i<users.size();i++) {
				 		emailIds[i]=users.get(i).getEmail();
				 	}
				 	service.sendMail(emailIds, body, subject);
				 	
				 	return true;
				 	
			      }
	  }
