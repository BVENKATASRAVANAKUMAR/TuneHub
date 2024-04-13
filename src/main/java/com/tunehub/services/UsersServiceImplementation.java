package com.tunehub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tunehub.entities.Users;
import com.tunehub.repositories.UsersRepository;

@Service
public class UsersServiceImplementation 
							implements UsersService{
	@Autowired
	UsersRepository repo;
	@Override
	public String addUser(Users user) {
		repo.save(user);
		return "user added succesfully";
	}
	@Override
	public boolean emailExists(String email) {
		if(repo.findByEmail(email)==null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	@Override
	public boolean validateUser(String email, String password) {
		Users user = repo.findByEmail(email);
		
		if(user != null) {
			String db_pass = user.getPassword();
			 if (password != null && password.equals(db_pass)) {
		            return true;
		        }
			
		}
		return false;
		
	}
	@Override
	public String getRole(String email) {
		Users user = repo.findByEmail(email);
		return user.getRole();
	}
	@Override
	public Users getUser(String email) {
		return repo.findByEmail(email);
	}
	@Override
	public void updateUser(Users user) {
		repo.save(user);
	}
	@Override
	public boolean isUserExists(String email) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isValidUser(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}
}
