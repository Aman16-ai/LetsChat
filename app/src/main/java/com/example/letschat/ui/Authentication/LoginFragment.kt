package com.example.letschat.ui.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.letschat.R
import com.example.letschat.ui.Authentication.viewmodel.LoginUserViewModel


class LoginFragment : Fragment() {
    private lateinit var emailEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var registertv : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var btn : Button

    private val loginUserViewModel : LoginUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailEt = view.findViewById(R.id.email_et)
        passwordEt = view.findViewById(R.id.pass_et)
        registertv = view.findViewById(R.id.registerViewTv)
        btn = view.findViewById(R.id.loginBtn)
        progressBar = view.findViewById(R.id.login_progress_bar)

//        progressBar.visibility = View.GONE

        loginUserViewModel.getAuthStatus().observe(viewLifecycleOwner){
           it?.let{
               if(it) {
                   val action = LoginFragmentDirections.actionLoginFragmentToFriendsFragment()
                   findNavController().navigate(action)
               }
           }
        }
        btn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            loginUserViewModel.loginUser(email = emailEt.text.toString(), password = passwordEt.text.toString())
        }
        registertv.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterUserFragment()
            findNavController().navigate(action)
        }
        return view
    }

}