package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.model.Book
import com.example.jpa.jpa_demo_performance.model.BookStatus
import org.hibernate.LockOptions
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.LockModeType
import javax.persistence.QueryHint


@Repository
interface BookRepo : JpaRepository<Book, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [QueryHint(name = "javax.persistence.lock.timeout", value = "" + LockOptions.SKIP_LOCKED)])
    fun findTop3ByStatus(status: BookStatus?, sort: Sort?): MutableList<Book>?

    @Transactional(readOnly = true)
    fun findByIdIn(ids:List<Long>):MutableList<Book>

    /**
     * if those book have get from DB before invoke this delete fun :
     *
     * -> after delete. it delete only from DB, mean data still avriable in Persistence Context
     * :: use those two config from clear data from Persistence Context
     */
    @Transactional
    @Modifying
    @Query("delete from Book b where b.author = ?1")
    fun deleteInBatchByAuthor (auth:Author)
}
