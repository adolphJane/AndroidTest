package com.magicalrice.adolph.androidtest.test_demo.custom_view.tool_custom_view.recyclerview;

import android.support.annotation.LayoutRes;

/**
 * Created by Adolph on 2018/1/18.
 */

public class Section {

    public enum State {
        LOADING, LOADED, FAILED, EMPTY
    }

    private State state = State.LOADED;
    boolean visible = true;
    boolean hasHeader = false;
    boolean hasFolder = false;

    @LayoutRes
    Integer headerResourceId;
    @LayoutRes
    Integer footerResourceId;
    @LayoutRes
    Integer itemResourceId;
    @LayoutRes
    private Integer loadingResourceId;
    @LayoutRes
    private Integer failedResourceId;
    @LayoutRes
    private Integer emptyResourceId;

    public Section() {

    }
}
