package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OptimisticLockType
import org.hibernate.annotations.OptimisticLocking
import java.io.Serializable
import javax.persistence.*

@Entity
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Version
        @JsonIgnore
        var version:Int? = 0,

        var title: String,
        var price: Double? = 0.5,

        var isDelete: Boolean ?= null,

        //if the value pass not content with enum will return bed request
        @Enumerated(EnumType.STRING)
        var status : BookStatus? = null,

        @JsonBackReference
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id")
        var author: Author ? =null
) : Serializable
