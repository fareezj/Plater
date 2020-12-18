package com.example.plater.view.favourite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import com.example.plater.R
import com.example.plater.model.RecipeRoomModel
import com.squareup.picasso.Picasso

class FavouriteRecipeListAdapter (val context: Context):
    RecyclerView.Adapter<FavouriteRecipeListAdapter.FavRecipeViewHolder>() {

    private var bundle = Bundle()
    private lateinit var navController: NavController
    private var favouriteRecipes = emptyList<RecipeRoomModel>()

    class FavRecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindItems(recipeRoomModel: RecipeRoomModel){

            val ivFavRecipeImage: ImageView = itemView.findViewById(R.id.iv_fav_recipe_list)
            val tvFavRecipeName: TextView = itemView.findViewById(R.id.tv_fav_recipe_list)

            tvFavRecipeName.text = recipeRoomModel.recipeName
            Picasso.get()
                .load(recipeRoomModel.recipeImage)
                .fit()
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivFavRecipeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavRecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_recipe_list_row, parent, false)
        return FavRecipeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    override fun onBindViewHolder(holder: FavRecipeViewHolder, position: Int) {

        holder.bindItems(favouriteRecipes[position])

        holder.itemView.setOnClickListener {

            val fetchedFavData = favouriteRecipes[position]

            val favId = fetchedFavData.id
            val favName = fetchedFavData.recipeName
            val favImage = fetchedFavData.recipeImage
            val favDiet = fetchedFavData.dietLabel
            val favFat = fetchedFavData.fat_stat
            val favProtein = fetchedFavData.protein_stat
            val favCarbs = fetchedFavData.carbs_stat
            val favHealth: List<String> = fetchedFavData.foodHealthChecks
            val favIngredients: List<String> = fetchedFavData.recipe_ingredients

            bundle = bundleOf(
                "favID" to favId,
                "favName" to favName,
                "favImage" to favImage,
                "favHealth" to favHealth,
                "favDiet" to favDiet,
                "favFat" to favFat,
                "favCarbs" to favCarbs,
                "favProtein" to favProtein,
                "favIng" to favIngredients,
            )
            navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_favouriteRecipeFragment_to_favouriteRecipeDetailsFragment, bundle)
        }
    }

    internal fun setFavRecipes(recipes: List<RecipeRoomModel>) {
        this.favouriteRecipes = recipes
        notifyDataSetChanged()
    }
}