package com.example.customviews.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.customviews.R

class CustomEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    private val bgDefault = ContextCompat.getDrawable(context, R.drawable.bg_default_edit_text)
    private val bgFocus = ContextCompat.getDrawable(context, R.drawable.bg_focus_edit_text)
    private val bgError = ContextCompat.getDrawable(context, R.drawable.bg_error_edit_text)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        try {
            val inputType = typedArray.getInt(R.styleable.CustomEditText_customInputType, 0)
            setupInputType(inputType)
        } finally {
            typedArray.recycle()
        }
        initView()
        setPaddingWithResources()
        setListener()
    }

    private fun setupInputType(type: Int) {
        inputType = when (type) {
            1 -> EditorInfo.TYPE_CLASS_NUMBER
            2 -> EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
            else -> EditorInfo.TYPE_CLASS_TEXT
        }
    }

    private fun initView() {
        isFocusableInTouchMode = true
        this.background = bgDefault
        this.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                background = bgFocus
                error = null
            } else {
                background = bgDefault
            }
        }
        this.imeOptions = EditorInfo.IME_ACTION_DONE
    }

    private fun setPaddingWithResources() {
        val verticalPadding = resources.getDimensionPixelSize(R.dimen._16dp)
        val horizontalPadding = resources.getDimensionPixelSize(R.dimen._10dp)
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
    }

    private fun setListener() {
        setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                clearFocus()
                true
            } else false
        }
        this.doAfterTextChanged {
            background = bgFocus
        }
    }

    fun setError(errorText: String) {
        error = errorText
        background = bgError
    }
}