package com.magicalrice.adolph.androidtest.test_demo.custom_view.tool_custom_view.recyclerview;

import android.support.annotation.LayoutRes;

/**
 * Created by Adolph on 2018/1/18.
 */

public class SectionParameters {
    @LayoutRes
    public Integer headerResourceId;
    @LayoutRes
    public Integer footerResourceId;
    @LayoutRes
    public Integer itemResourceId;
    @LayoutRes
    public Integer loadingResourceId;
    @LayoutRes
    public Integer failedResourceId;
    @LayoutRes
    public Integer emptyResourceId;

    public static class Builder {
        private final int itemResourceId;
        @LayoutRes
        private Integer headerResourceId;
        @LayoutRes
        private Integer footerResourceId;
        @LayoutRes
        private Integer loadingResourceId;
        @LayoutRes
        private Integer failedResourceId;
        @LayoutRes
        private Integer emptyResourceId;

        public Builder(@LayoutRes int itemResourceId) {
            this.itemResourceId = itemResourceId;
        }

        /**
         * Set layout resource for Section's header
         *
         * @param headerResourceId layout resource for Section's header
         * @return this builder
         */
        public Builder headerResourceId(@LayoutRes int headerResourceId) {
            this.headerResourceId = headerResourceId;
            return this;
        }

        /**
         * Set layout resource for Section's footer
         *
         * @param footerResourceId layout resource for Section's footer
         * @return this builder
         */
        public Builder footerResourceId(@LayoutRes int footerResourceId) {
            this.footerResourceId = footerResourceId;
            return this;
        }

        /**
         * Set layout resource for Section's loading
         *
         * @param loadingResourceId layout resource for Section's loading
         * @return this builder
         */
        public Builder loadingResourceId(@LayoutRes int loadingResourceId) {
            this.loadingResourceId = loadingResourceId;
            return this;
        }
    }
}
