package com.example.plater.data

import com.example.plater.model.CategoryModel

object RecipeCategories {

    fun getCategories(): ArrayList<CategoryModel> {

        val categoriesList = ArrayList<CategoryModel>()

        val cat1 = CategoryModel(
                1,
                "Chicken",
                "https://images.unsplash.com/photo-1525351484163-7529414344d8?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=80"
        )
        categoriesList.add(cat1)

        val cat2 = CategoryModel(
                2,
                "Fish",
                "https://images.unsplash.com/photo-1563897539633-7374c276c212?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=943&q=80"
        )
        categoriesList.add(cat2)

        val cat3 = CategoryModel(
                3,
                "Squid",
                "https://images.unsplash.com/photo-1572862905000-c5b6244027a5?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"
        )
        categoriesList.add(cat3)

        val cat4 = CategoryModel(
                4,
                "Vegetable",
                "https://images.unsplash.com/photo-1485827329522-c625acce0067?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"
        )
        categoriesList.add(cat4)

        val cat5 = CategoryModel(
                5,
                "Western",
                "https://images.unsplash.com/photo-1598577789978-b87168a57b69?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"
        )
        categoriesList.add(cat5)

        val cat6 = CategoryModel(
                6,
                "Asian",
                "https://images.unsplash.com/photo-1586128743915-ec7788189715?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"
        )
        categoriesList.add(cat6)

        val cat7 = CategoryModel(
                7,
                "Italian",
                "https://images.unsplash.com/photo-1604917877934-07d8d248d396?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80"
        )
        categoriesList.add(cat7)

        val cat8 = CategoryModel(
                8,
                "Korean",
                "https://images.unsplash.com/photo-1600289031464-74d374b64991?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=969&q=80"
        )
        categoriesList.add(cat8)

        val cat9 = CategoryModel(
                9,
                "Indian",
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?" +
                        "xlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"
        )
        categoriesList.add(cat9)

        val cat10 = CategoryModel(
                10,
                "Arabic",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?" +
                        "ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80"
        )
        categoriesList.add(cat10)

        return categoriesList
    }
}