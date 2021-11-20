package com.enofeb.lockview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.enofeb.lockview.databinding.LayoutTouchLockViewBinding

class TouchLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.touchLockViewStyle
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTouchLockViewBinding = LayoutTouchLockViewBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    init {
        initView()
    }

    private fun initView() {
        setLongClickListener()
    }

    private fun setLongClickListener() {
        binding.apply {
            constrainLayout.setOnLongClickListener {
                switchTouch.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }
        }
    }

}