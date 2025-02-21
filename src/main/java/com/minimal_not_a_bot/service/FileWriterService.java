package com.minimal_not_a_bot.service;

import java.io.FileWriter;
import java.io.IOException;

import org.jvnet.hk2.annotations.Service;

@Service // TODO: precisa mesmo da anotação?
public class FileWriterService {

    public static void htmlFileWriter(String content, String fileName) {
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Writting complete");
    }

}
