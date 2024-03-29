package com.example.dynamicgroceryapp.ui.login

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.dynamicgroceryapp.databinding.FragmentLoginBinding
import com.example.dynamicgroceryapp.R
import com.google.firebase.auth.FirebaseAuth
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
        //Creates authentication instance
        auth = FirebaseAuth.getInstance()

        //Email and password inputs from user
        val email = binding.loginEmail
        val password = binding.loginPassword

        //Listener for create account button. Sends user to registration page.
        binding.createAccountButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        //Listener for forgot password. Sends user to recovery page.
        binding.forgotPassword.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_accountRecoveryFragment)
        }

        //Listener for sign in button.
        binding.signInButton.setOnClickListener {
            //Converts

            val email: String = email.text.toString()
            val password: String = password.text.toString()
            var stop: Boolean = true

            //Checks to see if the email or password field is empty.
            if (email.isEmpty() || password.isEmpty()) {
                showToastMessage(requireContext(), "Invalid Email/Password")
            } else {
                stop = false
            }

            if (!stop) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToastMessage(requireContext(), "Signed in Successfully")
                            view?.findNavController()?.navigate(R.id.action_loginFragment_to_appMainFragment)

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

data class LoginInfo(val username: String, val passwordAuthentication: PasswordAuthentication) {

}