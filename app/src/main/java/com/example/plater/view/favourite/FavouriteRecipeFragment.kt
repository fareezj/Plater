package com.example.plater.view.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plater.R
import com.example.plater.viewModel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_favourite_recipe.*

class FavouriteRecipeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init Recycler View
        val recyclerView = rv_fav_recipe_list
        val adapter = FavouriteRecipeAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Init viewHolder
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        // Observe Changes
        recipeViewModel.getAllFavouriteRecipes.observe(requireActivity(), Observer { recipes ->
            recipes?.let { adapter.setFavRecipes(it) }
        })

    }


}