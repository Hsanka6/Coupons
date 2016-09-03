package com.creation.haasith.easycoupon;

import java.util.ArrayList;

/**
 * Created by gangasanka on 9/2/16.
 */
public class Store
{
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private ArrayList<Coupon> coupons;


    public ArrayList<Coupon> getCoupons()
    {
        return coupons;
    }


    public Store()
    {
    }

    public Store(String name, String address, String phoneNumber, String email)
    {

        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }
}
