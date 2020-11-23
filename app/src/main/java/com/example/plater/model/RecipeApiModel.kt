package com.example.plater.model

import com.google.gson.annotations.SerializedName

class RecipeApiModel {

    @SerializedName("q")
    var searchTitle: String = ""

    @SerializedName("hits")
    var hits: List<Recipe>? = null

    inner class Recipe {

        @SerializedName("recipe")
        var recipe: RecipeDetails? = null
    }

    class RecipeDetails {

        @SerializedName("label")
        var label: String = ""

        @SerializedName("image")
        var image: String = ""

        @SerializedName("ingredientLines")
        var ingredients: List<String>? = null

        @SerializedName("dietLabels")
        var dietLabel: List<String>? = null

        @SerializedName("healthLabels")
        var healthLabel: List<String>? = null

        @SerializedName("totalNutrients")
        var totalNutrients: Nutrients? = null

    }

    class Nutrients {

        @SerializedName("FAT")
        var fat: NutrientDetails? = null

        @SerializedName("PROCNT")
        var protein: NutrientDetails? = null

        @SerializedName("CHOCDF")
        var carbs: NutrientDetails? = null
    }

    class NutrientDetails {

        @SerializedName("label")
        var label: String? = null

        @SerializedName("quantity")
        var quantity: Float? = null

        @SerializedName("unit")
        var unit: String? = null
    }

}