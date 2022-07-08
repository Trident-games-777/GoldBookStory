package com.devsisters.SolitaireDeckedOu.game_screen.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView
import androidx.core.animation.doOnEnd

fun fall(
    view1: ImageView,
    view2: ImageView,
    view3: ImageView,
    onEnd: () -> Unit = {}
): AnimatorSet {
    return AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(view1, "scaleX", 70f, 1f),
            ObjectAnimator.ofFloat(view2, "scaleX", 70f, 1f),
            ObjectAnimator.ofFloat(view3, "scaleX", 70f, 1f),
            ObjectAnimator.ofFloat(view1, "scaleY", 70f, 1f),
            ObjectAnimator.ofFloat(view2, "scaleY", 70f, 1f),
            ObjectAnimator.ofFloat(view3, "scaleY", 70f, 1f),
        )
        doOnEnd { onEnd() }
        duration = 300
    }
}