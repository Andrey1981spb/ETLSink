package com.example.portal.controller;

import com.example.portal.model.Appeal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appeals")
public class AppealController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Appeal updateBook(@RequestBody final Appeal book) {
        return book;
    }

}
