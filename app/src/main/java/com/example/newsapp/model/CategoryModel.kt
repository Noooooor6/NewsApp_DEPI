package com.example.newsapp.model

import com.example.newsapp.R

data class CategoryModel(
    val id: String,
    val imageId: Int,
    val backgroundColorId: Int,
    val title: String,
    val isLeftSide: Boolean
) {
    companion object {

        val categoryModels = listOf(
            CategoryModel(
                id = "sports", imageId = R.drawable.sports, backgroundColorId = R.color.red,
                title = "Sports", isLeftSide = true
            ),
            CategoryModel(
                id = "entertainment",
                imageId = R.drawable.politics,
                backgroundColorId = R.color.blue,
                title = "Entertainment",
                isLeftSide = false
            ),
            CategoryModel(
                id = "health", imageId = R.drawable.health, backgroundColorId = R.color.pink,
                title = "Health", isLeftSide = true
            ),
            CategoryModel(
                id = "business", imageId = R.drawable.bussines, backgroundColorId = R.color.brown,
                title = "Business", isLeftSide = false
            ),
            CategoryModel(
                id = "technology",
                imageId = R.drawable.environment,
                backgroundColorId = R.color.light_blue,
                title = "Technology",
                isLeftSide = true
            ),
            CategoryModel(
                id = "science", imageId = R.drawable.science, backgroundColorId = R.color.yellow,
                title = "Science", isLeftSide = false
            ),
        )
    }
}
