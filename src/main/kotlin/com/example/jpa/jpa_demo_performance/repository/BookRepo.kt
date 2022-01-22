package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.model.Book
import com.example.jpa.jpa_demo_performance.model.BookStatus
import org.hibernate.LockOptions
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType
import javax.persistence.QueryHint


@Repository
interface BookRepo : JpaRepository<Book, Long>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [QueryHint(name = "javax.persistence.lock.timeout", value = "" + LockOptions.SKIP_LOCKED)])
    fun findTop3ByStatus(status: BookStatus?, sort: Sort?): MutableList<Book>?
}
