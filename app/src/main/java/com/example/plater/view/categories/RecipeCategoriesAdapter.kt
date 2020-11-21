package com.example.plater.view.categories

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
import com.example.plater.model.CategoryModel
import com.squareup.picasso.Picasso

class RecipeCategoriesAdapter(private val data: ArrayList<CategoryModel>, val context: Context) :
    RecyclerView.Adapter<RecipeCategoriesAdapter.ViewHolder>() {

    private var bundle = Bundle()
    private lateinit var navController: NavController


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(categoryModel: CategoryModel){
            val ivCategory = itemView.findViewById(R.id.iv_categories) as ImageView
            val tvCategory = itemView.findViewById(R.id.tv_category) as TextView

            tvCategory.text = categoryModel.categoryName
            Picasso.get()
                .load(categoryModel.categoryImage)
                .fit()
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivCategory)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.categories_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].let { holder.bindItems(it) }

        holder.itemView.setOnClickListener {
            val model = data.get(position)
            val fetchedName: String = model.categoryName

            bundle = bundleOf("cuisineType" to fetchedName)
            navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_recipeCategoriesFragment_to_recipeListFragment, bundle)

        }

    }

}