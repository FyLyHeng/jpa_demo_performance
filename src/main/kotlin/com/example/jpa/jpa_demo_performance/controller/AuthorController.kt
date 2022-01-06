package com.example.jpa.jpa_demo_performance.controller

import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.repository.AuthorRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/author")
class AuthorController {

    @Autowired
    lateinit var authorRepo: AuthorRepo


    @GetMapping("/a")
    fun getA (): MutableList<Author> {
        return authorRepo.findByFetchJoin(50.0)
    }

    @GetMapping("/b")
    fun getB (): MutableList<Author> {
        return authorRepo.findByLeftJoin(50.0)
    }


}