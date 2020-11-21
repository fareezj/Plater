package com.example.plater.view.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plater.R
import com.example.plater.data.RecipeCategories
import kotlinx.android.synthetic.main.fragment_recipe_categories.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.*

class RecipeCategoriesFragment : Fragment() {

    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = RecipeCategories.getCategories()
        navController = Navigation.findNavController(view)

        setupToolbar()

        val myAdapter = RecipeCategoriesAdapter(data, requireContext())
        rv_categories.layoutManager = GridLayoutManager(
            requireContext(), 2, GridLayoutManager.VERTICAL, false)
        rv_categories.adapter = myAdapter

    }

    private fun setupToolbar() {
        tv_toolbar_title.text = "Recipe Categories"
        iv_toolbar_back_button.setOnClickListener {
            navController.navigateUp()
        }
    }

}