package com.example.plater.view.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.plater.R
import com.example.plater.model.RecipeApiModel
import com.example.plater.model.RecipeRoomModel
import com.example.plater.viewModel.RecipeViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.*


class RecipeDetailsFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private val subscriptions = CompositeDisposable()
    private val extractedData = ArrayList<RecipeApiModel.RecipeDetails>()
    private lateinit var navController: NavController

    private var room_int: Int = 2
    private var room_recipeName: String? = null
    private var room_recipeImage: String? = null
    private var room_dietLabel: List<String>? = null
    private var room_foodHealthChecks: List<String>? = null
    private var room_recipe_ingredients: List<String>? = null
    private var room_fat_stat: String? = null
    private var room_protein_stat: String? = null
    private var room_carbs_stat: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        tb_favourite.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24))

        val fetchedData: String? = arguments?.getString("recipeName")
        val refinedRecipeInput = fetchedData?.replace("\\s".toRegex(), "+")

        setupToolbar(fetchedData.toString())
        requestRecipeFromApi(refinedRecipeInput.toString())

    }

    private fun requestRecipeFromApi(query: String) {
        val subscribe = recipeViewModel.requestGetRecipeFromApi(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({

                    pb_loading_detail.visibility = View.VISIBLE
                    val fetchedData: List<RecipeApiModel.Recipe>? = it.hits
                    if(fetchedData != null){

                        for(i in fetchedData){
                            extractedData?.add(i.recipe!!)
                        }

                        val filteredData = extractedData.elementAt(0)
                        setupUI(filteredData)

                    }
                    pb_loading_detail.visibility = View.GONE

                }, { err ->
                    var msg = err.localizedMessage
                    Log.i("Aryan", msg.toString())
                })
        subscriptions.add(subscribe)
    }

    private fun setupUI(data: RecipeApiModel.RecipeDetails){

        Picasso.get()
                .load(data.image)
                .fit()
                .error(android.R.drawable.ic_menu_report_image)
                .into(iv_recipe_details)

        tv_recipe_title_detail.text = data.label
        val ingredientList: List<String>? = data.ingredients
        val dietLabel: List<String>? = data.dietLabel
        val healthLabel: List<String>? = data.healthLabel

        if(dietLabel != null){
            tv_dietLabel.text = dietLabel.elementAt(0).toString()
        }

        if(!healthLabel.isNullOrEmpty()){

            val healthLabelSize = healthLabel.size

            for(i in 0 until healthLabelSize){
                val rowTextView = TextView(requireContext())
                rowTextView.text = "✅" + healthLabel.elementAt(i).toString()
                ll_healthCheck.addView(rowTextView)
                rowTextView.setPadding(20,10,10,10)
                rowTextView.setTextColor(Color.BLACK)
            }
        }

        if(!ingredientList.isNullOrEmpty()){

            val ingredientSize = ingredientList.size

            for(i in 0 until ingredientSize){
                val ingredientItem = TextView(requireContext())
                ingredientItem.text = "\uD83D\uDE0A" + ingredientList.elementAt(i).toString()
                ll_ingredients.addView(ingredientItem)
                ingredientItem.setPadding(20,10,10,10)
                ingredientItem.setTextColor(Color.BLACK)
            }
        }

        val fetchedNutrients: RecipeApiModel.Nutrients? = data.totalNutrients

        val filteredFat: RecipeApiModel.NutrientDetails? = fetchedNutrients?.fat
        val filteredProtein: RecipeApiModel.NutrientDetails? = fetchedNutrients?.protein
        val filteredCarbs: RecipeApiModel.NutrientDetails? = fetchedNutrients?.carbs

        if(filteredFat != null && filteredCarbs != null && filteredProtein != null){

            val fatDouble: Double = String.format("%.1f", filteredFat.quantity).toDouble()
            val proteinDouble: Double = String.format("%.1f", filteredProtein.quantity).toDouble()
            val carbsDouble: Double = String.format("%.1f", filteredCarbs.quantity).toDouble()

            val fatUnit = filteredFat.unit.toString()
            val proteinUnit = filteredProtein.unit.toString()
            val carbsUnit = filteredCarbs.unit.toString()

            tv_fat.text = fatDouble.toString() + fatUnit
            tv_protein.text = proteinDouble.toString() + proteinUnit
            tv_carbs.text = carbsDouble.toString() + carbsUnit

            // ROOM COLLECTIONS
            room_fat_stat = fatUnit
            room_carbs_stat = carbsUnit
            room_protein_stat = proteinUnit

        }

        // ROOM COLLECTIONS
        room_recipeName = data.label
        room_recipeImage = data.image
        room_dietLabel = data.dietLabel
        room_recipe_ingredients = data.ingredients
        room_foodHealthChecks = data.healthLabel

        tb_favourite.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24)
                Toast.makeText(requireContext(), "is checked", Toast.LENGTH_LONG).show()

                val favRecipeData = RecipeRoomModel(
                        0,
                        data.label.toString(),
                        data.image.toString(),
                        data.dietLabel.toString(),
                        data.healthLabel!!,
                        data.ingredients!!,
                        room_fat_stat.toString(),
                        room_carbs_stat.toString(),
                        room_protein_stat.toString()
                )
                recipeViewModel.insertFavRecipe(favRecipeData)
                //recipeViewModel.deleteAllFavRecipe()
                //recipeViewModel.deleteFavRecipe(data.label)

                recipeViewModel.getAllFavouriteRecipes.observe(requireActivity(), Observer { it ->
                    Log.i("Aryan", it.toString())
                })

            }else{
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24)
                Toast.makeText(requireContext(), "is not checked", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun setupToolbar(recipeName: String) {
        tv_toolbar_title.text = recipeName.toString()
        iv_toolbar_back_button.setOnClickListener {
            navController.navigateUp()
        }
    }
}