package com.example.restfulWebService.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.example.restfulWebService.daos.Post;
import com.example.restfulWebService.daos.User;
import com.example.restfulWebService.exceptions.UserNotFoundException;
import com.example.restfulWebService.repositories.PostRepository;
import com.example.restfulWebService.repositories.UserRepository;

@RestController
public class JpaUserResource {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private PostRepository postrepo;

	@RequestMapping(method = RequestMethod.GET, path = "/jpausers")
	public List<User> retrieveAllUsers() {
		return userrepo.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/jpausers/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userrepo.findById(id);
		EntityModel<User> resource = EntityModel.of(user.get());
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		if (!user.isPresent()) {
			throw new UserNotFoundException("id " + id);
		} else {
			return resource;
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/jpausers")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User saveduser = userrepo.save(user);
		UriComponents location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(saveduser.getId());
		return ResponseEntity.created(location.toUri()).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/jpausers/{id}")
	public List<User> deleteUser(@PathVariable int id) {
            userrepo.deleteById(id);
			return userrepo.findAll();
	}

	@GetMapping(path = "/jpausers/{id}/posts")
	public List<Post> getAllPostsForUser(@PathVariable int id) {
		Optional<User> user = userrepo.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id " + id);
		}else {
			return user.get().getPost();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/jpausers/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userrepo.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id " + id);
		}else {
			User usertemp = user.get();
			post.setUser(usertemp);
			postrepo.save(post);
			UriComponents location = ServletUriComponentsBuilder.fromCurrentRequest().build();
			return ResponseEntity.created(location.toUri()).build();
		}
	}
}
