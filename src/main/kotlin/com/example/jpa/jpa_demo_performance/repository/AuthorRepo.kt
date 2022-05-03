package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.dto.AuthorPageable
import com.example.jpa.jpa_demo_performance.dto.pageable.AuthorPageableWithTotalCount
import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.model.Book
import org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection
import javax.persistence.LockModeType
import javax.persistence.QueryHint

@Repository
interface AuthorRepo : JpaRepository<Author, Long>, JpaSpecificationExecutor<Author>, JpaSpecificationExecutorWithProjection<Author> {


    /**
     * (Join Fetch) V_S (Left Join) is performance effice 10x time.
     *
     * (Join Fetch) is faster 10x times
     */
    @Query("select a from Author a join fetch a.books b where b.price>?1")
    fun findByFetchJoin(price: Double) : MutableList<Author>

    @Query("select a from Author a left join a.books b where b.price>?1")
    fun findByLeftJoin(price: Double) : MutableList<Author>




    /**
     * Fetch all from vai Child (many to one)
     * INNER JOIN BAD = slowest
     * INNER JOIN GOOD = fast
     * INNER JOIN FETCH = fastest
     *
     */
    // INNER JOIN BAD
    @Query(value = "SELECT b FROM Book b INNER JOIN b.author a")
    fun fetchBooksAuthorsInnerJoinBad(): MutableList<Book>

    // INNER JOIN GOOD
    @Query(value = "SELECT b, a FROM Book b INNER JOIN b.author a")
    fun fetchBooksAuthorsInnerJoinGood(): MutableList<Book>

    // JOIN FETCH
    @Query(value = "SELECT b FROM Book b JOIN FETCH b.author a")
    fun fetchBooksAuthorsJoinFetch(): MutableList<Book>



    /**
     * FetchAll Left
     * This tow style perform the same time excurate
     */
    @Query(value = "SELECT b, a FROM Book b LEFT JOIN b.author a")
    fun fetchBooks(): MutableList<Book>

    @Query(value = "SELECT b FROM Book b LEFT JOIN FETCH b.author a")
    fun fetchBookLJF(): MutableList<Book>



    /**
     * Fetch All DTO + Pageable + Criteria
     *
     * Two style with the same performance
     * Recommend Style JPQL (easy to modify , faster that Native Query)
     */


    // this query perform two select: 1 (for get result) 2 (for count page, totalItem)
    @Transactional(readOnly = true)
    @Query("select a.name as authorName, a.email as authorEmail, b.title as bookTitle, b.price as bookPrice from Author a left join a.books b ")
    fun fetchPageOfDto (pageable: Pageable) : Page<AuthorPageable>


    // custom select totalCount (get result in one select): not recommend bcus it use Native query
    @Transactional(readOnly = true)
    @Query("select a.name as authorName, a.email as authorEmail, b.title as bookTitle, b.price as bookPrice, COUNT(a.id) OVER() as totalCount from author a left join book b ON a.id = b.author_id", nativeQuery = true)
    fun fetchPageOfDtoTotalCount (pageable: Pageable) : MutableList<AuthorPageableWithTotalCount>


    @Transactional(readOnly = true)
    @Query("select a.name as authorName, a.email as authorEmail, b.title as bookTitle, b.price as bookPrice from Author a left join a.books b ")
    fun fetchSliceOfDto (pageable: Pageable) : Slice<AuthorPageable>


    @Query("select a.name as authorName from Author a")
    fun select (fieldName: String): MutableList<AuthorPageable>?


    /**
     * default findAll(spe)
     */
//    @QueryHints(value = [QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false")])
//    override fun findAll(spec: Specification<Author>?, pageable: Pageable): Page<Author>



    /**
     * Custom inject Dependency
     */
//    @Query("select a.name as authorName, a.email as authorEmail, b.title as bookTitle, b.price as bookPrice from Author a left join a.books b ")
//    @Query("select a from Author a join fetch a.books b")
    @QueryHints(value = [QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false")])
    override fun <R : Any?> findAll(p0: Specification<Author>?, p1: Class<R>?, p2: Pageable?): Page<R>





    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Author a where a.id =?1")
    fun deleteVaiBatch (id:Long)
}
