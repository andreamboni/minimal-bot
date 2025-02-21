package com.minimal_not_a_bot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.minimal_not_a_bot.model.BlogPages;

@Repository
public interface BlogPagesRepository extends MongoRepository<BlogPages, String>{
}
