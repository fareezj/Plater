package com.example.plater.db

import androidx.lifecycle.LiveData
import com.example.plater.model.RecipeRoomModel

class RecipeRepository (private val recipeDAO: RecipeDAO) {

    val allFavouriteRecipe: LiveData<List<RecipeRoomModel>> = recipeDAO.getAllFavouriteRecipes()

    suspend fun insertFavRecipe(recipe: RecipeRoomModel){
        recipeDAO.insertFavouriteRecipe(recipe)
    }

    suspend fun updateFavRecipe(recipe: RecipeRoomModel){
        recipeDAO.updateFavRecipe(recipe)
    }

    suspend fun deleteAllFavRecipe(){
        recipeDAO.deleteALLFavouriteRecipe()
    }

    suspend fun  deleteFavRecipe(recipeName: String) {
        recipeDAO.deleteFavouriteRecipe(recipeName)
    }


}