package com.enofeb.lockview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

class TouchLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.touchLockViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initView()
    }

    private fun initView() {
        val rootView =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater)
                ?.inflate(R.layout.layout_touch_lock_view, this, true)

    }

}