package com.nithinkumar.zumpersolution.Model;

import java.util.List;

/**
 * Created by Nithin on 10/31/2017.
 */

public class Photo{
    private String height;
    private String width;
    private String reference;
    private List<String> htmlAttributions;

    public Photo(String height, String width, String reference, List<String> htmlAttributions) {
        this.height = height;
        this.width = width;
        this.reference = reference;
        this.htmlAttributions = htmlAttributions;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getReference() {
        return reference;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }
}
