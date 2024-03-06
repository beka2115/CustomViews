package com.example.customviews.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.example.customviews.R
import com.example.customviews.databinding.ItemButtonBinding
import com.google.android.material.card.MaterialCardView

class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private var binding: ItemButtonBinding =
        ItemButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var isLoading: Boolean = false
        get() = binding.btnProgress.isVisible
        set(value) {
            field = value
            binding.btnText.isVisible = !value
            binding.btnProgress.isVisible = value
        }

    var buttonText: CharSequence?
        get() = binding.btnText.text
        set(value) {
            binding.btnText.text = value
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomButton) {
            buttonText = getString(R.styleable.CustomButton_buttonText) ?: ""
            isLoading = getBoolean(R.styleable.CustomButton_isLoading, false)
            cardElevation = getDimension(R.styleable.CustomButton_cardElevation, 0f)
            radius = getDimension(R.styleable.CustomButton_cardCornerRadius, 0f)
        }
        initView()
    }

    private fun initView() {
        val marginVertical = resources.getDimension(R.dimen._16dp).toInt()
        val marginHorizontal = resources.getDimension(R.dimen._4dp).toInt()
        binding.btnText.setPadding(
            marginHorizontal,
            marginVertical,
            marginHorizontal,
            marginVertical
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            outlineSpotShadowColor = ContextCompat.getColor(context,R.color.nothing)
        }

        val rippleColor = ContextCompat.getColor(context,R.color.shimmer)
        val rippleColorStateList = ColorStateList.valueOf(rippleColor)
        this.rippleColor = rippleColorStateList

    }
}