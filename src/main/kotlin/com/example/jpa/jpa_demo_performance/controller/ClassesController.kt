package com.example.jpa.jpa_demo_performance.controller

import com.example.jpa.jpa_demo_performance.model.Classes
import com.example.jpa.jpa_demo_performance.repository.ClassesRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses

@RestController
@RequestMapping("/classes")
class ClassesController {

    @Autowired
    lateinit var classesRepo: ClassesRepo

    @PostMapping
    fun save (@RequestBody classes: Classes): Classes {



        classes.student.orEmpty()
        classes.student?.plus(classes.student!!)


        var rs = classesRepo.save(classes)

        return rs

    }

    @GetMapping("{id}")
    fun get (@PathVariable id:Long): Classes {
        var rs = classesRepo.getById(id)

        return rs
    }

    @PutMapping("{id}")
    fun update (@PathVariable id: Long, t:Classes){
        var data = classesRepo.findById(id).get()
        println(data)

        data = t
        data.student?.clear()
        data.student?.addAll(t.student!!)

        classesRepo.save(data)
    }

}