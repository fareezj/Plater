package com.example.plater.view.favourite

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.plater.R
import com.example.plater.model.RecipeApiModel
import com.example.plater.model.RecipeRoomModel
import com.example.plater.viewModel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_favourite_recipe_details.*
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.toolbar_favourite_details.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.*
import kotlinx.android.synthetic.main.toolbar_with_back_button.iv_toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_back_button.tv_toolbar_title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList


class FavouriteRecipeDetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: RecipeViewModel
    private var inputResult: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view);
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val fetchedID: Int = requireArguments().getInt("favID")
        val fetchedName: String? = requireArguments().getString("favName")
        val fetchedImage: String? = requireArguments().getString("favImage")
        val fetchedDiet: String? = requireArguments().getString("favDiet")
        val fetchedFats: String? = requireArguments().getString("favFat")
        val fetchedCarbs: String? = requireArguments().getString("favCarbs")
        val fetchedProtein: String? = requireArguments().getString("favProtein")
        val fetchedHealth: List<String>? = requireArguments().getStringArrayList("favHealth")
        val fetchedIng: List<String>? = requireArguments().getStringArrayList("favIng")

        val collectedFavData = RecipeRoomModel(
            fetchedID,
            fetchedName!!,
            fetchedImage!!,
            fetchedDiet!!,
            fetchedHealth!!,
            fetchedIng!!,
            fetchedFats!!,
            fetchedProtein!!,
            fetchedCarbs!!
        )

        setupToolbar(fetchedName, fetchedID);
        setupUI(collectedFavData)

        btn_edit_fav_title.setOnClickListener {
            editTitleDialog(fetchedName)
        }

    }

    private fun editTitleDialog(favTitle: String): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)
        dialogBuilder.setView(dialogView)
        val input = dialogView.findViewById(R.id.et_fav_title) as EditText
        input.setText(favTitle)
        dialogBuilder.setPositiveButton(getString(R.string.okay_text)) { dialog, which ->
            inputResult = input.text.toString()
            tv_recipe_title_detail_fav.setText(inputResult.toString())
            Log.i("Fav", "${inputResult.toString()}")
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton(getString(R.string.cancel_text)) { dialog, which -> dialog!!.dismiss() }
        return dialogBuilder.show()
    }

    private fun setupToolbar(favTitle: String, favId: Int) {
        tv_toolbar_title.text = favTitle
        iv_toolbar_back_button.setOnClickListener {
            navController.navigateUp()
        }
        iv_delete_favourite.setOnClickListener {
            viewModel.deleteFavRecipe(favId)
            navController.navigateUp()
            Snackbar.make(requireView(), "$favTitle deleted !", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupUI(data: RecipeRoomModel){

        Picasso.get()
            .load(data.recipeImage)
            .fit()
            .error(android.R.drawable.ic_menu_report_image)
            .into(iv_recipe_details_fav)

        tv_recipe_title_detail_fav.text = data.recipeName
        val ingredientList: List<String>? = data.recipe_ingredients
        val dietLabel: String = data.dietLabel
        val healthLabel: List<String>? = data.foodHealthChecks

        if(dietLabel != null){
            tv_dietLabel_fav.text = dietLabel
        }

        if(!healthLabel.isNullOrEmpty()){

            val healthLabelSize = healthLabel.size

            for(i in 0 until healthLabelSize){
                val rowTextView = TextView(requireContext())
                rowTextView.text = "✅" + healthLabel.elementAt(i).toString()
                ll_healthCheck_fav.addView(rowTextView)
                rowTextView.setPadding(20,10,10,10)
                rowTextView.setTextColor(Color.BLACK)
            }
        }

        if(!ingredientList.isNullOrEmpty()){

            val ingredientSize = ingredientList.size

            for(i in 0 until ingredientSize){
                val ingredientItem = TextView(requireContext())
                ingredientItem.text = "\uD83D\uDE0A" + ingredientList.elementAt(i).toString()
                ll_ingredients_fav.addView(ingredientItem)
                ingredientItem.setPadding(20,10,10,10)
                ingredientItem.setTextColor(Color.BLACK)
            }
        }


            tv_fat_fav.text = data.fat_stat.toString() + 'g'
            tv_protein_fav.text = data.protein_stat.toString() + 'g'
            tv_carbs_fav.text = data.carbs_stat.toString() + 'g'

    }
}