package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Book (
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       var id:Long?,
       var title:String,
       var price:Double?=0.5,

       @JsonIgnore
       @ManyToOne
       var author: Author?=null
)