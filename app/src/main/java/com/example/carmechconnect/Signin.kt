package com.example.carmechconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.carmechconnect.databinding.ActivitySigninBinding

class Signin : AppCompatActivity() {
    // name of our state machine
    private val stateMachineName = "State Machine 1"
    private lateinit var binding : ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun onEmailFoucus() {

    // we are using  focus listner to capture the currently focued input field
        binding!!.email.setOnFocusChangeListener { v, hasFocus ->

            // when user click on email to enter email id then our character will look it
            if (hasFocus) {

                // setBoolen will take 3 arguments machine name, state name and value
                binding!!.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "Check",
                    value = true
                )
            }
            // else character will changes its focus
            else {
                binding!!.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "Check",
                    value = false
                )
            }
        }
    }

    private fun eyePostionChange() {

    // we are using text changed listner to change position of eyes whenever user type new character
        binding!!.email.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    // setNumber will take 3 input
                    // machine name, state name, and value
                    binding!!.loginCharacter.controller.setNumberState(stateMachineName, "Look", 2*p0!!.length.toFloat())
                } catch (_: Exception) {

                }
            }

            override fun afterTextChanged(p0: Editable?) {


            }

        })
    }

    private fun onPasswordFoucus() {

    // we are using  focus listner to capture the currently focued input field
        binding!!.password.setOnFocusChangeListener { v, hasFocus ->
            // if focus is on password text field then close eyes
            if (hasFocus) {

                // setBoolen will take 3 arguments machine name, state name and value
                binding!!.loginCharacter.controller.setBooleanState(stateMachineName, "hands_up", true)
            }

            // if focus is not on password text field then open eyes
            else {
                binding!!.loginCharacter.controller.setBooleanState(stateMachineName, "hands_up", false)
            }
        }
    }

    private fun LoginAccount(email: String, password: String) {

    // if credentials matches the show success state
        if (email == "admin@gmail.com" && password == "123456"){

            // using looper to use animation multiple times for 2s
            Handler(mainLooper).postDelayed({

                // fireState will take two input machine name and state
                binding!!.loginCharacter.controller.fireState(stateMachineName, "success")

            }, 2000)

            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
        }
        // if credentials mismatches then show failure state
        else{

            // using looper to use animation multiple times for 2s
            Handler(mainLooper).postDelayed({

                // fireState will take two input machine name and state
                binding!!.loginCharacter.controller.fireState(stateMachineName, "fail")

            },2000)

            Toast.makeText(this, "Invalid Id or Password", Toast.LENGTH_SHORT).show()
        }
    }
}