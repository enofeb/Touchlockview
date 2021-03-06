package com.enofeb.lockview

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.FloatRange
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

    private var _lockEnabledText: String? = null
    private var _lockDisabledText: String? = null

    @ColorInt
    private var _lockTextColor = Color.BLACK

    @FloatRange(from = 0.0, to = 1.0)
    private var _animationSpeed = 0.5f

    private var _hiddenViewTime: Long = 5000

    var lockEnabledText: String?
        get() = _lockEnabledText
        set(value) {
            _lockEnabledText = value
        }

    var lockDisabledText: String?
        get() = _lockDisabledText
        set(value) {
            _lockDisabledText = value
        }

    var lockTextColor: Int
        @ColorInt get() = _lockTextColor
        set(@ColorInt value) {
            _lockTextColor = value
            binding.textViewLabel.setTextColor(value)
        }

    var lockTextSize: Float
        @Dimension get() = binding.textViewLabel.textSize
        set(@Dimension value) {
            binding.textViewLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }

    var animationSpeed: Float
        @FloatRange(from = 0.0, to = 1.0) get() = _animationSpeed
        set(@FloatRange(from = 0.0, to = 1.0) value) {
            binding.lottieLock.speed = value
        }

    var hiddenViewTime: Long
        get() = _hiddenViewTime
        set(@Dimension value) {
            _hiddenViewTime = value
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
                lockEnabledText = getString(R.styleable.TouchLockView_lockEnabledText)
                lockDisabledText = getString(R.styleable.TouchLockView_lockDisabledText)
                lockTextColor = getColor(R.styleable.TouchLockView_lockTextColor, lockTextColor)
                lockTextSize = getDimension(R.styleable.TouchLockView_lockTextSize, lockTextSize)
                animationSpeed = getFloat(R.styleable.TouchLockView_animationSpeed, animationSpeed)
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
        viewCountDownTimer = object : CountDownTimer(hiddenViewTime, INTERVAL_TIMER_FUTURE) {
            override fun onTick(p0: Long) {
                //no-op
            }

            override fun onFinish() {
                setTimeForSwitchVisibility()
            }
        }
        lockCountDownTimer = object : CountDownTimer(hiddenViewTime, INTERVAL_TIMER_FUTURE) {
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
            speed = animationSpeed
            progress = DEFAULT_PROGRESS
            setOnClickListener {
                viewCountDownTimer.cancel()
                lockCountDownTimer.cancel()
                lockCountDownTimer.start()
                if (isTouchEnable == true) {
                    isTouchEnable = false
                    setMinAndMaxProgress(MIN_PROGRESS, DEFAULT_PROGRESS)
                } else {
                    isTouchEnable = true
                    setMinAndMaxProgress(DEFAULT_PROGRESS, MAX_PROGRESS)
                }
                setLabel(isTouchEnable)
                adjustRootClickable(isTouchEnable)
                playAnimation()
            }
        }
    }

    private fun setLabel(isChecked: Boolean?) {
        if (isChecked == true) {
            binding.textViewLabel.text = lockEnabledText
        } else {
            binding.textViewLabel.text = lockDisabledText
        }
    }

    private fun setLabelDefaultText() {
        binding.textViewLabel.text = lockDisabledText
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

    companion object {
        private const val DEFAULT_PROGRESS = 0.5f
        private const val MIN_PROGRESS = 0.5f
        private const val MAX_PROGRESS = 1f
        private const val INTERVAL_TIMER_FUTURE: Long = 2000
    }

}