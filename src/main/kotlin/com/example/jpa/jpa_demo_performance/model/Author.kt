package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.io.Serializable
import javax.persistence.*

@Entity
data class Author(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var name: String,
        var email: String,
        var gender: String,


        @JsonManagedReference
        @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
        var books: MutableList<Book>? = mutableListOf(),
) : Serializable
