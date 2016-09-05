package com.creation.haasith.easycoupon.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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



    public Store(String name, String address, String phoneNumber, String email, ArrayList<Coupon> coupons)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.coupons = coupons;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("address", address);
        result.put("phoneNumber", phoneNumber);
        result.put("email", email);
        result.put("coupons", coupons);

        return result;
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
