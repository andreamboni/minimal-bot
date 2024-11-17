package com.minimal_not_a_bot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minimal_not_a_bot.model.MinimalPost;

@Repository
public interface MinimalBotRepository extends JpaRepository<MinimalPost, Integer> {
    Optional<MinimalPost> findByPostHashCode(String postHashCode);
}
