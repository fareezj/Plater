package com.example.plater.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_recipe")
data class RecipeRoomModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "recipe_name")
    val recipeName: String,

    @ColumnInfo(name = "diet_label")
    val dietLabel: String,

    @ColumnInfo(name = "food_health_checks")
    val foodHealthChecks: List<String>,

    @ColumnInfo(name = "recipe_ingredients")
    val recipe_ingredients: String,

    @ColumnInfo(name = "fat_stat")
    val fat_stat: String,

    @ColumnInfo(name = "protein_stat")
    val protein_stat: String,

    @ColumnInfo(name = "carbs_stat")
    val carbs_stat: String,
)