package com.enofeb.lockview

import android.view.View

/*
 * Shows the given view
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/*
 * Hides the given view
 */
fun View.hide() {
    this.visibility = View.INVISIBLE
}

/*
 * Dismiss the given view from the screen
 */
fun View.dismiss() {
    this.visibility = View.GONE
}

fun View.showIf(statement: Boolean) {
    if (statement) show() else dismiss()
}