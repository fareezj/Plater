package com.example.plater.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plater.model.RecipeModel
import com.example.plater.network.ApiServices
import com.example.plater.util.Constants
import io.reactivex.Observable
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application): AndroidViewModel(application) {

    private var recipeApi: ApiServices = ApiServices.getServices()

    fun requestGetRecipeFromApi(query: String): Observable<RecipeModel>{
        return recipeApi.getRecipes(query, Constants.APP_ID, Constants.APP_KEY)
    }
}