package ru.tinkoff.edu.java.scrapper.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    @PostMapping("/{id}")
    public ResponseEntity<String> registerChat(@PathVariable Long id) {
        if (id < 0 ) {
            throw new IllegalArgumentException("Id can`t be negative");
        }
        return ResponseEntity.ok("Чат зарегистрирован");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {
        if (id < 0) {
            throw new NoSuchElementException("No such element id");
        }
        return ResponseEntity.ok("Чат успешно удалён");
    }

}