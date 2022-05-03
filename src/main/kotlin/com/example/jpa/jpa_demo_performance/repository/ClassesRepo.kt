package com.example.jpa.jpa_demo_performance.repository

import com.example.jpa.jpa_demo_performance.model.Classes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection

@Repository
interface ClassesRepo : JpaRepository<Classes, Long>, JpaSpecificationExecutor<Classes>, JpaSpecificationExecutorWithProjection<Classes> {

}
