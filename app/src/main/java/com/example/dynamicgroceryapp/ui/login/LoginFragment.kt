package com.example.dynamicgroceryapp.ui.login

import android.content.ContentValues.TAG
import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.dynamicgroceryapp.databinding.FragmentLoginBinding

import com.example.dynamicgroceryapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.net.PasswordAuthentication

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        val email = binding.loginEmail
        val password = binding.loginPassword

        binding.createAccountButton.setOnClickListener{
                view: View->
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.forgotPassword.setOnClickListener{
                view: View->
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_accountRecoveryFragment)
        }

        binding.signInButton.setOnClickListener{
            val email: String = email.text.toString()
            val password: String = password.text.toString()
            var stop: Boolean = true

            if (email.isEmpty() || password.isEmpty()){
                showToastMessage(requireContext(), "Invalid Email/Password")
            }
            else {
                stop = false
            }

            if (!stop) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToastMessage(requireContext(), "Signed in Successfully")

                        } else {
                            showToastMessage(requireContext(), "Invalid Email/Password")
                        }
                    }
            }


        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    //Create toast method
    fun showToastMessage(context: Context, message: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }


}
data class LoginInfo(val username: String, val passwordAuthentication: PasswordAuthentication){

}