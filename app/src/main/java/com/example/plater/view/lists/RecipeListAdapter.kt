package com.example.plater.view.lists

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
import com.example.plater.R
import com.example.plater.model.RecipeApiModel
import com.squareup.picasso.Picasso

class RecipeListAdapter (private val dataList: ArrayList<RecipeApiModel.RecipeDetails>, val context: Context) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    private var bundle = Bundle()
    private lateinit var navController: NavController

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(recipeApiModel: RecipeApiModel.RecipeDetails){
            val ivRecipeList = itemView.findViewById(R.id.iv_recipe_list) as ImageView
            val tvRecipeList = itemView.findViewById(R.id.tv_recipe_list) as TextView

            tvRecipeList.text = recipeApiModel.label
            Picasso.get()
                .load(recipeApiModel.image)
                .fit()
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivRecipeList)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        dataList[position].let { holder.bindItems(it) }

        holder.itemView.setOnClickListener {
            val model = dataList.get(position)
            val fetchedName: String = model.label

            bundle = bundleOf("recipeName" to fetchedName)
            navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_recipeListFragment_to_recipeDetailsFragment, bundle)

        }
    }


}