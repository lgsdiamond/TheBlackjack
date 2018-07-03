package com.lgsdiamond.theblackjack

import android.view.View
import android.view.animation.*
import com.lgsdiamond.theblackjack.blackjackelement.BjService

//=== Animations ====
const val CARD_ANIMATION_DURATION = 300L

enum class CardAnimation { NONE, DEALING, PEEKING, OPENING, DISCARDING }
enum class BJAnimation { NONE, CARD_DEALING, CARD_PEEKING, CARD_OPENING, CARD_DISCARDING,
    BTN_EMPHASIS
}

fun View.bjAnimation(animCode: BJAnimation, delay: Long = 0L) {
    var anim: AnimationSet? = null
    when (animCode) {
        BJAnimation.CARD_DEALING -> {
            anim = BjAnimUtility.sCardDealAnim
        }
        BJAnimation.CARD_PEEKING -> {
            anim = BjAnimUtility.sCardDealAnim
        }
        BJAnimation.CARD_OPENING -> {
            anim = BjAnimUtility.sCardDealAnim
        }
        BJAnimation.CARD_DISCARDING -> {
            anim = BjAnimUtility.sCardDealAnim
        }
        BJAnimation.BTN_EMPHASIS -> {
            anim = BjAnimUtility.sButtonEmphAnim
        }
        else -> {
        }
    }
    if (anim != null) {
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                BjService.notifyDelayUI(delay)
            }

            override fun onAnimationStart(animation: Animation?) {
                BjService.clearDelayUI()
            }
        })

        startAnimation(anim)
    }
}

class BjAnimUtility {
    companion object {
        val sCardDealAnim: AnimationSet by lazy { newCardDealAnimation() }
        val sCardPeekAnim: AnimationSet by lazy { newCardPeekAnimation() }
        val sCardDiscardAnim: AnimationSet by lazy { newCardDiscardAnimation() }
        val sScoreTextAnim: AnimationSet by lazy { newScoreTextAnimation() }
        val sButtonEmphAnim: AnimationSet by lazy { newButtonEmphAnimation() }

        private fun newCardDealAnimation(): AnimationSet {
            val anim = AnimationSet(true)
            val trans = TranslateAnimation(200.0f, 0.0f, 0.0f, 0.0f)
            val alpha = AlphaAnimation(0.0f, 1.0f)
            val scale = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f)

            anim.addAnimation(trans)
            anim.addAnimation(alpha)
            anim.addAnimation(scale)

            anim.duration = CARD_ANIMATION_DURATION
            anim.interpolator = DecelerateInterpolator()

            return anim
        }

        private fun newCardPeekAnimation(): AnimationSet {
            val anim = AnimationSet(true)
            val transA = TranslateAnimation(0.0f, 30.0f, 0.0f, 0.0f)
            val transB = TranslateAnimation(30.0f, 0.0f, 0.0f, 0.0f)

            transA.interpolator = DecelerateInterpolator()
            transB.interpolator = AccelerateInterpolator()
            transB.startOffset = (CARD_ANIMATION_DURATION * 0.5).toLong()

            anim.addAnimation(transA)
            anim.addAnimation(transB)

            anim.duration = (CARD_ANIMATION_DURATION * 0.5).toLong()

            return anim
        }

        private fun newCardDiscardAnimation(): AnimationSet {
            val anim = AnimationSet(true)
            val alpha = AlphaAnimation(1.0f, 0.0f)
            val scale = ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f)

            anim.addAnimation(alpha)
            anim.addAnimation(scale)

            anim.duration = CARD_ANIMATION_DURATION
            anim.interpolator = DecelerateInterpolator()

            return anim
        }

        private fun newScoreTextAnimation(): AnimationSet {
            val anim = AnimationSet(true)
            val alpha = AlphaAnimation(0.0f, 1.0f)
            val scale = ScaleAnimation(3.0f, 1.0f, 3.0f, 1.0f)

            anim.addAnimation(alpha)
            anim.addAnimation(scale)

            anim.duration = (CARD_ANIMATION_DURATION * 1.2).toLong()
            anim.interpolator = DecelerateInterpolator()

            return anim
        }

        private fun newButtonEmphAnimation(): AnimationSet {
            val anim = AnimationSet(true)
            val scale = ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f)
            val alpha = AlphaAnimation(0.8f, 1.0f)
            anim.addAnimation(scale)
            anim.addAnimation(alpha)
            anim.duration = 500L
            anim.interpolator = DecelerateInterpolator()

            return anim
        }
    }
}
