package com.enofeb.lockview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.enofeb.lockview.databinding.LayoutTouchLockViewBinding
import kotlinx.coroutines.*
import kotlin.concurrent.fixedRateTimer

class TouchLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.touchLockViewStyle
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTouchLockViewBinding = LayoutTouchLockViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private val job = Job()

    private val scope = CoroutineScope(job + Dispatchers.Main)

    init {
        initView()
    }

    private fun initView() {
        initLongClickListener()
        initSwitchChecked()
    }

    private fun initLongClickListener() {

        this@TouchLockView.setOnLongClickListener {
            showComponentItems()
            fixedRateTimer("timer", false, 4000, 10) {
                setTimeForSwitchVisibility()
                this.cancel()
            }
            return@setOnLongClickListener true
        }

    }

    private fun initSwitchChecked() {
        binding.switchTouch.setOnCheckedChangeListener { _, isChecked ->
            this@TouchLockView.children.forEach {
                if (isChecked) {
                    if (it.id != R.id.switchTouch) {
                        it.isClickable = false
                    }
                } else {
                    it.isClickable = true
                }
            }
        }
    }

    private fun setTimeForSwitchVisibility() {
        scope.launch {
            binding.apply {
                switchTouch.dismiss()
                textViewLabel.dismiss()
                viewLayer.dismiss()
            }
        }
    }

    private fun showComponentItems() {
        binding.apply {
            switchTouch.show()
            textViewLabel.show()
            viewLayer.show()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.complete()
    }

}