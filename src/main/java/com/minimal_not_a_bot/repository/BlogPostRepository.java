package com.minimal_not_a_bot.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.minimal_not_a_bot.model.BlogPost;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost, String> {
    Optional<BlogPost> findByPostHashCode(String postHashCode);

}
