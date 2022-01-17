package com.example.jpa.jpa_demo_performance.dto

interface AuthorDto {
    var name:String?
    var email:String?
    var books : MutableList<BookDto>?


    interface BookDto {
        var title:String?
        var price:Double?
    }
}

