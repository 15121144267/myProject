package com.dispatching.feima.help;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AniCreator {

    public static final int DURATION = 300;
    public static final int DURATION_RATION = 6000;

    public static final int ANIMATION_MODE_DROPDOWN = 100;
    public static final int ANIMATION_MODE_DROPDOWN_RESEVERD = 104;
    public static final int ANIMATION_MODE_DROPDOWN_RESEVERD_PARENT = 1041;
    public static final int ANIMATION_MODE_POPUP = 101;
    public static final int ANIMATION_MODE_POPUP_PARENT = 1011;
    public static final int ANIMATION_MODE_POPUP_RESERVED = 105;
    public static final int ANIMATION_MODE_SILDEOUT_LEFT = 102;
    public static final int ANIMATION_MODE_SLIDIN_LEFT = 106;
    public static final int ANIMATION_MODE_SLIDEOUT_RIGHT = 103;
    public static final int ANIMATION_MODE_SLIDEIN_RIGHT = 107;

    public static final int ANIMATION_MODE_ROTATION_CLOCKWISED = 108;
    public static final int ANIMATION_MODE_ROTAION_ANTI_CLOCKWISED = 109;

    public static final int ANIMATION_MODE_ALPHA_VISABLE = 110;
    public static final int ANIMATION_MODE_ALPHA_INVISABLE = 111;
    public static final int ANIMATION_MODE_SCALE_TO_SMALL = 112;
    public static final int ANIMATION_MODE_SCALE_TO_BIG = 113;

    private Animation mAnimation_DropDown ;
    private Animation mAnimation_DropDown_Reseverd;
    private Animation mAnimation_DropDown_Reseverd_Parent;
    private Animation mAnimation_PopUp ;
    private Animation mAnimation_PopUp_Parent ;
    private Animation mAnimation_PopUp_Reseverd;


    private Animation mTranslateAnimationSlideOut_Left = null;
    private Animation mTranslateAnimationSlideIn_Left = null;

    private Animation mTranslateAnimationSlideOut_Right = null;
    private Animation mTranslateAnimationSlideIn_Right = null;

    private Animation mAlphaAnimationVisable = null;
    private Animation mAlphaAnimationInvisable = null;

    private Animation mScaleAnimationToSmall = null;
    private Animation mScaleAnimationToBig = null;

    private Animation mRotationAnimationClockWise = null;
    private Animation mRotationAnimationAntiClockWise = null;

    private AccelerateInterpolator mInterpolator_Ace = null;//加速
    private DecelerateInterpolator mInterpolator_Dec = null;

    private LinearInterpolator mLinearInter = new LinearInterpolator();

    private static AniCreator mInstance = null;

    private AniCreator() {

        mInterpolator_Ace = new AccelerateInterpolator();
        mInterpolator_Dec = new DecelerateInterpolator();

    }

    public static AniCreator getInstance() {

        if (mInstance == null)
            mInstance = new AniCreator();

        return mInstance;

    }

    /**
     * ANIMATION_MODE_ROTATION_CLOCKWISED 顺时针旋转
     * ANIMATION_MODE_ROTAION_ANTI_CLOCKWISED 逆时针旋转
     */
    public void apply_animation_Ratation(View view, int ani_mode,
                                         int view_display) {

        if (ani_mode == ANIMATION_MODE_ROTATION_CLOCKWISED) {

            if (mRotationAnimationClockWise == null)
                mRotationAnimationClockWise = new RotateAnimation(0f, 360f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

            mRotationAnimationClockWise.setDuration(DURATION_RATION);
            mRotationAnimationClockWise
                    .setRepeatCount(RotateAnimation.INFINITE);
            mRotationAnimationClockWise.setInterpolator(mLinearInter);
            view.startAnimation(mRotationAnimationClockWise);
            view.setVisibility(view_display);

        }

        if (ani_mode == ANIMATION_MODE_ROTAION_ANTI_CLOCKWISED) {

            if (mRotationAnimationAntiClockWise == null)
                mRotationAnimationAntiClockWise = new RotateAnimation(360f, 0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

            mRotationAnimationAntiClockWise.setDuration(DURATION_RATION);
            mRotationAnimationAntiClockWise
                    .setRepeatCount(RotateAnimation.INFINITE);
            mRotationAnimationClockWise.setInterpolator(mLinearInter);
            view.startAnimation(mRotationAnimationAntiClockWise);
            view.setVisibility(view_display);

        }
    }

    /**
     * ANIMATION_MODE_ALPHA_VISABLE 淡到显示
     * ANIMATION_MODE_ALPHA_INVISABLE 显示到淡
     */
    private void apply_animation_alpha(View view, int ani_mode,
                                       int view_display, boolean isFillAfter) {

        if (ani_mode == ANIMATION_MODE_ALPHA_VISABLE) {

            if (mAlphaAnimationVisable == null)
                mAlphaAnimationVisable = new AlphaAnimation(0, 1);

            mAlphaAnimationVisable.setDuration(DURATION);
            mAlphaAnimationVisable.setFillAfter(isFillAfter);
            // mAlphaAnimationVisable.setInterpolator(mInterpolator_Ace);
            view.startAnimation(mAlphaAnimationVisable);
            view.setVisibility(view_display);

        }

        if (ani_mode == ANIMATION_MODE_ALPHA_INVISABLE) {

            if (mAlphaAnimationInvisable == null)
                mAlphaAnimationInvisable = new AlphaAnimation(1, 0);

            mAlphaAnimationInvisable.setDuration(DURATION);
            mAlphaAnimationInvisable.setFillAfter(isFillAfter);
            // mAlphaAnimationInvisable.setInterpolator(mInterpolator_Dec);
            view.startAnimation(mAlphaAnimationInvisable);
            view.setVisibility(view_display);

        }

    }

    public void apply_animation_scale(View view, int ani_mode,
                                      boolean isFillAfter) {

        if (ani_mode == ANIMATION_MODE_SCALE_TO_SMALL) {


            mScaleAnimationToSmall = new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            mScaleAnimationToSmall.setDuration(100);
            mScaleAnimationToSmall.setFillAfter(isFillAfter);
            // mAlphaAnimationVisable.setInterpolator(mInterpolator_Ace);
            view.startAnimation(mScaleAnimationToSmall);
        }

        if (ani_mode == ANIMATION_MODE_SCALE_TO_BIG) {


            mScaleAnimationToBig = new ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            mScaleAnimationToBig.setDuration(100);
            mScaleAnimationToBig.setFillAfter(isFillAfter);
            // mAlphaAnimationVisable.setInterpolator(mInterpolator_Ace);
            view.startAnimation(mScaleAnimationToBig);

        }

    }

    /**
     * ANIMATION_MODE_DROPDOWN从上出来
     * ANIMATION_MODE_DROPDOWN_RESEVERD从上面消失
     * public static final int ANIMATION_MODE_POPUP = 101;冲下面出来
     * public static final int ANIMATION_MODE_POPUP_RESERVED = 105;从下面消失
     * public static final int ANIMATION_MODE_SILDEOUT_LEFT = 102;左边出来
     * public static final int ANIMATION_MODE_SLIDIN_LEFT = 106;左边消失
     * public static final int ANIMATION_MODE_SLIDEOUT_RIGHT = 103;右边出来
     * public static final int ANIMATION_MODE_SLIDEIN_RIGHT = 107;右边消失
     *
     * @param view
     * @param ani_mode
     * @param view_display
     * @param isFillAfter
     * @param animationListener
     */
    public void apply_animation_translate(View view, int ani_mode, int view_display, boolean isFillAfter, AnimationListener animationListener) {

        switch (ani_mode) {
            case ANIMATION_MODE_DROPDOWN: {

                if (mAnimation_DropDown == null)
                    mAnimation_DropDown = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0f);

                mAnimation_DropDown.setDuration(DURATION);
                mAnimation_DropDown.setFillAfter(isFillAfter);
                mAnimation_DropDown.setInterpolator(mLinearInter);

                if (animationListener != null) {
                    mAnimation_DropDown
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mAnimation_DropDown);
                view.setVisibility(view_display);

                break;
            }

            case ANIMATION_MODE_DROPDOWN_RESEVERD: {

                if (mAnimation_DropDown_Reseverd == null)
                    mAnimation_DropDown_Reseverd = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, -1.0f);

                mAnimation_DropDown_Reseverd.setDuration(DURATION);
                mAnimation_DropDown_Reseverd.setFillAfter(isFillAfter);
                mAnimation_DropDown_Reseverd
                        .setInterpolator(mLinearInter);

                if (animationListener != null) {
                    mAnimation_DropDown_Reseverd
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mAnimation_DropDown_Reseverd);
                view.setVisibility(view_display);
                break;

            }
            case ANIMATION_MODE_DROPDOWN_RESEVERD_PARENT: {

                if (mAnimation_DropDown_Reseverd_Parent == null)
                    mAnimation_DropDown_Reseverd_Parent = new TranslateAnimation(
                            Animation.RELATIVE_TO_PARENT, 0f,
                            Animation.RELATIVE_TO_PARENT, 0f,
                            Animation.RELATIVE_TO_PARENT, 0f,
                            Animation.RELATIVE_TO_PARENT, -1.0f);

                mAnimation_DropDown_Reseverd_Parent.setDuration(DURATION);
                mAnimation_DropDown_Reseverd_Parent.setFillAfter(isFillAfter);
                mAnimation_DropDown_Reseverd_Parent
                        .setInterpolator(mLinearInter);

                if (animationListener != null) {
                    mAnimation_DropDown_Reseverd_Parent
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mAnimation_DropDown_Reseverd_Parent);
                view.setVisibility(view_display);
                break;

            }
            case ANIMATION_MODE_POPUP: {
                if (mAnimation_PopUp == null)
                    mAnimation_PopUp = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0f);

                mAnimation_PopUp.setDuration(DURATION);
                mAnimation_PopUp.setFillAfter(isFillAfter);
                mAnimation_PopUp
                        .setInterpolator(mLinearInter);

                if (animationListener != null) {
                    mAnimation_PopUp
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mAnimation_PopUp);
                view.setVisibility(view_display);

                break;

            }

            case ANIMATION_MODE_POPUP_PARENT: {

                if (mAnimation_PopUp_Parent == null)
                    mAnimation_PopUp_Parent = new TranslateAnimation(
                            Animation.RELATIVE_TO_PARENT, 0f,
                            Animation.RELATIVE_TO_PARENT, 0f,
                            Animation.RELATIVE_TO_PARENT, 1.0f,
                            Animation.RELATIVE_TO_PARENT, 0f);

                mAnimation_PopUp_Parent.setDuration(DURATION);
                mAnimation_PopUp_Parent.setFillAfter(isFillAfter);
                mAnimation_PopUp_Parent
                        .setInterpolator(mLinearInter);

                if (animationListener != null) {
                    mAnimation_PopUp_Parent
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mAnimation_PopUp_Parent);
                view.setVisibility(view_display);

                break;

            }
            case ANIMATION_MODE_POPUP_RESERVED: {

                if (mAnimation_PopUp_Reseverd == null)
                    mAnimation_PopUp_Reseverd = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1.0f);

                mAnimation_PopUp_Reseverd.setDuration(DURATION);
                mAnimation_PopUp_Reseverd.setFillAfter(isFillAfter);
                mAnimation_PopUp_Reseverd.setInterpolator(mInterpolator_Dec);

                if (animationListener != null) {
                    mAnimation_PopUp_Reseverd
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mAnimation_PopUp_Reseverd);
                view.setVisibility(view_display);

                break;
            }

            case ANIMATION_MODE_SILDEOUT_LEFT: {

                if (mTranslateAnimationSlideOut_Left == null)
                    mTranslateAnimationSlideOut_Left = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f);

                mTranslateAnimationSlideOut_Left.setDuration(DURATION);
                mTranslateAnimationSlideOut_Left.setFillAfter(isFillAfter);
                mTranslateAnimationSlideOut_Left.setInterpolator(mInterpolator_Ace);

                if (animationListener != null) {
                    mTranslateAnimationSlideOut_Left
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mTranslateAnimationSlideOut_Left);
                view.setVisibility(view_display);

                break;

            }

            case ANIMATION_MODE_SLIDIN_LEFT: {

                if (mTranslateAnimationSlideIn_Left == null)
                    mTranslateAnimationSlideIn_Left = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f);

                mTranslateAnimationSlideIn_Left.setDuration(DURATION);
                mTranslateAnimationSlideIn_Left.setFillAfter(isFillAfter);
                mTranslateAnimationSlideIn_Left.setInterpolator(mInterpolator_Dec);

                if (animationListener != null) {
                    mTranslateAnimationSlideIn_Left
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mTranslateAnimationSlideIn_Left);
                view.setVisibility(view_display);

                break;

            }

            case ANIMATION_MODE_SLIDEOUT_RIGHT: {

                if (mTranslateAnimationSlideOut_Right == null)
                    mTranslateAnimationSlideOut_Right = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f);

                mTranslateAnimationSlideOut_Right.setDuration(DURATION);
                mTranslateAnimationSlideOut_Right.setFillAfter(isFillAfter);
                mTranslateAnimationSlideOut_Right
                        .setInterpolator(mInterpolator_Ace);

                if (animationListener != null) {
                    mTranslateAnimationSlideOut_Right
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mTranslateAnimationSlideOut_Right);
                view.setVisibility(view_display);

                break;

            }

            case ANIMATION_MODE_SLIDEIN_RIGHT: {

                if (mTranslateAnimationSlideIn_Right == null)
                    mTranslateAnimationSlideIn_Right = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f);

                mTranslateAnimationSlideIn_Right.setDuration(DURATION);
                mTranslateAnimationSlideIn_Right.setFillAfter(isFillAfter);
                mTranslateAnimationSlideIn_Right.setInterpolator(mInterpolator_Dec);

                if (animationListener != null) {
                    mTranslateAnimationSlideIn_Right
                            .setAnimationListener(animationListener);
                }

                view.startAnimation(mTranslateAnimationSlideIn_Right);
                view.setVisibility(view_display);

                break;

            }

        }

    }

}
