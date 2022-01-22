package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OptimisticLockType
import org.hibernate.annotations.OptimisticLocking
import org.hibernate.annotations.SelectBeforeUpdate
import java.io.Serializable
import javax.persistence.*

@Entity
@DynamicUpdate
//@SelectBeforeUpdate(true)
@OptimisticLocking(type = OptimisticLockType.DIRTY)
data class Author(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @JsonIgnore
        var version:Int? = 0,

        var name: String,
        var email: String,
        var gender: String,


        @JsonManagedReference
        @OneToMany(mappedBy = "author", cascade = [ CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.LAZY)
        var books: MutableList<Book>? = mutableListOf()
) : Serializable
