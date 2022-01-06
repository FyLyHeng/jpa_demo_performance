package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AuthorRepo : JpaRepository<Author, Long>{


    /**
     * (Join Fetch) V_S (Left Join) is performance effice 10x time.
     *
     * (Join Fetch) is faster 10x times
     */
    @Query("select a from Author a join fetch a.books b where b.price>?1")
    fun findByFetchJoin (price:Double) : MutableList<Author>

    @Query("select a from Author a left join a.books b where b.price>?1")
    fun findByLeftJoin (price:Double) : MutableList<Author>




}