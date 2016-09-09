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
    private String image;

    public String getImage()
    {
        return image;
    }

    public Coupon(String name, String description, String start, String end, String image)
    {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.image = image;
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
