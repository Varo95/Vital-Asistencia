package com.vitalasistencia.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BUser extends RealmObject {
    private String date;
    private String phone;
    private String email;
    private String address;
    @PrimaryKey
    private String affiliate_number;
    private String image;
    private String dayWeek;
    private boolean food;

    public BUser() {
        this.date = "";
        this.phone = "";
        this.email = "";
        this.address = "";
        this.affiliate_number = "";
        this.image = "";
        this.dayWeek = "";
        this.food=false;
    }

    public BUser(String image, String address, String email,String affiliate_number){
        this.image=image;
        this.address=address;
        this.email=email;
        this.affiliate_number=affiliate_number;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public boolean getFood(){
        return food;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Set the User date
     *
     * @param format date format, like dd/MM/yyyy or some compatible format to date object
     * @param value  the value of the TextInput
     * @return
     */
    public boolean setDate(String format, String value) {
        //v=verified
        boolean v_date = false;
        Date date = null;
        try {
            //Comprobamos la fecha parseándo el String a un date con SimpleDateFormat
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (value.equals(sdf.format(date))) {
                //Si sale bien, insertamos la fecha ya parseada en el string del objeto
                v_date = true;
                this.date = value;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            v_date = false;
        }
        return v_date;
    }

    /**
     * Set the User Phone
     *
     * @param phone
     * @return
     */
    public boolean setPhone(String phone) {
        boolean v_phone = false;
        if (phone.matches("^[+]?[0-9]{10,13}$")) {
            v_phone = true;
            this.phone = phone;
        } else {
            v_phone = false;
        }
        return v_phone;
    }

    /**
     * Set the User Email
     *
     * @param email
     * @return
     */
    public boolean setEmail(String email) {
        //Utilizando el API de javaemail
        boolean v_email = false;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            v_email = true;
            this.email = email;
        } catch (AddressException ex) {
            v_email = false;
        }
        return v_email;
    }

    /**
     * Set the user Address
     *
     * @param address
     * @return
     */
    public boolean setAddress(String address) {
        boolean v_address = false;
        if (address.startsWith("C") || address.startsWith("A")) {
            v_address = true;
            this.address = address;
        }
        return v_address;
    }

    /**
     * Set the User Affiliate Number
     *
     * @param affiliate_number
     * @return
     */
    public boolean setAffiliate_number(String affiliate_number) {
        boolean v_affiliate_number = false;
        if (affiliate_number.matches("^[0-9]{3}+[A-Z]{5}$")) {
            v_affiliate_number = true;
            this.affiliate_number = affiliate_number;
        } else {
            v_affiliate_number = false;
        }
        return v_affiliate_number;
    }

    public boolean setDayWeek(String dayWeek) {
        boolean v_setDayWeek=false;
        this.dayWeek = dayWeek;
        v_setDayWeek=true;
        return v_setDayWeek;
    }

    public void setFood(boolean food){
        this.food=food;
    }

    @Override
    public boolean equals(Object obj){
        boolean result=false;
        if(obj!=null && obj instanceof BUser){
            BUser other=(BUser) obj;
            if(affiliate_number.equals(other.getAffiliate_number())){
                result=true;
            }
        }
        return result;
    }

}
