package com.example.jpa.jpa_demo_performance.controller

import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.model.Book
import com.example.jpa.jpa_demo_performance.repository.AuthorRepo
import com.example.jpa.jpa_demo_performance.repository.BookRepo
import com.example.jpa.jpa_demo_performance.service.AuthorService
import com.example.jpa.jpa_demo_performance.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book/")
class BookController {

    @Autowired
    lateinit var service: BookService
    @Autowired
    lateinit var authorService: AuthorService
    @Autowired
    lateinit var bookRepo: BookRepo
    @Autowired
    lateinit var authorRepo: AuthorRepo

    @PutMapping
    fun update(@RequestBody book:Book){
        service.update(book)
    }

    @GetMapping("{id}")
    fun get (@PathVariable id:Long): Book {
        return bookRepo.getById(id)
    }

    @GetMapping("skip-lock")
    fun testSkipLock (){
        service.fetchBooksViaTwoTransactions()
    }


    @PutMapping("author-marge/{id}")
    fun update (@PathVariable id:Long, @RequestBody auth: Author) {
        val a = authorRepo.getById(id)
        a.name = auth.name
        a.email = auth.email
        a.gender = auth.gender

        auth.books?.map { it.author = a }
        a.books = auth.books
        authorRepo.save(a)

    }

    @PutMapping("author-update/{id}")
    fun updateA (@PathVariable id:Long, @RequestBody auth: Author){
        val a = authorRepo.getById(id)
        a.name = auth.name
        a.email = auth.email
        a.gender = auth.gender

        auth.books?.map { it.author = a }
        a.books = auth.books

        authorService.updateAuthorViaUpdate(a)
    }
}
