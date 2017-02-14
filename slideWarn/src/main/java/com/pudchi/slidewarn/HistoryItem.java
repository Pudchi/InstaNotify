package com.pudchi.slidewarn;

public class HistoryItem
{
    public int drawableId;
    public int readflag;
    public int emergencylevel;
    public String time;
    public String header;
    public String point;


    public int getDrawable()
    {
        return drawableId;
    }

    public void setDrawable(int drawableId)
    {
        this.drawableId = drawableId;

    }

    public int getReadflag()
    {
        return readflag;
    }

    public void setReadflag(int readflag)
    {
        this.readflag = readflag;
    }

    public int getEmergencylevel()
    {
        return emergencylevel;
    }

    public void setEmergencylevel(int emergencylevel)
    {
        this.emergencylevel = emergencylevel;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String date)
    {
        this.time = date;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getPoint()
    {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

}
