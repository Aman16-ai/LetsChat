package com.example.letschat.ui.Authentication

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letschat.R
import com.example.letschat.helper.getFileExtension
import com.example.letschat.ui.Authentication.viewmodel.RegisterUserViewModel


class RegisterUserFragment : Fragment() {
    private lateinit var firstnameEt : EditText
    private lateinit var lastnameEt : EditText
    private lateinit var emailEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var logintv : TextView
    private lateinit var img:ImageView
    private lateinit var btn : Button
    private lateinit var imgUri :Uri
    private val registerUserViewModel : RegisterUserViewModel by viewModels()

    private val getImgFromSystem = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            imgUri = it
            img.setImageURI(it)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_user, container, false)
        registerUserViewModel.getAuthStatus().observe(viewLifecycleOwner) {
            if(it) {
                val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToFriendsFragment()
                findNavController().navigate(action)

            }
        }

        firstnameEt = view.findViewById(R.id.register_firstname_et)
        lastnameEt = view.findViewById(R.id.register_lastname_et)
        emailEt = view.findViewById(R.id.register_email_et)
        passwordEt = view.findViewById(R.id.register_pass_et)
        logintv = view.findViewById(R.id.loginViewTv)
        btn = view.findViewById(R.id.registerBtn)
        img = view.findViewById(R.id.img)


        img.setOnClickListener {
            getImgFromSystem.launch("image/*")
        }
        btn.setOnClickListener {
            getFileExtension(requireContext(),imgUri)?.let { type ->
                registerUserViewModel.registerUser(firstnameEt.text.toString(), lastnameEt.text.toString(),imgUri,
                    type,emailEt.text.toString(),passwordEt.text.toString())
            }
        }
        logintv.setOnClickListener {
            val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
            findNavController().navigate(action)

        }
        return view
    }


}