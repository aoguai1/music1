package com.example.szq.music1.domain;

/**
 * Created by szq on 18-9-29.
 * 作用：歌词类
 */

public class Lyric {
    /**
     * 歌词内容
     */
    private String content;

    /**
     * 时间戳
     */
    private Long timePoint;

    /**
     * 休眠时间或者高亮显示的时间
     */
    private Long sleepTime;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Long timePoint) {
        this.timePoint = timePoint;
    }

    public Long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }


    @Override
    public String toString() {
        return "Lyric{" +
                "content='" + content + '\'' +
                ", timePoint=" + timePoint +
                ", sleepTime=" + sleepTime +
                '}';
    }
}
