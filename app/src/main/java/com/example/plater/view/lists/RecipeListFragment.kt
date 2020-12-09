package com.example.plater.view.lists

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plater.R
import com.example.plater.model.RecipeApiModel
import com.example.plater.viewModel.RecipeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.*

class RecipeListFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private val subscriptions = CompositeDisposable()
    private lateinit var navController: NavController
    private var recipeList = ArrayList<RecipeApiModel.RecipeDetails>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setupToolbar()


        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        val fetchedData: String? = arguments?.getString("cuisineType")

        requestRecipeFromApi(fetchedData.toString())

    }

    private fun requestRecipeFromApi(query: String) {
        val subscribe = viewModel.requestGetRecipeFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({

                pb_loading.visibility = View.VISIBLE
                val fetchedData: List<RecipeApiModel.Recipe>? = it.hits
                var data = fetchedData
                if(data != null){
                    for(i in data){
                        val extractedData: RecipeApiModel.RecipeDetails? = i.recipe
                        if (extractedData != null) {
                            recipeList.add(extractedData)
                        }
                    }
                }
                setupAdapter(recipeList)
                pb_loading.visibility = View.GONE

            }, { err ->
                var msg = err.localizedMessage
            })
        subscriptions.add(subscribe)
    }

    private fun setupAdapter(data: ArrayList<RecipeApiModel.RecipeDetails>) {

        val myAdapter = RecipeListAdapter(data, requireContext())
        rv_recipe_list.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_recipe_list.adapter = myAdapter
    }

    private fun setupToolbar() {
        tv_toolbar_title.text = "Recipe List"
        iv_toolbar_back_button.setOnClickListener {
            navController.navigateUp()
        }
    }
}