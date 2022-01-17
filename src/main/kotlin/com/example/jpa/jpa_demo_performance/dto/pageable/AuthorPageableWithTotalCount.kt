package com.example.jpa.jpa_demo_performance.dto.pageable

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("totalCount")
interface AuthorPageableWithTotalCount {
    var authorName:String?
    var authorEmail:String?
    var bookTitle:String?
    var bookPrice:Double?
    var totalCount:Long?
}