package com.example.jpa.jpa_demo_performance.controller

import com.example.jpa.jpa_demo_performance.dto.AuthorDto
import com.example.jpa.jpa_demo_performance.dto.AuthorPageable
import com.example.jpa.jpa_demo_performance.dto.pageable.AuthorPageableWithTotalCount
import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.model.Book
import com.example.jpa.jpa_demo_performance.repository.AuthorRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate

@RestController
@RequestMapping("/author")
class AuthorController {

    @Autowired
    lateinit var authorRepo: AuthorRepo


    @GetMapping("/a")
    fun getA (): MutableList<Author> {
        return authorRepo.findByFetchJoin(10.0)
    }

    @GetMapping("/b")
    fun getB (): MutableList<Author> {
        return authorRepo.findByLeftJoin(10.0)
    }



    @GetMapping("/book-a")
    fun getBookA (): MutableList<Book> {
        return authorRepo.fetchBooksAuthorsInnerJoinBad()
    }

    @GetMapping("/book-b")
    fun getBookB (): MutableList<Book> {
        return authorRepo.fetchBooksAuthorsInnerJoinGood()
    }

    @GetMapping("/book-c")
    fun getBookC (): MutableList<Book> {
        return authorRepo.fetchBooksAuthorsJoinFetch()
    }


    @GetMapping("/left-a")
    fun getLeftA (): MutableList<Book> {
        return authorRepo.fetchBooks()
    }

    @GetMapping("/left-b")
    fun getLeftB (): MutableList<Book> {
        return authorRepo.fetchBookLJF()
    }

    @GetMapping("/page")
    fun getPageableOfDTO (@RequestParam page:Int, @RequestParam size:Int, @RequestParam sortBy:String): Page<AuthorPageable> {
        val pageable : Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,sortBy))
        return authorRepo.fetchPageOfDto(pageable)
    }


    @GetMapping("/page-totalCount")
    fun getPageableOfDTOTotalCount (@RequestParam page:Int, @RequestParam size:Int, @RequestParam sortBy:String): Page<AuthorPageableWithTotalCount> {
        val pageable : Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,sortBy))
        val content = authorRepo.fetchPageOfDtoTotalCount(pageable)
        val totalCount = content.first().totalCount?:0

        return PageImpl(content, pageable, totalCount)
    }


    @GetMapping("/slice")
    fun getSliceOfDTO (@RequestParam page:Int, @RequestParam size:Int, @RequestParam sortBy:String): Slice<AuthorPageable> {
        val pageable : Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,sortBy))
        return authorRepo.fetchSliceOfDto(pageable)
    }


    @GetMapping("/criteria")
    fun findAllCriteria (@RequestParam allParams : MutableMap<String, String>): Page<AuthorPageable> {

        val authorName = allParams["author"]
        val page = allParams["page"]?.toInt() ?: 0
        val size : Int = allParams["size"]?.toInt() ?:10

        val rs = authorRepo.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()

            authorName?.let {
                val cusId = cb.equal(root.get<String>("name"), authorName)
                predicates.add(cusId)
            }
            cb.and(*predicates.toTypedArray())
        }, AuthorPageable::class.java,PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return rs
    }


    @GetMapping("criteria-basic")
    fun findAllCriteriaBasic (@RequestParam allParams : MutableMap<String, String>): Page<Author> {
        val authorName = allParams["author"]
        val page = allParams["page"]?.toInt() ?: 0
        val size : Int = allParams["size"]?.toInt() ?:10

        val rs = authorRepo.findAll({ root, cq, cb ->
            val predicates = ArrayList<Predicate>()

            val book : Join<Objects, Objects> = root.join("books",JoinType.LEFT)
            cq.multiselect(root,book)

            authorName?.let {
                val cusId = cb.equal(root.get<String>("name"), authorName)
                predicates.add(cusId)
            }
            cb.and(*predicates.toTypedArray())
        },PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return rs
    }


    @GetMapping("/dynamic")
    fun dynamicSelect (): MutableList<AuthorPageable>? {


        val fieldSelect = "a.name as authorName, a.email as authorEmail"
        return authorRepo.select(fieldSelect)
    }





}
