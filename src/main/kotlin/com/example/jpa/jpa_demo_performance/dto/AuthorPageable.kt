package com.example.jpa.jpa_demo_performance.dto

interface AuthorPageable {
    var authorName:String?
    var authorEmail:String?
    var bookTitle:String?
    var bookPrice:Double?
}