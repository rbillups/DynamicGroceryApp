package com.example.dynamicgroceryapp.ui.signup

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dynamicgroceryapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //create binding and firebase
        auth = FirebaseAuth.getInstance()
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        //get user input
        val editTextUsername = binding.textFieldUsername
        val editTextPassword = binding.textFieldPassword
        val editTextName = binding.textFieldName
        val editTextEmail = binding.textFieldEmail
        val editTextPhone = binding.textFieldPhone

        binding.signupButton.setOnClickListener {
            //convert inputs to string
            val username: String = editTextUsername.text.toString()
            val password: String = editTextPassword.text.toString()
            val name: String = editTextName.text.toString()
            val email: String = editTextEmail.text.toString()
            val phoneNum: String = editTextPhone.text.toString()
            var stop: Boolean = true

            //Going to add more conditions
            //conditionals for user input
            if (username.isEmpty()) {
                showToastMessage(requireContext(), "Please enter username")

            } else if (password.isEmpty()) {
                showToastMessage(requireContext(), "Please enter password")

            } else if (name.isEmpty()) {
                showToastMessage(requireContext(), "Please enter Name")

            } else if (email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
                showToastMessage(requireContext(), "Please enter valid email")

            } else if (phoneNum.isEmpty()) {
                showToastMessage(requireContext(), "Please enter phoneNum")

            } else {
                stop = false
            }

            //If requirements met then create account
            if (!stop) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val message = "Account created"
                            val duration = Toast.LENGTH_SHORT

                            val toast = Toast.makeText(requireContext(), message, duration)
                            toast.show()

                            //Doesnt work yet
                            // view?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                requireContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

            }

        }

        return binding.root

    }

    //Create toast method
    fun showToastMessage(context: Context, message: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }

}