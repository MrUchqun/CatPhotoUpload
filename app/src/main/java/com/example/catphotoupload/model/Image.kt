package com.example.catphotoupload.model

data class Image(
    val breeds: ArrayList<Breed>? = null,
    val height: Int? = null,
    val id: String? = null,
    val url: String? = null,
    val width: Int? = null,
    val original_filename: String? = null,
    val pending: Int? = null,
    val approved: Int? = null,
    val sub_id: String? = null,
    val created_at: String? = null,
    val breed_ids: Any? = null
)