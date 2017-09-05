package com.haiqi.base.thirdsdk.swipbackhelper;

import android.app.Activity;

import java.util.Stack;

/**
 * 滑动的全局管理类
 */
public class SwipeBackHelper {

//
//    SwipeBackHelper.getCurrentPage(this)//get current instance
//            .setSwipeBackEnable(true)//on-off
//    .setSwipeEdge(200)//set the touch area。200 mean only the left 200px of screen can touch to begin swipe.
//    .setSwipeEdgePercent(0.2f)//0.2 mean left 20% of screen can touch to begin swipe.
//    .setSwipeSensitivity(0.5f)//sensitiveness of the gesture。0:slow  1:sensitive
//    .setScrimColor(Color.BLUE)//color of Scrim below the activity
//    .setClosePercent(0.8f)//close activity when swipe over this
//    .setSwipeRelateEnable(false)//if should move together with the following Activity
//    .setSwipeRelateOffset(500)//the Offset of following Activity when setSwipeRelateEnable(true)
//    .setDisallowInterceptTouchEvent(true)//your view can hand the events first.default false;
//    .addListener(new SwipeListener() {
//
//        @Override
//        public void onScroll(float percent, int px) {
//        }
//
//        @Override
//        public void onEdgeTouch() {
//        }
//
//        @Override
//        public void onScrollToClose() {
//        }
//    });


    private static final Stack<SwipeBackPage> mPageStack = new Stack<>();

    private static SwipeBackPage findHelperByActivity(Activity activity){
        for (SwipeBackPage swipeBackPage : mPageStack) {
            if (swipeBackPage.mActivity == activity) return swipeBackPage;
        }
        return null;
    }

    public static SwipeBackPage getCurrentPage(Activity activity){
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        return page;
    }

    public static void onCreate(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            page = mPageStack.push(new SwipeBackPage(activity));
        }
        page.onCreate();
    }

    public static void onPostCreate(Activity activity){
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        page.onPostCreate();
    }

    public static void onDestroy(Activity activity){
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        mPageStack.remove(page);
        page.mActivity=null;
    }

    public static void finish(Activity activity){
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        page.scrollToFinishActivity();
    }

    static SwipeBackPage getPrePage(SwipeBackPage activity){
        int index = mPageStack.indexOf(activity);
        if (index>0)return mPageStack.get(index-1);
        else return null;
    }

}
