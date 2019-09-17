package com.magicalrice.common.router;

/**
 * Created by Adolph on 2018/2/8.
 */

public class RouterTable {
    //组
    public static final String GROUP_VIEW = "/view";
    public static final String GROUP_ANIMATION = "/animation";
    public static final String GROUP_LIBRARY_STUDY = "/libraryStudy";
    public static final String GROUP_GAME = "/game";
    public static final String GROUP_DOC_WEB =  "/web";

    //View
    public static final String ITEM_VIEW_DRAGFLOAT = GROUP_VIEW + "/dragfloat";
    public static final String ITEM_VIEW_NOTIFICATION = GROUP_VIEW + "/notification";
    public static final String ITEM_VIEW_KEYBOARD = GROUP_VIEW + "/keyboard";
    public static final String ITEM_VIEW_RECYCLERVIEW = GROUP_VIEW + "/recyclerview";
    public static final String ITEM_VIEW_RECYCLERVIEW_ITEMDECORATION = GROUP_VIEW + "/recyclerview/itemdecoration";
    public static final String ITEM_VIEW_PROGRESS_BAR = GROUP_VIEW + "/progressbar";
    public static final String ITEM_VIEW_HORIZONTAL_SCROLL_TAG = GROUP_VIEW + "/scrolltag";
    public static final String ITEM_VIEW_DATA_GRAPH = GROUP_VIEW + "/datagraph";
    //Animation
    public static final String ITEM_ANIMARION_LAYOUT_TRANSITION = GROUP_ANIMATION + "/layouttransition";
    //LibraryStudy
    public static final String ITEM_LIBRARY_STUDY_APPBARLAYOUT = GROUP_LIBRARY_STUDY + "/appbarlayout";
    //Game
    public static final String ITEM_GAME_SNAKE = GROUP_GAME + "/snake";
    //WebPath
    public static final String ITEM_WEB_DOC = GROUP_DOC_WEB + "/doc";

    public static String WEB_PATH_LAYOUTTRANSITION = "https://blog.csdn.net/u010142437/article/details/53819573";
}
