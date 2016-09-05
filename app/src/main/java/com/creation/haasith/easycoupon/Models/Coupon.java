package com.creation.haasith.easycoupon.Models;

/**
 * Created by gangasanka on 9/2/16.
 */
public class Coupon
{
    private String name;
    private String description;
    private String start;
    private String end;


    public Coupon(String name, String description, String start, String end)
    {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public Coupon()
    {

    }

    public String getEnd()
    {

        return end;
    }

    public String getStart()
    {
        return start;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }





}
