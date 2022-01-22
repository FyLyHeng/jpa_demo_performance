package com.example.jpa.jpa_demo_performance.service

import com.example.jpa.jpa_demo_performance.model.Author
import com.example.jpa.jpa_demo_performance.repository.AuthorRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.hibernate.Session
import org.springframework.transaction.annotation.Transactional



@Service
class AuthorService {
    @Autowired
    lateinit var authorRepo: AuthorRepo

    @PersistenceContext
    lateinit var entityManager : EntityManager



    @Transactional
    fun updateAuthorViaUpdate(author: Author?) {
        val session: Session = entityManager.unwrap(Session::class.java)
        session.merge(author)
    }
}
