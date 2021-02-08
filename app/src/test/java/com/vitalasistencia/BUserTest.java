package com.vitalasistencia;

import com.vitalasistencia.models.BUser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BUserTest {
    private BUser user;

    @Before
    public void setUp(){
        this.user=new BUser();
    }

    @Test
    public void BUser_date_isCorrect(){
        assertTrue(this.user.setDate("dd/MM/yyyy", "04/02/2021"));
        assertEquals("04/02/2021", this.user.getDate());
        assertFalse(this.user.setDate("dd/mm/YYYY", "4/02/2021"));
        assertEquals("04/02/2021",this.user.getDate());
    }

    @Test
    public void BUser_phone_isCorrect(){
        assertTrue(this.user.setPhone("1234567890"));
        assertEquals("1234567890", this.user.getPhone());
        assertFalse(this.user.setPhone("12345"));
        assertEquals("1234567890",this.user.getPhone());
    }

    @Test
    public void BUser_email_isCorrect(){
        assertTrue(this.user.setEmail("a@1.com"));
        assertEquals("a@1.com", this.user.getEmail());
        assertFalse(this.user.setEmail("Esto no es un email.com"));
        assertEquals("a@1.com",this.user.getEmail());
        assertTrue(this.user.setEmail("a@1"));
        assertEquals("a@1", this.user.getEmail());
    }

    @Test
    public void BUser_address_isCorrect(){
        assertTrue(this.user.setAddress("C/ Real Dealer"));
        assertEquals("C/ Real Dealer", this.user.getAddress());
        assertFalse(this.user.setAddress("Unexpected } on line 32"));
        assertEquals("C/ Real Dealer",this.user.getAddress());
    }

    @Test
    public void BUser_affiliateNumber_isCorrect(){
        assertTrue(this.user.setAffiliate_number("111AAAAA"));
        assertEquals("111AAAAA", this.user.getAffiliate_number());
        assertFalse(this.user.setAffiliate_number("7654KISESTOP?"));
        assertEquals("111AAAAA", this.user.getAffiliate_number());
    }

    @Test
    public void BUser_equals_isCorrect(){
        BUser temp=new BUser();
        temp.setAffiliate_number("123AAAAA");
        this.user.setAffiliate_number("123AAAAA");
        assertEquals(this.user, temp);
        temp.setAffiliate_number("123AAAAA");
        this.user.setAffiliate_number("123AAAAE");
        assertNotEquals(this.user, temp);
    }
}
