package com.thanosfisherman.presentation.common.extensions

import android.animation.*
import android.animation.Animator.AnimatorListener
import android.graphics.Rect
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Produces an animation when the display is cleared or an error is detected which looks like it
 * starts from the center of the [sourceView] button which caused the event and sweeps across the
 * result/formula display.
 *
 * We initialize our variable *groupOverlay* by retrieving the top-level window decor view from
 * the current Window of the activity and fetching the overlay for this view, creating it if it
 * does not yet exist (a [ViewGroupOverlay] is an extra layer that sits on top of a [ViewGroup]
 * (the "host view") which is drawn after all other content in that view (including the view
 * group's children). Interaction with the overlay layer is done by adding and removing views
 * and drawables). We initialize our variable *displayRect* with a new instance of [Rect] and
 * use the *getGlobalVisibleRect* method of [mDisplayView] to set *displayRect* to encompass the
 * region that it occupies on the screen. We initialize our variable *revealView* with a new
 * instance of [View], set its *bottom* to the *bottom* of *displayRect*, its *left* to the
 * *left* of *displayRect*, and its *right* to the *right* of *displayRect* (its *top* defaults
 * to 0). We then set the background color of *revealView* to the color with resource id
 * [colorRes] and add it to *groupOverlay*. We initialize our variable *clearLocation* with a
 * new instance of [IntArray](2), use the *getLocationInWindow* method of [sourceView] to fill
 * it with its (x,y) coordinates then add half the width of [sourceView] to its x coordinate
 * and half the height to its y coordinate (*clearLocation* now contains the coordinates of the
 * center of [sourceView]). We initialize our variable *revealCenterX* with the difference between
 * the X coordinate in *clearLocation* and the *left* side of *revealView*, and our variable
 * *revealCenterY* with the difference between the Y coordinate in *clearLocation* and the *top*
 * side of *revealView*. We then calculate our variable *revealRadius* to be the maximum of the
 * distance from the center of the button that was clicked and the top corners of *revealView*.
 * We initialize our variable *revealAnimator* with an [Animator] which can animate a clipping
 * circle for *revealView* centered at *revealCenterX* and *revealCenterY* with a starting radius
 * of 0.0 and an end radius of *revealRadius*, we set the duration to the system constant
 * config_mediumAnimTime (500ms), and add [listener] as an [AnimatorListener] to it. We initialize
 * our variable *alphaAnimator* with an [ObjectAnimator] which will animate the ALPHA property of
 * *revealView* to 0.0 and set its duration to config_mediumAnimTime also. We initialize our
 * variable *animatorSet* with a new instance of [AnimatorSet], configure it to play *revealAnimator*
 * before *alphaAnimator*, set its [TimeInterpolator] to an [AccelerateDecelerateInterpolator],
 * and add an anonymous [AnimatorListenerAdapter] whose *onAnimationEnd* override will remove
 * *revealView* from *groupOverlay* and set [mCurrentAnimator] to null. Having fully configured
 * *animatorSet* we set [mCurrentAnimator] to it and start it running.
 *
 * @param displayView the view in which the sweep wave animation shall be displayed on
 * @param window the activity window
 * @param colorRes resource id of the color we are to use.
 * @param listener [AnimatorListener] whose *onAnimationEnd* override will be called at the end
 * of the animation in order to perform whatever cleanup is appropriate.
 */
fun View.reveal(displayView: View, window: Window, colorRes: Int, listener: AnimatorListener) {

    val groupOverlay = window.decorView.overlay as ViewGroupOverlay

    val displayRect = Rect()
    displayView.getGlobalVisibleRect(displayRect)

    // Make reveal cover the display and status bar.
    val revealView = View(context)
    revealView.bottom = displayRect.bottom
    revealView.left = displayRect.left
    revealView.right = displayRect.right
    revealView.setBackgroundColor(ContextCompat.getColor(context, colorRes))
    groupOverlay.add(revealView)

    val clearLocation = IntArray(2)
    this.getLocationInWindow(clearLocation)
    clearLocation[0] += this.width / 2
    clearLocation[1] += this.height / 2

    val revealCenterX = clearLocation[0] - revealView.left
    val revealCenterY = clearLocation[1] - revealView.top

    val x1_2 = (revealView.left - revealCenterX).toDouble().pow(2.0)
    val x2_2 = (revealView.right - revealCenterX).toDouble().pow(2.0)
    val y_2 = (revealView.top - revealCenterY).toDouble().pow(2.0)
    val revealRadius = max(sqrt(x1_2 + y_2), sqrt(x2_2 + y_2)).toFloat()

    val revealAnimator = ViewAnimationUtils.createCircularReveal(
        revealView,
        revealCenterX, revealCenterY, 0.0f, revealRadius
    )
    revealAnimator.duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
    revealAnimator.addListener(listener)

    val alphaAnimator = ObjectAnimator.ofFloat(revealView, View.ALPHA, 0.0f)
    alphaAnimator.duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()

    val animatorSet = AnimatorSet()
    animatorSet.play(revealAnimator).before(alphaAnimator)
    animatorSet.interpolator = AccelerateDecelerateInterpolator()
    animatorSet.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animator: Animator) {
            groupOverlay.remove(revealView)
        }
    })

    animatorSet.start()
}