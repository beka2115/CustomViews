package com.example.customviews

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnClearFocus?.setOnClickListener {
            binding?.etCustom?.clearFocus()
            val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(binding?.root?.windowToken, 0)
        }

        binding?.btnError?.setOnClickListener {
            binding?.etCustom?.setError("Error")
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}