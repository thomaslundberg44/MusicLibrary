package com.thomas.lundberg.dao.jpa;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.thomas.lundberg.dao.UserDAO;
import com.thomas.lundberg.entities.User;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JPAUserDAO implements UserDAO {
	
	@PersistenceContext
	private EntityManager em;

	public boolean addUser(User user) {
		Query query = em.createQuery("From User u where u.name := name");
		query.setParameter("name", user.getName());
		if(query.getResultList().size() > 0) {
			em.persist(user);
			return true;
		}
		else
			return false;
	}

	public User getUser(String name) {
		Query query = em.createQuery("From User u where u.name := name");
		query.setParameter("name", name);
		User user = (User) query.getSingleResult();
		return user;
	}

	public User getUser(int userId) {
		User user = em.find(User.class, userId);
		return user;
	}

	public Collection<User> getAllUsers() {
		Query query = em.createQuery("From User");
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public String verifyUser(String uname, String pwd) {
		Query query = em.createQuery("From User");
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
		for(User user : users) {
			if(user.getUsername().equals(uname)) {
				if(user.getPassword().equals(pwd))
					return "Success";
				else 
					return "Password not correct";
			}
		}
		return "Username not found";
	}

}
