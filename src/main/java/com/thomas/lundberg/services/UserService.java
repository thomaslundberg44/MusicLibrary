package com.thomas.lundberg.services;

import java.util.Collection;

import javax.ejb.Local;

import com.thomas.lundberg.entities.User;

@Local
public interface UserService {
	public String addUser(User user);
	public User getUser(String name);
	public User getUser(int userId);
	public Collection<User> getAllUsers();
	public String verifyUser(String uname, String pass);
	public String updateUser(User user);
}
