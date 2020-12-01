package com.example.plater.view.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.plater.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {
            signUpProcess()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private fun signUpProcess() {

        auth = FirebaseAuth.getInstance()

        if(et_fullname.text.toString().isEmpty()) {
            et_fullname.error = "Please enter fullname"
            return
        }
        if(et_age.text.toString().isEmpty()) {
            et_age.error = "Please enter age"
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()){
            et_email.error = "Please enter valid email"
            return
        }
        if(et_password.text.toString().isEmpty()) {
            et_password.error = "Please enter password"
            return
        }

        auth.createUserWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "User registered success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "User registered fail", Toast.LENGTH_SHORT).show()
                }
            }

    }

}