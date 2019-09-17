package com.magicalrice.view.recyclerview.typeOne;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Adolph on 2018/1/18.
 */

public abstract class Section {

    public enum State {
        LOADING, LOADED, FAILED, EMPTY
    }

    private State state = State.LOADED;
    private boolean visible = true;
    private boolean hasHeader = false;
    private boolean hasFooter = false;

    @LayoutRes private Integer headerResourceId;
    @LayoutRes
    private Integer footerResourceId;
    @LayoutRes private Integer itemResourceId;
    @LayoutRes private Integer loadingResourceId;
    @LayoutRes private Integer failedResourceId;
    @LayoutRes private Integer emptyResourceId;

    private final boolean itemViewWillBeProvided;
    private final boolean headerViewWillBeProvided;
    private final boolean footerViewWillBeProvided;
    private final boolean loadingViewWillBeProvided;
    private final boolean failedViewWillBeProvided;
    private final boolean emptyViewWillBeProvided;

    public Section(SectionParameters parameters) {
        this.headerResourceId = parameters.headerResourceId;
        this.footerResourceId = parameters.footerResourceId;
        this.itemResourceId = parameters.itemResourceId;
        this.loadingResourceId = parameters.loadingResourceId;
        this.failedResourceId = parameters.failedResourceId;
        this.emptyResourceId = parameters.emptyResourceId;

        this.itemViewWillBeProvided = parameters.itemViewWillBeProvided;
        this.headerViewWillBeProvided = parameters.headerViewWillBeProvided;
        this.footerViewWillBeProvided = parameters.footerViewWillBeProvided;
        this.loadingViewWillBeProvided = parameters.loadingViewWillBeProvided;
        this.failedViewWillBeProvided = parameters.failedViewWillBeProvided;
        this.emptyViewWillBeProvided = parameters.emptyViewWillBeProvided;

        this.hasHeader = (this.headerResourceId != null) || this.headerViewWillBeProvided;
        this.hasFooter = (this.footerResourceId != null) || this.footerViewWillBeProvided;
    }

    /**
     * Set the State of this Section
     *
     * @param state state of this section
     */
    public final void setState(State state) {
        switch (state) {
            case LOADING:
                if (loadingResourceId == null && !loadingViewWillBeProvided) {
                    throw new IllegalStateException("Resource id for 'loading state' should be provided or 'loadingViewWillBeProvided' should be set");
                }
                break;
            case FAILED:
                if (failedResourceId == null && !failedViewWillBeProvided) {
                    throw new IllegalStateException("Resource id for 'failed state' should be provided or 'failedViewWillBeProvided' should be set");
                }
                break;
            case EMPTY:
                if (emptyResourceId == null && !emptyViewWillBeProvided) {
                    throw new IllegalStateException("Resource id for 'empty state' should be provided or 'emptyViewWillBeProvided' should be set");
                }
                break;
        }
        this.state = state;
    }

    public final State getState() {
        return state;
    }

    public final boolean isVisible() {
        return visible;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final boolean hasHeader() {
        return hasHeader;
    }

    public final void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public final boolean hasFooter() {
        return hasFooter;
    }

    public final void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    public final Integer getHeaderResourceId() {
        return headerResourceId;
    }

    public Integer getFooterResourceId() {
        return footerResourceId;
    }

    public Integer getItemResourceId() {
        return itemResourceId;
    }

    public Integer getLoadingResourceId() {
        return loadingResourceId;
    }

    public Integer getFailedResourceId() {
        return failedResourceId;
    }

    public Integer getEmptyResourceId() {
        return emptyResourceId;
    }

    public boolean isItemViewWillBeProvided() {
        return itemViewWillBeProvided;
    }

    public boolean isHeaderViewWillBeProvided() {
        return headerViewWillBeProvided;
    }

    public boolean isFooterViewWillBeProvided() {
        return footerViewWillBeProvided;
    }

    public boolean isLoadingViewWillBeProvided() {
        return loadingViewWillBeProvided;
    }

    public boolean isFailedViewWillBeProvided() {
        return failedViewWillBeProvided;
    }

    public boolean isEmptyViewWillBeProvided() {
        return emptyViewWillBeProvided;
    }

    /**
     * Bind the data to the ViewHolder for the Content of this Section, that can be the Items,
     * Loading view or Failed view, depending on the current state of the section
     *
     * @param holder   ViewHolder for the Content of this Section
     * @param position position of the item in the Section, not in the RecyclerView
     */
    public final void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (state) {
            case LOADING:
                onBindLoadingViewHolder(holder);
                break;
            case EMPTY:
                onBindEmptyViewHolder(holder);
                break;
            case LOADED:
                onBindItemViewHolder(holder, position);
                break;
            case FAILED:
                onBindFailedViewHolder(holder);
                break;
            default:
                throw new IllegalStateException("Invalid State");
        }
    }

    /**
     * Return the total of items of this Section, including content items (according to the section
     * state) plus header and footer
     *
     * @return total of items of this section
     */
    public final int getSectionItemsTotal() {
        int contentItemsTotal;

        switch (state) {
            case LOADING:
                contentItemsTotal = 1;
                break;
            case LOADED:
                contentItemsTotal = getContentItemsTotal();
                break;
            case FAILED:
                contentItemsTotal = 1;
                break;
            case EMPTY:
                contentItemsTotal = 1;
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }

        return contentItemsTotal + (hasHeader ? 1 : 0) + (hasFooter ? 1 : 0);
    }

    /**
     * Return the total of items of this Section
     *
     * @return total of items of this Section
     */
    public abstract int getContentItemsTotal();

    /**
     * Return the ViewHolder for the Header of this Section
     *
     * @param view View inflated by resource returned by getHeaderResourceId
     * @return ViewHolder for the Header of this Section
     */
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    /**
     * Bind the data to the ViewHolder for the Header of this Section
     *
     * @param holder ViewHolder for the Header of this Section
     */
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

    }

    /**
     * Return the ViewHolder for the Footer of this Section
     *
     * @param view View inflated by resource returned by getFooterResourceId
     * @return ViewHolder for the Footer of this Section
     */
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    /**
     * Bind the data to the ViewHolder for the Footer of this Section
     *
     * @param holder ViewHolder for the Footer of this Section
     */
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {

    }

    public View getItemView(ViewGroup group) {
        throw new UnsupportedOperationException("You need to implement getItemView() if you set itemViewWillBeProvided.");
    }

    /**
     * Return the ViewHolder for a single Item of this Section
     *
     * @param view View inflated by resource returned by getItemResourceId
     * @return ViewHolder for the Item of this Section
     */
    public abstract RecyclerView.ViewHolder getItemViewHolder(View view);

    /**
     * Bind the data to the ViewHolder for an Item of this Section
     *
     * @param holder   ViewHolder for the Item of this Section
     * @param position position of the item in the Section, not in the RecyclerView
     */
    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    /**
     * Return the ViewHolder for the Loading state of this Section
     *
     * @param view View inflated by resource returned by getItemResourceId
     * @return ViewHolder for the Loading state of this Section
     */
    public RecyclerView.ViewHolder getLoadingViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    /**
     * Bind the data to the ViewHolder for Loading state of this Section
     *
     * @param holder ViewHolder for the Loading state of this Section
     */
    public void onBindLoadingViewHolder(RecyclerView.ViewHolder holder) {

    }

    /**
     * Return the ViewHolder for the Failed state of this Section
     *
     * @param view View inflated by resource returned by getItemResourceId
     * @return ViewHolder for the Failed of this Section
     */
    public RecyclerView.ViewHolder getFailedViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    /**
     * Bind the data to the ViewHolder for the Failed state of this Section
     *
     * @param holder ViewHolder for the Failed state of this Section
     */
    public void onBindFailedViewHolder(RecyclerView.ViewHolder holder) {

    }

    /**
     * Return the ViewHolder for the Empty state of this Section
     *
     * @param view View inflated by resource returned by getItemResourceId
     * @return ViewHolder for the Empty of this Section
     */
    public RecyclerView.ViewHolder getEmptyViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    /**
     * Bind the data to the ViewHolder for the Empty state of this Section
     *
     * @param holder ViewHolder for the Empty state of this Section
     */
    public void onBindEmptyViewHolder(RecyclerView.ViewHolder holder) {

    }
}
