package com.example.plater.view.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plater.R
import com.example.plater.viewModel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_favourite_recipe.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.*

class FavouriteRecipeListFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setupToolbar()

        // Init Recycler View
        val recyclerView = rv_fav_recipe_list
        val adapter = FavouriteRecipeListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Init viewHolder
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        // Observe Changes
        recipeViewModel.getAllFavouriteRecipes.observe(requireActivity(), Observer { recipes ->
            recipes?.let { adapter.setFavRecipes(it) }
        })

    }

    private fun setupToolbar() {
        tv_toolbar_title.text = "Favourite Recipes"
        iv_toolbar_back_button.setOnClickListener {
            navController.navigateUp()
        }
    }


}