package com.example.plater.view.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.plater.R
import com.example.plater.model.RecipeRoomModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourite_recipe_list_row.view.*

class FavouriteRecipeAdapter (val context: Context):
    RecyclerView.Adapter<FavouriteRecipeAdapter.FavRecipeViewHolder>() {

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
    }

    internal fun setFavRecipes(recipes: List<RecipeRoomModel>) {
        this.favouriteRecipes = recipes
        notifyDataSetChanged()
    }
}