package com.example.dynamicgroceryapp.ui.signup

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dynamicgroceryapp.R
import com.example.dynamicgroceryapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var UserImage: Uri

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

        binding.changeImageBtn.setOnClickListener(){
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }


        //listener for Sign up button
        binding.signupButton.setOnClickListener {
            //convert inputs to string
            val username: String = binding.textFieldUsername.text.toString()
            val password: String = binding.textFieldPassword.text.toString()
            val name: String = binding.textFieldName.text.toString()
            val email: String = binding.textFieldEmail.text.toString()
            val phoneNum: String = binding.textFieldPhone.text.toString()
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

            } else if(UserImage == null){
                showToastMessage(requireContext(), "Please select a profile image")
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

                            //grab userid
                            val currentUser=auth.currentUser
                            val reference= FirebaseDatabase.getInstance().getReference("UserTest")
                            val storage = FirebaseStorage.getInstance()

                            //create user and store in database
                            val storageReference = storage.reference.child("Profile").child(Date().time.toString())
                            storageReference.putFile(UserImage).addOnCompleteListener{
                                if (it.isSuccessful) {
                                    storageReference.downloadUrl.addOnSuccessListener { task ->
                                        val user = User(username, password, name, email, phoneNum, task.toString())

                                        currentUser?.uid?.let { reference.child(it).setValue(user) }
                                    }
                                }
                            }

                            val toast = Toast.makeText(requireContext(), message, duration)
                            toast.show()

                            //next page
                            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                UserImage = data.data!!

                binding.userImage.setImageURI(UserImage)
            }
        }
    }

}