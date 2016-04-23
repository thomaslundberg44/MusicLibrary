package com.thomas.lundberg.services.ejb;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.thomas.lundberg.dao.UserDAO;
import com.thomas.lundberg.entities.User;
import com.thomas.lundberg.services.UserService;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserServiceEJB implements UserService {
	
	@Inject private UserDAO userDAO;

	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}

	public User getUser(String name) {
		return userDAO.getUser(name);
	}

	public User getUser(int userId) {
		return userDAO.getUser(userId);
	}

	public Collection<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	public String verifyUser(String uname, String pass) {
		return userDAO.verifyUser(uname, pass);
	}

}
