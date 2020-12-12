package com.example.restfulWebService.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All Details about User")
@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	@ApiModelProperty(notes="Name should have at least 2 Charcters")
	@Size(min=2, message="Name should have at least 2 Charcters")
	private String name;
	@ApiModelProperty(notes="Birthdate should be less than today")
	@Past
	private Date birthDate;
	@OneToMany(mappedBy="user")
	private List<Post> post;
	public User() {
	}
	public User(int id, String name, Date birthDate) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	public User(String name, Date birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public List<Post> getPost() {
		return post;
	}
	public void setPost(List<Post> post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
}
