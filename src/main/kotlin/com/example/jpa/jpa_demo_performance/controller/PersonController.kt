package com.example.jpa.jpa_demo_performance.controller

import com.example.jpa.jpa_demo_performance.model.Person
import com.example.jpa.jpa_demo_performance.repository.PersonRepo
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("person/")
class PersonController {

    @Autowired
    lateinit var personRepo: PersonRepo

    @GetMapping("{id}")
    fun get (@PathVariable id:Long): Person {

        val p = personRepo.getById(id)
        p.readingBook = null

        return p
    }

    @GetMapping("wap/{id}")
    fun getByJacksonWap(@PathVariable id:Long): MappingJacksonValue {

        val filter = SimpleFilterProvider()

        filter.addFilter("person", SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","lastName","email","readingBook"))
        filter.addFilter("book", SimpleBeanPropertyFilter.filterOutAllExcept("id","title","price"))
        filter.setFailOnUnknownId(false)

        val wrapper = MappingJacksonValue(personRepo.getById(id))
        wrapper.filters = filter

        return wrapper

    }

    @GetMapping("/wap-cus/{id}")
    fun getByCustomRepoJacksonWrap (@PathVariable id: Long){

    }
}
