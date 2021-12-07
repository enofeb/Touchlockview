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

    private var _switchEnabledText: String? = null
    private var _switchDisabledText: String? = null

    var switchEnabledText: String?
        get() = _switchEnabledText
        set(value) {
            _switchEnabledText = value
        }

    var switchDisabledText: String?
        get() = _switchDisabledText
        set(value) {
            _switchDisabledText = value
        }

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
        initView()
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TouchLockView,
            defStyleAttr,
            0
        )

        try {
            with(typedArray) {
                switchEnabledText = getString(R.styleable.TouchLockView_switchEnabledText)
                switchDisabledText = getString(R.styleable.TouchLockView_switchDisabledText)
            }
        } catch (e: Exception) {
            // ignored
        } finally {
            typedArray.recycle()
        }
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
            adjustRootClickable(isChecked)
            setLabel(isChecked)
        }
    }

    private fun adjustRootClickable(isChecked: Boolean) {
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

    private fun setLabel(isChecked: Boolean) {
        if (isChecked) {
            binding.textViewLabel.text = switchEnabledText
        } else {
            binding.textViewLabel.text = switchDisabledText
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