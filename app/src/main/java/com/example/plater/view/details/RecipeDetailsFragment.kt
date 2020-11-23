package com.example.plater.view.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.plater.R
import com.example.plater.model.RecipeApiModel
import com.example.plater.viewModel.RecipeViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.*


class RecipeDetailsFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private val subscriptions = CompositeDisposable()
    private val extractedData = ArrayList<RecipeApiModel.RecipeDetails>()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val fetchedData: String? = arguments?.getString("recipeName")
        val refinedRecipeInput = fetchedData?.replace("\\s".toRegex(), "+")
        Log.i("Aryan", refinedRecipeInput.toString())

        setupToolbar(fetchedData.toString())
        requestRecipeFromApi(refinedRecipeInput.toString())

    }

    private fun requestRecipeFromApi(query: String) {
        val subscribe = viewModel.requestGetRecipeFromApi(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({

                    pb_loading_detail.visibility = View.VISIBLE
                    val fetchedData: List<RecipeApiModel.Recipe>? = it.hits
                    if(fetchedData != null){

                        for(i in fetchedData){
                            Log.i("Aryan", "i Data:${i.recipe}")
                            extractedData?.add(i.recipe!!)
                            Log.i("Aryan", "Extract Data:${extractedData!!.size}")
                        }

                        val filteredData = extractedData.elementAt(0)
                        setupUI(filteredData)
                        Log.i("Aryan", "Filtered Data:${filteredData.label}")

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
        Log.i("Aryan", "HEALTH LABEL:" + healthLabel?.toString())

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
        }
    }

    private fun setupToolbar(recipeName: String) {
        tv_toolbar_title.text = recipeName.toString()
        iv_toolbar_back_button.setOnClickListener {
            navController.navigateUp()
        }
    }
}