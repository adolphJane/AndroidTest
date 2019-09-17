package com.magicalrice.base_libs.bean;

/**
 * Created by Adolph on 2018/3/26.
 */

public class AndroidTestInfo {
    private String name;
    private String content;
    private String path;

    public AndroidTestInfo(String name, String content, String path) {
        this.name = name;
        this.content = content;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
