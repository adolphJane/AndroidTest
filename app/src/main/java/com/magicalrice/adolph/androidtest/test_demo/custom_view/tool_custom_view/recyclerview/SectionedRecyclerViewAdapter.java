package com.magicalrice.adolph.androidtest.test_demo.custom_view.tool_custom_view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.NoRouteToHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Adolph on 2018/1/25.
 */

public class SectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int VIEW_TYPE_HEADER = 0;
    public final static int VIEW_TYPE_FOOTER = 1;
    public final static int VIEW_TYPE_ITEM_LOADED = 2;
    public final static int VIEW_TYPE_LOADING = 3;
    public final static int VIEW_TYPE_FAILED = 4;
    public final static int VIEW_TYPE_EMPTY = 5;

    private LinkedHashMap<String, Section> sections;
    private HashMap<String, Integer> sectionViewTypeNumbers;
    private int viewTypeCount = 0;
    private final static int VIEW_TYPE_QTY = 6;

    public SectionedRecyclerViewAdapter() {
        sections = new LinkedHashMap<>();
        sectionViewTypeNumbers = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        for (Map.Entry<String, Integer> entry : sectionViewTypeNumbers.entrySet()) {
            if (viewType >= entry.getValue() && viewType < entry.getValue() + VIEW_TYPE_QTY) {
                Section section = sections.get(entry.getKey());
                int sectionViewType = viewType - entry.getValue();

                switch (sectionViewType) {
                    case VIEW_TYPE_HEADER:
                        break;
                    case VIEW_TYPE_FOOTER:
                        break;
                    case VIEW_TYPE_ITEM_LOADED:
                        break;
                    case VIEW_TYPE_LOADING:
                        break;
                    case VIEW_TYPE_FAILED:
                        break;
                    case VIEW_TYPE_EMPTY:
                        break;
                }
            }
        }
        return viewHolder;
    }

    private RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, Section section) {
        View view = LayoutInflater.from(parent.getContext()).inflate(section.getItemResourceId(), parent, false);
        return section.getItemViewHolder(view);
    }

    private RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent, Section section) {
        Integer resId = section.getHeaderResourceId();
        if (resId == null)
            throw new NullPointerException("Missing 'header state' resource id");
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return section.getHeaderViewHolder(view);
    }

    private RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent, Section section) {
        Integer resId = section.getFooterResourceId();
        if (resId == null)
            throw new NullPointerException("Missing 'footer state' resource id");
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return section.getFooterViewHolder(view);
    }

    private RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent, Section section) {
        Integer resId = section.getLoadingResourceId();
        if (resId == null)
            throw new NullPointerException("Missing 'loading state' resource id");
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return section.getLoadingViewHolder(view);
    }

    private RecyclerView.ViewHolder getFailedViewHolder(ViewGroup parent, Section section) {
        Integer resId = section.getFailedResourceId();
        if (resId == null)
            throw new NullPointerException("Missing 'failed state' resource id");
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return section.getFailedViewHolder(view);
    }

    private RecyclerView.ViewHolder getEmptyViewHolder(ViewGroup parent, Section section) {
        Integer resId = section.getEmptyResourceId();
        if (resId == null)
            throw new NullPointerException("Missing 'empty state' resource id");
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return section.getEmptyViewHolder(view);
    }

    /**
     * Add a section to this recyclerview.
     *
     * @param tag     unique identifier of the section
     * @param section section to be added
     */
    public void addSection(String tag, Section section) {
        this.sections.put(tag, section);
        this.sectionViewTypeNumbers.put(tag, viewTypeCount);
        viewTypeCount += VIEW_TYPE_QTY;
    }

    /**
     * Add a section to this recyclerview with a random tag;
     *
     * @param section section to be added
     * @return generated tag
     */
    public String addSection(Section section) {
        String tag = UUID.randomUUID().toString();
        addSection(tag, section);
        return tag;
    }

    /**
     * Return the section with the tag provided.
     *
     * @param tag unique identifier of the section
     * @return section
     */
    public Section getSection(String tag) {
        return this.sections.get(tag);
    }

    /**
     * Remove section from this recyclerview.
     *
     * @param tag unique identifier of the section
     */
    public void removeSection(String tag) {
        this.sections.remove(tag);
    }

    /**
     * Remove all sections from this recyclerview.
     */
    public void removeAllSections() {
        this.sections.clear();
    }

    @Override
    public int getItemViewType(int position) {
         /*
         Each Section has 6 "viewtypes":
         1) header
         2) footer
         3) items
         4) loading
         5) load failed
         6) empty
         */

        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();
            if (!section.isVisible())
                continue;
            int sectionTotal = section.getSectionItemsTotal();
            if (position >= currentPos && position <= (currentPos + sectionTotal - 1)) {
                int viewType = sectionViewTypeNumbers.get(entry.getKey());
                if (section.hasHeader()) {
                    if (position == currentPos) {
                        return viewType;
                    }
                }

                if (section.hasFooter()) {
                    if (position == (currentPos + sectionTotal - 1)) {
                        return viewType + 1;
                    }
                }

                switch (section.getState()) {
                    case LOADED:
                        return viewType + 2;
                    case LOADING:
                        return viewType + 3;
                    case FAILED:
                        return viewType + 4;
                    case EMPTY:
                        return viewType + 5;
                    default:
                        throw new IllegalStateException("Invalid State");
                }
            }
            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid Position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();
            if (!section.isVisible())
                continue;

            int sectionTotal = section.getSectionItemsTotal();

            //check if position is in this section
            if (position >= currentPos && position <= (currentPos + sectionTotal - 1)) {
                if (section.hasHeader()) {
                    if (position == currentPos) {
                        return;
                    }
                }

                if (section.hasFooter()) {
                    if (position == (currentPos + sectionTotal - 1)) {
                        return;
                    }
                }

                return;
            }
            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position");
    }

    @Override
    public int getItemCount() {
        int count = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();
            if (!section.isVisible())
                continue;
            count += section.getSectionItemsTotal();
        }
        return count;
    }
}
