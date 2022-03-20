package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.Where
import java.io.Serializable
import javax.persistence.*

@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
/**
 * This one use by set the child to null
 * if the field is null Jackson will not serialize it.
 */


@JsonFilter("person")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var firstName: String?=null,
        var lastName: String?=null,
        var email: String?=null,
        var gender: String?=null,

        @Basic(fetch = FetchType.LAZY)
        var country: String?=null,

        @Where(clause = "is_delete = false")
        @JsonManagedReference
        @OneToMany(mappedBy = "person", cascade = [ CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.EAGER)
        var readingBook : MutableList<Book> ? = null,

) : Serializable
