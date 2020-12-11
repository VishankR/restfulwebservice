package com.example.restfulWebService.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.restfulWebService.daos.User;
import com.example.restfulWebService.exceptions.UserNotFoundException;
import com.example.restfulWebService.services.UserDaoService;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.http.ResponseEntity;

@RestController
public class UserResources {
	@Autowired
	UserDaoService service;

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public List<User> retrieveAllUsersHome() {
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findUser(id);
		EntityModel<User> resource = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		if (null == user) {
			throw new UserNotFoundException("id " + id);
		} else {
			return resource;
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User saveduser = service.createUser(user);
		UriComponents location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(saveduser.getId());
		return ResponseEntity.created(location.toUri()).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/users/{id}")
	public List<User> deleteUser(@PathVariable int id) {
		User removedUser = service.deleteById(id);
		if (null == removedUser) {
			throw new UserNotFoundException("id " + id);
		} else {
			return service.findAll();
		}
	}
}
