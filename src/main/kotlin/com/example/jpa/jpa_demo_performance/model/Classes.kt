package com.example.jpa.jpa_demo_performance.model

import javax.persistence.*

@Entity
data class Classes (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null,
    var classNum : String?= "12A-1",




    @OneToMany( cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "classes_id", referencedColumnName = "id")
    var student : MutableList<Student> ? = null

    ){
    override fun toString(): String {
        return "sth"
    }
}