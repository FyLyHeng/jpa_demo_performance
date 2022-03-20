package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.model.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepo : JpaRepository<Person,Long> {
}
