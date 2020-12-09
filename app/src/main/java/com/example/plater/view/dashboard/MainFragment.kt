package com.example.plater.view.dashboard

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.plater.R
import com.example.plater.util.Constants
import com.example.plater.viewModel.RecipeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_nickname.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private val subscriptions = CompositeDisposable()
    private lateinit var navController: NavController
    private var isFirstTime: Boolean = true
    private var inputUsername: String = ""
    private var testI: String = ""
    private lateinit var userDataStore: UserDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDataStore = UserDataStore(requireContext())

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.getAllFavouriteRecipes.observe(requireActivity(), Observer { it ->
            Log.i("Aryan", it.toString())
        })

        showNicknameDialog()

        userDataStore.getUserState.asLiveData()
                .observe(requireActivity(), Observer {
                    if (it == false) {
                        Constants.IS_FIRST_TIME = it
                        if(!Constants.IS_FIRST_TIME){

                            et_username.visibility = View.GONE
                            btn_input_username.visibility = View.GONE
                            userDataStore.getUsername.asLiveData().observe(requireActivity(), Observer {
                                tv_username.text = it.toString()
                            })
                        }

                    }else {
                    }
                })

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        navController = Navigation.findNavController(view)


        switch_theme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

    }

    private fun showNicknameDialog(): AlertDialog? {

        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_nickname, null)
        dialogBuilder.setView(dialogView)
        val input = dialogView.findViewById(R.id.et_nickname) as EditText
        dialogBuilder.setPositiveButton(getString(R.string.okay_text)) { dialog, which ->

            inputUsername = input.text.toString()

            lifecycleScope.launch(Dispatchers.Main) {
                userDataStore.saveUsername(inputUsername)
            }
            lifecycleScope.launch (Dispatchers.Main) {
                userDataStore.saveUserState(false)
            }
            Snackbar.make(requireView(), "Data Store Saved !", Snackbar.LENGTH_SHORT).show()
        }
        dialogBuilder.setNegativeButton(getString(R.string.cancel_text)) { dialog, which -> dialog!!.dismiss() }
        return dialogBuilder.show()
    }
}

