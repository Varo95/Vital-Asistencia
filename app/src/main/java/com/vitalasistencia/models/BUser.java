package com.vitalasistencia.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BUser {
    private String date;
    private String phone;
    private String email;
    private String address;
    private String affiliate_number;

    public BUser() {
        this.date="";
        this.phone="";
        this.email="";
        this.address="";
        this.affiliate_number="";
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getAffiliate_number() {
        return affiliate_number;
    }

    public boolean setDate(String date1){
        //v=verified
        boolean v_date=false;
        if (date1 == null){
            v_date=false;
        } else if(!date1.matches("^([0-2][0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})$")) {
            v_date = true;
        }
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date1);
            this.date=date1;
            v_date=true;
        }catch (ParseException e){
            v_date=false;
        }
        return v_date;
    }

    public boolean setPhone(String phone){
        boolean v_phone=false;
        this.phone=phone;
        return v_phone;
    }

    public boolean setEmail(String email){
        boolean v_email=false;

        return v_email;
    }

    public boolean setAddress(String address){
        boolean v_address=false;

        return v_address;
    }

    public boolean setAffiliate_number(String affiliate_number){
        boolean v_affiliate_number=false;

        return v_affiliate_number;
    }

/*    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.startsWith("a")) {
            this.name = name;
            return true;
        } else {
            return false;
        }
    }*/
}
