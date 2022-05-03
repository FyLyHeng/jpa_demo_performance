package com.example.jpa.jpa_demo_performance.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Student (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long ? = null,

    var name : String?="default",

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = true, name = "classes_id", insertable = true, updatable = true)
    var classes: Classes? = null

    )