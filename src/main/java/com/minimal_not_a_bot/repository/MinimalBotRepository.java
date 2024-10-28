package com.minimal_not_a_bot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minimal_not_a_bot.model.MinimalBot;

@Repository
public interface MinimalBotRepository extends JpaRepository<MinimalBot, Integer> {
    Optional<MinimalBot> findByPostHashCode(String postHashCode);
}
