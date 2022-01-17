package com.example.jpa.jpa_demo_performance.model

import java.io.Serializable
import javax.persistence.*

@Entity
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var firstName: String,
        var lastName: String,
        var email: String,
        var gender: String,
        var country: String
) : Serializable