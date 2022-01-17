package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonBackReference
import java.io.Serializable
import javax.persistence.*

@Entity
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var title: String,
        var price: Double? = 0.5,

        val isDelete: Boolean ?= null,

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        var author: Author,
) : Serializable
