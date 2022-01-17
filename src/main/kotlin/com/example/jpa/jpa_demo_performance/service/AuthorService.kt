package com.example.jpa.jpa_demo_performance.service

import com.example.jpa.jpa_demo_performance.repository.AuthorRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorService {
    @Autowired
    lateinit var authorRepo: AuthorRepo

    fun findAllFullCriteria(){

    }
}