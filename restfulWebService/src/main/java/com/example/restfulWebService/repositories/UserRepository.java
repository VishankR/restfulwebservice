package com.example.restfulWebService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restfulWebService.daos.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
