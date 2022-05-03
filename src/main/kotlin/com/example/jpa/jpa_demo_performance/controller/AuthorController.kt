package com.example.jpa.jpa_demo_performance.controller

import com.example.jpa.jpa_demo_performance.dto.AuthorDto
import com.example.jpa.jpa_demo_performance.dto.AuthorPageable
import com.example.jpa.jpa_demo_performance.dto.pageable.AuthorPageableWithTotalCount
import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.model.Book
import com.example.jpa.jpa_demo_performance.repository.AuthorRepo
import com.example.jpa.jpa_demo_performance.repository.BookRepo
import org.hibernate.hql.internal.ast.tree.OrderByClause
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Tuple
import javax.persistence.criteria.*
import javax.transaction.Transactional


@RestController
@RequestMapping("/author")
@Transactional
class AuthorController {

    @Autowired
    lateinit var authorRepo: AuthorRepo

    @Autowired
    lateinit var bookRepo: BookRepo

    @PersistenceContext
    lateinit var entityManager : EntityManager


    @GetMapping("{id}")
    fun get(@PathVariable id: Long): Author {
        val b = authorRepo.getById(id).books
        return Author(id=1,name = "", email = "", gender = "",books = b )
    }

    @GetMapping("/a")
    fun getA(): MutableList<Author> {
        return authorRepo.findByFetchJoin(10.0)
    }

    @GetMapping("/b")
    fun getB(): MutableList<Author> {
        return authorRepo.findByLeftJoin(10.0)
    }


    @GetMapping("/book-a")
    fun getBookA(): MutableList<Book> {
        return authorRepo.fetchBooksAuthorsInnerJoinBad()
    }

    @GetMapping("/book-b")
    fun getBookB(): MutableList<Book> {
        return authorRepo.fetchBooksAuthorsInnerJoinGood()
    }

    @GetMapping("/book-c")
    fun getBookC(): MutableList<Book> {
        return authorRepo.fetchBooksAuthorsJoinFetch()
    }


    @GetMapping("/left-a")
    fun getLeftA(): MutableList<Book> {
        return authorRepo.fetchBooks()
    }

    @GetMapping("/left-b")
    fun getLeftB(): MutableList<Book> {
        return authorRepo.fetchBookLJF()
    }

    @GetMapping("/page")
    fun getPageableOfDTO(@RequestParam page: Int, @RequestParam size: Int, @RequestParam sortBy: String): Page<AuthorPageable> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy))
        return authorRepo.fetchPageOfDto(pageable)
    }


    @GetMapping("/page-totalCount")
    fun getPageableOfDTOTotalCount(@RequestParam page: Int, @RequestParam size: Int, @RequestParam sortBy: String): Page<AuthorPageableWithTotalCount> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy))
        val content = authorRepo.fetchPageOfDtoTotalCount(pageable)
        val totalCount = content.first().totalCount ?: 0

        return PageImpl(content, pageable, totalCount)
    }


    @GetMapping("/slice")
    fun getSliceOfDTO(@RequestParam page: Int, @RequestParam size: Int, @RequestParam sortBy: String): Slice<AuthorPageable> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy))
        return authorRepo.fetchSliceOfDto(pageable)
    }


    @GetMapping("/criteria")
    fun findAllCriteria(@RequestParam allParams: MutableMap<String, String>): Page<AuthorDto> {

        val authorName = allParams["author"]
        val page = allParams["page"]?.toInt() ?: 0
        val size: Int = allParams["size"]?.toInt() ?: 10

        val rs = authorRepo.findAll({ root, cq, cb ->
            val predicates = ArrayList<Predicate>()

            val book: Join<Author, Book> = root.join("books", JoinType.LEFT)
            //val book = root.fetch<Objects, Objects>("books", JoinType.LEFT) as Join<*, *>

            cq.multiselect(root, book.get<String>("title"), book.get<Double>("price"))
            //cq.multiselect(root.get<String>("name"))


            //root.fetch<Any, Any>("books", JoinType.LEFT)
            //q.where(cb.equal(r.get<Any>("productItem")["status"], "received"))
            //cq.where(cb.equal(root.get<Book>("books").get<String>("status"),BookStatus.PENDING))


            //val book = root.fetch<Any, Any>("books", JoinType.LEFT) as Join<*, *>
            //cq.multiselect(root, book)

            cq.distinct(true)
            authorName?.let {
                val cusId = cb.equal(root.get<String>("name"), authorName)
                predicates.add(cusId)
            }
            cb.and(*predicates.toTypedArray())
        }, AuthorDto::class.java, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return rs
    }


    @GetMapping("criteria-basic")
    fun findAllCriteriaBasic(@RequestParam allParams: MutableMap<String, String>): Int {
        val authorName = allParams["author"]
        val page = allParams["page"]?.toInt() ?: 0
        val size: Int = allParams["size"]?.toInt() ?: 10

        val rs = authorRepo.findAll({ root, cq, cb ->
            val predicates = ArrayList<Predicate>()

            val book: Join<Author, Book> = root.join("books", JoinType.LEFT)
            cq.multiselect(root.get<String>("name"))
            //cq.multiselect(root, book.get<String>("title"))
            cq.distinct(true)


            authorName?.let {
                val cusId = cb.equal(root.get<String>("name"), authorName)
                predicates.add(cusId)
            }
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return rs.content.size
    }

    @GetMapping("criteria-jpa")
    fun findAllCriteriaJPA(@RequestParam allParams: MutableMap<String, String>): Page<Author> {
        val authorName = allParams["author"]
        val page = allParams["page"]?.toInt() ?: 0
        val size: Int = allParams["size"]?.toInt() ?: 10

        val rs = authorRepo.findAll({ root, cq, cb ->
            val predicates = ArrayList<Predicate>()

            //val book = root.fetch<Objects, Objects>("books", JoinType.LEFT) as Join<*, *>
            //cq.multiselect(root, book)

            //val productItemFetch: Fetch<Author, Book> = root.fetch("books", JoinType.LEFT)
            //cq.where(cb.equal(root.get<Book>("books").get<String>("status"), "PENDING"));
            //cq.multiselect(root,productItemFetch)


            //val book = root.fetch<Objects, Objects>("books") as Join<*, *>

            //val productItemFetch: Fetch<Author, Book> = root.fetch("books", JoinType.LEFT)
            //val book: Join<Author, Book> = productItemFetch as Join<Author,Book>
            //cq.where(cb.like(book.get("status"), "PENDING"))

            //root.fetch<Author, Book>("books", JoinType.LEFT)
            root.join<Author,Book>("books", JoinType.LEFT)
            cq.distinct(true)
            cb.conjunction()

            authorName?.let {
                val cusId = cb.equal(root.get<String>("name"), authorName)
                predicates.add(cusId)
            }
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return rs
    }


    @GetMapping("criteria-em")
    fun findAllCriteriaEntityManager(@RequestParam allParams: MutableMap<String, String>): MutableList<Author> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder

        val cq: CriteriaQuery<Tuple> = cb.createTupleQuery()
        val root: Root<Author> = cq.from(Author::class.java)
        val book = root.join<Author,Book>("books", JoinType.LEFT)

        cq.multiselect(root.get<Long>("id"), root.get<String>("name"), book.get<String>("title"))
        //cq.groupBy(root.get<Long>("id"))
        val tupleResult: List<Tuple> = entityManager.createQuery(cq).resultList

        val rsList = mutableListOf<Author>()
        tupleResult.forEach {
            //println("${it.get(0)}  ${it.get(1)}  ${it.get(2)}")
            rsList.add(
                Author(
                    id = it.get(0) as Long,
                    name = it.get(1) as String,
                    email = "", gender = ""
                )
            )
        }
        return rsList

    }


//=====================================================================================
    /**
     *
     *
     */

    @GetMapping("/exist")
    fun dynamicSelect(@RequestParam param: Map<String, String>): Map<String, Example<Book>> {

        val book = Book(
                title = param["title"].toString(),
                isDelete = param["isDelete"].toBoolean()
        )


        //matchingAny (or)
        //matchingAll (and)

        val ex = Example.of(book, ExampleMatcher.matchingAll()
                .withIgnorePaths("price", "id").withIgnoreCase()
        )


        return mapOf("isExist ${bookRepo.exists(ex)}" to ex)
    }


    @Transactional
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val au = authorRepo.getById(id)

        au.books?.forEach {
            println("me ${it.title}")
        }

        bookRepo.deleteAllInBatch(au.books!!)
        authorRepo.deleteById(au.id!!)
    }

}
