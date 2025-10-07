package com.jorgeromo.androidClassMp1.secondpartial.home.model


data class Event(
    val _id: Map<String, String>,
    val name: String,
    val address: String,
    val created_by: String,
    val category: String,
    val image_url: String
)