package com.enofeb.lockview

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.forEach
import com.enofeb.lockview.databinding.LayoutTouchLockViewBinding

class TouchLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.touchLockViewStyle
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTouchLockViewBinding = LayoutTouchLockViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var isTouchEnable: Boolean? = false

    private lateinit var viewCountDownTimer: CountDownTimer
    private lateinit var lockCountDownTimer: CountDownTimer

    private var _touchEnabledText: String? = null
    private var _touchDisabledText: String? = null

    @ColorInt
    private var _touchTextColor = Color.BLACK

    var touchEnabledText: String?
        get() = _touchEnabledText
        set(value) {
            _touchEnabledText = value
        }

    var touchDisabledText: String?
        get() = _touchDisabledText
        set(value) {
            _touchDisabledText = value
        }

    var touchTextColor: Int
        @ColorInt get() = _touchTextColor
        set(@ColorInt value) {
            _touchTextColor = value
            binding.textViewLabel.setTextColor(value)
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
                touchEnabledText = getString(R.styleable.TouchLockView_touchEnabledText)
                touchDisabledText = getString(R.styleable.TouchLockView_touchDisabledText)
                touchTextColor = getColor(R.styleable.TouchLockView_touchTextColor, touchTextColor)
            }
        } catch (e: Exception) {
            // ignored
        } finally {
            typedArray.recycle()
        }
    }

    private fun initView() {
        initCountDowns()
        initSwitchChecked()
        setLabelDefaultText()
    }

    private fun initCountDowns() {
        viewCountDownTimer = object : CountDownTimer(2000, 4000) {
            override fun onTick(p0: Long) {
                //no-op
            }

            override fun onFinish() {
                setTimeForSwitchVisibility()
            }
        }
        lockCountDownTimer = object : CountDownTimer(2000, 4000) {
            override fun onTick(p0: Long) {
                //no-op
            }

            override fun onFinish() {
                setTimeForSwitchVisibility()
            }
        }
    }

    fun start() {
        showComponentItems()
        viewCountDownTimer.start()
    }

    private fun initSwitchChecked() {
        binding.lottieLock.apply {
            speed = 0.5f
            progress = 0.5f
            setOnClickListener {
                viewCountDownTimer.cancel()
                lockCountDownTimer.cancel()
                lockCountDownTimer.start()
                if (isTouchEnable == true) {
                    isTouchEnable = false
                    setMinAndMaxProgress(0.0f, 0.5f)
                } else {
                    isTouchEnable = true
                    setMinAndMaxProgress(0.5f, 1f)
                }
                setLabel(isTouchEnable)
                adjustRootClickable(isTouchEnable)
                playAnimation()
            }
        }
    }

    private fun setLabel(isChecked: Boolean?) {
        if (isChecked == true) {
            binding.textViewLabel.text = touchEnabledText
        } else {
            binding.textViewLabel.text = touchDisabledText
        }
    }

    private fun setLabelDefaultText() {
        binding.textViewLabel.text = touchDisabledText
    }

    private fun setTimeForSwitchVisibility() {
        this.dismiss()
    }

    private fun showComponentItems() {
        this.show()
    }

    private fun adjustRootClickable(isChecked: Boolean?) {

        val parent = this.parent as? ViewGroup

        parent?.children?.forEach { view ->
            if (view !is TouchLockView) {
                if (view is ViewGroup) {
                    val childGroup = view as? ViewGroup
                    childGroup?.forEach {
                        it.isClickable = isChecked != true
                    }
                } else {
                    view.isClickable = isChecked != true
                }
            }
        }
    }

}