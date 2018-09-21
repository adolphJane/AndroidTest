package com.magicalrice.adolph.custom_widget.recyclerview.item_decoration;

public class SectionInfo {
    private int mSectionId;
    private String mTitle;
    private int mPosition;
    private int mGroupLength;

    public SectionInfo(int groupId,String title) {
        this.mSectionId = groupId;
        this.mTitle = title;
    }

    public int getSectionId() {
        return mSectionId;
    }

    public void setSectionId(int mSectionId) {
        this.mSectionId = mSectionId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public boolean isFirstViewInGroup() {
        return mPosition == 0;
    }

    public boolean isLastViewInGroup() {
        return mPosition == mGroupLength - 1 && mPosition >= 0;
    }

    public void setGroupLength(int mGroupLength) {
        this.mGroupLength = mGroupLength;
    }
}
