package com.vitalasistencia.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * Set Date of the Object BUser
     * @param format date format, like dd/MM/yyyy or some compatible format to date object
     * @param value the value that user input
     * @return
     */
    public boolean setDate(String format, String value){
        //v=verified
        boolean v_date=false;
        Date date=null;
        try {
            //Comprobamos la fecha parseándo el String a un date con SimpleDateFormat
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (value.equals(sdf.format(date))) {
                //Si sale bien, insertamos la fecha ya parseada en el string del objeto
                this.date = String.valueOf(date);
                v_date=true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            v_date=false;
        }
        return v_date;
    }

    public boolean setPhone(String phone){
        boolean v_phone=false;
        if(phone.matches("^[+]?[0-9]{10,13}$")){
            v_phone=true;
            this.phone=phone;
        }else{
            v_phone=false;
        }
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
