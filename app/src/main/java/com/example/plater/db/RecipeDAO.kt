package com.example.plater.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.plater.model.RecipeRoomModel

@Dao
interface RecipeDAO {

    @Query("Select * FROM favourite_recipe")
    fun getAllFavouriteRecipes(): LiveData<List<RecipeRoomModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteRecipe(recipe: RecipeRoomModel)

    @Update
    suspend fun updateFavRecipe(recipe: RecipeRoomModel)

    @Query("DELETE FROM favourite_recipe")
    suspend fun deleteALLFavouriteRecipe()

    @Query("DELETE FROM favourite_recipe WHERE id = :recipeId")
    suspend fun deleteFavouriteRecipe(recipeId: Int);
}