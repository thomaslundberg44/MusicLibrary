package com.thomas.lundberg.dao;

import java.util.Collection;

import javax.ejb.Local;

import com.thomas.lundberg.entities.User;

@Local
public interface UserDAO {
	public boolean addUser(User user);
	public User getUser(String name);
	public User getUser(int userId);
	public Collection<User> getAllUsers();
	public String verifyUser(String uname, String pwd);
}
