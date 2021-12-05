package com.enofeb.lockview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
        this, true
    )

    private val job = Job()

    private val scope = CoroutineScope(job + Dispatchers.Main)

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
                fixedRateTimer("timer", false, 4000, 10) {
                    setTimeForSwitchVisibility()
                    this.cancel()
                }
                return@setOnLongClickListener true
            }
        }
    }

    private fun setTimeForSwitchVisibility() {
        scope.launch {
            binding.switchTouch.visibility = View.GONE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.complete()
    }

}