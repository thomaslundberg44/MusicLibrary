package com.thomas.lundberg.entities;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserList {
private Collection<User> users;
	
	public Collection<User> getUserCollection() {
		return this.users;
	}
	
	public void setUserCollection(Collection<User> users) {
		this.users = users;
	}
}
