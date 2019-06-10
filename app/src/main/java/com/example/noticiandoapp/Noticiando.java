package com.example.noticiandoapp;

public class Noticiando {

    private long mTimeInMilliseconds;
    private String mNewsHeadline;
    private String mSectionName;
    private String mUrl;


    public Noticiando(Long timeInMilliseconds, String newsHeadline, String sectionName, String url) {
        mTimeInMilliseconds = timeInMilliseconds;
        mNewsHeadline = newsHeadline;
        mSectionName = sectionName;
        mUrl = url;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getNewsHeadline() {
        return mNewsHeadline;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getUrl() {
        return mUrl;
    }

}
