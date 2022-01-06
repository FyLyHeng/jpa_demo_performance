package com.example.jpa.jpa_demo_performance.model

import javax.persistence.*

@Entity
data class Author (
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       var id:Long?,
       var name:String,
       var email:String,
       var gender:String,

       @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
       var books : MutableList<Book>?= mutableListOf()
)