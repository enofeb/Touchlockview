package com.enofeb.lockview

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.forEach
import com.enofeb.lockview.databinding.LayoutTouchLockViewBinding
import kotlinx.coroutines.*

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

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    private var _switchEnabledText: String? = null
    private var _switchDisabledText: String? = null

    private var isTouchEnable: Boolean? = false

    private lateinit var viewCountDownTimer: CountDownTimer
    private lateinit var lockCountDownTimer: CountDownTimer

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
        initCountDowns()
        initSwitchChecked()
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
            binding.textViewLabel.text = switchEnabledText
        } else {
            binding.textViewLabel.text = switchDisabledText
        }
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.complete()
    }

}