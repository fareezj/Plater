package com.example.plater.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.plater.db.RecipeDatabase
import com.example.plater.db.RecipeRepository
import com.example.plater.model.RecipeApiModel
import com.example.plater.model.RecipeRoomModel
import com.example.plater.network.ApiServices
import com.example.plater.util.Constants
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application): AndroidViewModel(application) {

    private var recipeApi: ApiServices = ApiServices.getServices()
    private val repository: RecipeRepository

    val getAllFavouriteRecipes: LiveData<List<RecipeRoomModel>>

    init {
        val recipeDAO = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDAO)
        getAllFavouriteRecipes = repository.allFavouriteRecipe
    }

    fun insertFavRecipe(recipe: RecipeRoomModel) = viewModelScope.launch (Dispatchers.IO) {
        repository.insertFavRecipe(recipe)
    }

    fun updateFavRecipe(recipe: RecipeRoomModel) = viewModelScope.launch (Dispatchers.IO) {
        repository.updateFavRecipe(recipe)
    }

    fun updateFavTitle(title: String, id: Int) = viewModelScope.launch (Dispatchers.IO) {
        repository.updateFavTitle(title, id)
    }

    fun deleteAllFavRecipe() = viewModelScope.launch (Dispatchers.IO) {
        repository.deleteAllFavRecipe()
    }

    fun deleteFavRecipe(recipeId: Int) = viewModelScope.launch (Dispatchers.IO) {
        repository.deleteFavRecipe(recipeId)
    }

    fun requestGetRecipeFromApi(query: String): Observable<RecipeApiModel>{
        return recipeApi.getRecipes(query, Constants.APP_ID, Constants.APP_KEY)
    }
}