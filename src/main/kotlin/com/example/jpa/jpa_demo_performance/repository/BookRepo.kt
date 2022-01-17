package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface BookRepo : JpaRepository<Author, Long>, JpaSpecificationExecutor<Book> {


//    @Query("update Book b set b.isDelete = true where b.isDelete = false and b.id in(?1) ")
//    fun delete (ids:List<Book>):MutableList<Book>

}
