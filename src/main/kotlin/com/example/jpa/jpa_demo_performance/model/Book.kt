package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.*
import java.io.Serializable
import javax.persistence.*
import javax.persistence.Entity

@Entity
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@JsonFilter("book")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Version
        @JsonIgnore
        var version: Int? = 0,

        var title: String,
        var price: Double? = 0.5,

        var isDelete: Boolean? = null,

        //if the value pass not content with enum will return bed request
        @Enumerated(EnumType.STRING)
        var status: BookStatus? = null,

        //we can config the large field to load lazy(only when we need)
        @Lob
        @Basic(fetch = FetchType.LAZY)
        @LazyGroup("binaryGroup")
        var coverImage: ByteArray? = null,


        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "author_id", insertable = true, updatable = false,nullable = true)
        var author: Author? = null,


//        @JsonBackReference
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "person_id", referencedColumnName = "id")
//        @OptimisticLock(excluded = true)
//        var person: Person? = null,
) : Serializable
