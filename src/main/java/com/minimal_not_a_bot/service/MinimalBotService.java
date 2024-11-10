package com.minimal_not_a_bot.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.MinimalPost;
import com.minimal_not_a_bot.repository.MinimalBotRepository;

@Service
public class MinimalBotService {

    @Autowired
    private MinimalBotRepository repository;

    public boolean postExist(String postHashCode) {
        Optional<MinimalPost> hashCode = repository.findByPostHashCode(postHashCode);

        if(hashCode.isPresent()) {
            System.out.println("Post já existe");
            return true;
        }

        repository.saveAndFlush(MinimalPost
                .builder()
                .postHashCode(postHashCode)
                .insertTs(LocalDateTime.now())
                .build());

        return false;
    }

}
