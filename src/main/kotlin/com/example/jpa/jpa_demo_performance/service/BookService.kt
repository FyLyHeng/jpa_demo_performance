package com.example.jpa.jpa_demo_performance.service

import com.example.jpa.jpa_demo_performance.model.Book
import com.example.jpa.jpa_demo_performance.model.BookStatus
import com.example.jpa.jpa_demo_performance.repository.BookRepo
import org.aspectj.lang.annotation.Before
import org.hibernate.Session
import org.jetbrains.annotations.TestOnly
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class BookService {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    //@Autowired
    //lateinit var template: TransactionTemplate

    @Autowired
    lateinit var trans: PlatformTransactionManager

    @Autowired
    lateinit var bookRepository: BookRepo


    fun update(book: Book) {
        val session: Session = entityManager.unwrap(Session::class.java)
        session.update(book)
    }


    fun fetchBooksViaTwoTransactions() {
/*        template.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
        template.execute {

            fun doInTransactionWithoutResult(status: TransactionStatus) {
                val books = bookRepository.findTop3ByStatus(BookStatus.PENDING, Sort.by(Sort.Direction.ASC, "id"))

                template.execute {
                    fun doInTransactionWithoutResult(status: TransactionStatus) {
                        val books = bookRepository.findTop3ByStatus(BookStatus.PENDING, Sort.by(Sort.Direction.ASC, "id"))
                        println("Second transaction: $books")
                    }
                }
                println("First transaction: $books")
            }
        }*/


        val template = TransactionTemplate(trans)
        template.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
        template.execute {
            fun doInTransactionWithoutResult(status: TransactionStatus) {
                val books = bookRepository.findTop3ByStatus(BookStatus.PENDING, Sort.by(Sort.Direction.ASC, "id"))

                template.execute {
                    fun doInTransactionWithoutResult(status: TransactionStatus) {
                        val books = bookRepository.findTop3ByStatus(BookStatus.PENDING, Sort.by(Sort.Direction.ASC, "id"))
                        println("Second transaction: $books")
                    }
                }
                println("First transaction: $books")
            }
        }
        println("Done! bro")
    }
}
