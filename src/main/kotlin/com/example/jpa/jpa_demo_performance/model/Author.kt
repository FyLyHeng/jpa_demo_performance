package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.*

import java.io.Serializable
import javax.persistence.*
import javax.persistence.CascadeType
import javax.persistence.Entity

@Entity
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
data class Author(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @JsonIgnore
        @Version
        var version:Int? = 0,

        var name: String,
        var email: String,
        var gender: String,


        @Where(clause = "is_delete = false")
        @JsonManagedReference
        @OneToMany(mappedBy = "author", cascade = [ CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.LAZY)
        @OptimisticLock(excluded = true)// not incres version when child entity have mordify
        var books: MutableList<Book>? = mutableListOf()
) : Serializable
