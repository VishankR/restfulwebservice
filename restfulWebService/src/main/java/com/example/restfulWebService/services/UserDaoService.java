package com.example.restfulWebService.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.restfulWebService.daos.User;

@Component
public class UserDaoService {

	private static int userCount = 3;
	private static List<User> users = new ArrayList<>();
	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User findUser(int id) {
		if(id > users.size() || id < 1) {
			return null;
		}
		return users.get(id - 1);
	}

	public User createUser(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	public User deleteById(int id) {
		User removedUser = users.remove(id-1);
		return removedUser;
	}
}
