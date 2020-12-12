package com.example.restfulWebService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restfulWebService.daos.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
