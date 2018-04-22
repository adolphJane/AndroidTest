package com.magicalrice.adolph.custom_widget.recyclerview.typeOne;

import android.support.annotation.LayoutRes;

/**
 * Created by Adolph on 2018/1/18.
 */

public class SectionParameters {
    @LayoutRes public Integer headerResourceId;
    @LayoutRes public Integer footerResourceId;
    @LayoutRes public Integer itemResourceId;
    @LayoutRes public Integer loadingResourceId;
    @LayoutRes public Integer failedResourceId;
    @LayoutRes public Integer emptyResourceId;

    public final boolean itemViewWillBeProvided;
    public final boolean headerViewWillBeProvided;
    public final boolean footerViewWillBeProvided;
    public final boolean loadingViewWillBeProvided;
    public final boolean failedViewWillBeProvided;
    public final boolean emptyViewWillBeProvided;

    private SectionParameters(Builder builder) {
        this.headerResourceId = builder.headerResourceId;
        this.footerResourceId = builder.footerResourceId;
        this.itemResourceId = builder.itemResourceId;
        this.loadingResourceId = builder.loadingResourceId;
        this.failedResourceId = builder.failedResourceId;
        this.emptyResourceId = builder.emptyResourceId;

        this.itemViewWillBeProvided = builder.itemViewWillBeProvided;
        this.headerViewWillBeProvided = builder.headerViewWillBeProvided;
        this.footerViewWillBeProvided = builder.footerViewWillBeProvided;
        this.loadingViewWillBeProvided = builder.loadingViewWillBeProvided;
        this.failedViewWillBeProvided = builder.failedViewWillBeProvided;
        this.emptyViewWillBeProvided = builder.emptyViewWillBeProvided;

        if (itemResourceId != null && itemViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided can not both be set");
        } else if (itemResourceId != null && !itemViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId or itemViewWillBeProvided must be set");
        }

        if (headerResourceId != null && headerViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided can not both be set");
        }

        if (footerResourceId != null && footerViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided can not both be set");
        }

        if (loadingResourceId != null && loadingViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided can not both be set");
        }

        if (failedResourceId != null && failedViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided can not both be set");
        }

        if (emptyResourceId != null && emptyViewWillBeProvided) {
            throw new IllegalArgumentException("itemResourceId and itemViewWillBeProvided can not both be set");
        }
    }

    public static class Builder {
        @LayoutRes private Integer itemResourceId;
        @LayoutRes private Integer headerResourceId;
        @LayoutRes private Integer footerResourceId;
        @LayoutRes private Integer loadingResourceId;
        @LayoutRes private Integer failedResourceId;
        @LayoutRes private Integer emptyResourceId;

        private boolean itemViewWillBeProvided;
        private boolean headerViewWillBeProvided;
        private boolean footerViewWillBeProvided;
        private boolean loadingViewWillBeProvided;
        private boolean failedViewWillBeProvided;
        private boolean emptyViewWillBeProvided;

        private Builder() {}

        public Builder itemResourceId(@LayoutRes int itemResourceId) {
            this.itemResourceId = itemResourceId;
            return this;
        }

        public Builder itemViewWillBeProvided() {
            itemViewWillBeProvided = true;
            return this;
        }

        public Builder headerResourceId(@LayoutRes int headerResourceId) {
            this.headerResourceId = headerResourceId;
            return this;
        }

        public Builder headerViewWillBeProvided() {
            headerViewWillBeProvided = true;
            return this;
        }

        public Builder footerResourceId(@LayoutRes int footerResourceId) {
            this.footerResourceId = footerResourceId;
            return this;
        }

        public Builder footerViewWillBeProvided() {
            this.footerViewWillBeProvided = footerViewWillBeProvided;
            return this;
        }

        public Builder loadingResourceId(@LayoutRes int loadingResourceId) {
            this.loadingResourceId = loadingResourceId;
            return this;
        }

        public Builder loadingViewWillBeProvided() {
            this.loadingViewWillBeProvided = loadingViewWillBeProvided;
            return this;
        }

        public Builder failedResourceId(@LayoutRes int failedResourceId) {
            this.failedResourceId = failedResourceId;
            return this;
        }

        public Builder failedViewWillBeProvided() {
            this.failedViewWillBeProvided = failedViewWillBeProvided;
            return this;
        }

        public Builder emptyResourceId(@LayoutRes int emptyResourceId) {
            this.emptyResourceId = emptyResourceId;
            return this;
        }

        public Builder emptyViewWillBeProvided() {
            this.emptyViewWillBeProvided = emptyViewWillBeProvided;
            return this;
        }

        public SectionParameters build() {
            return new SectionParameters(this);
        }
    }
}
