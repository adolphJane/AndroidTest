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
    private boolean visible = true;
    private boolean hasHeader = false;
    private boolean hasFooter = false;

    @LayoutRes
    private Integer headerResourceId;
    @LayoutRes
    private Integer footerResourceId;
    @LayoutRes
    private Integer itemResourceId;
    @LayoutRes
    private Integer loadingResourceId;
    @LayoutRes
    private Integer failedResourceId;
    @LayoutRes
    private Integer emptyResourceId;

    public Section(SectionParameters parameters) {
        this.headerResourceId = parameters.headerResourceId;
        this.footerResourceId = parameters.footerResourceId;
        this.itemResourceId = parameters.itemResourceId;
        this.loadingResourceId = parameters.loadingResourceId;
        this.failedResourceId = parameters.failedResourceId;
        this.emptyResourceId = parameters.emptyResourceId;

        this.hasHeader = this.headerResourceId != null;
        this.hasFooter = this.footerResourceId != null;
    }
}
