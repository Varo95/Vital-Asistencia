package com.vitalasistencia;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vitalasistencia.models.BUser;
import com.vitalasistencia.models.MUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MUserTest {
    private MUser mUser;
    private BUser test;
    private BUser test1;
    private BUser removeMe;

    @Before
    public void setUp(){
        Realm realm = Realm.getDefaultInstance();
        realm.close();
        Realm.deleteRealm(realm.getConfiguration());
        mUser = new MUser();
        test = new BUser();
        test.setDate("dd/MM/yyyy", "04/02/2021");
        test.setPhone("1234567890");
        test.setDayWeek("Martes");
        test.setEmail("instrumentada@tapocho.com");
        test.setAddress("C/ Test");
        test.setAffiliate_number("123FFFFF");
        test1 = new BUser();
        test1.setDate("dd/MM/yyyy", "04/02/2021");
        test1.setPhone("0034987654321");
        test1.setDayWeek("Lunes");
        test1.setEmail("notengodeesto@gmail.com");
        test1.setAddress("C/ Test");
        test1.setAffiliate_number("555JJJJJ");
        removeMe = new BUser();
        removeMe.setDate("dd/MM/yyyy", "04/02/2023");
        removeMe.setPhone("8976543290");
        removeMe.setDayWeek("Miercoles");
        removeMe.setEmail("dontcry@deleted.com");
        removeMe.setAddress("C/ Remove");
        removeMe.setAffiliate_number("765HJKLI");
    }


    public void MUser_insert_isCorrect(){
        //Como el metodo existUser se usa dentro de este método no te he puesto el test de existUser
        assertTrue(mUser.insertUser(test));
        //Por lo tanto comprobamos aquí que no puede meter el mismo usuario
        assertFalse(mUser.insertUser(test));
        assertTrue(mUser.insertUser(test1));
        assertFalse(mUser.insertUser(test1));
        assertTrue(mUser.insertUser(removeMe));
        assertFalse(mUser.insertUser(removeMe));
    }

    @Test
    public void MUser_update_isCorrect(){
        test.setAddress("Av Test1");
        test1.setAddress("Avenida tolai");
        assertTrue(mUser.updateUser(test));
        assertTrue(mUser.updateUser(test1));
        test1.setDayWeek("Martes");
        //Al borrarlo no debería de encontrarlo, por lo tanto devolvería un false a la hora de intentar actualizar.
        mUser.removeUser(test1.getAffiliate_number());
        assertFalse(mUser.updateUser(test1));
    }

    @Test
    public void MUser_remove_isCorrect(){
        assertTrue(mUser.removeUser(removeMe.getAffiliate_number()));
        assertFalse(mUser.removeUser(removeMe.getAffiliate_number()));
    }

    @Test
    public void MUser_searchUser_isCorrect(){
        assertEquals(test , mUser.searchUser("123FFFFF"));
        assertEquals(null , mUser.searchUser("1"));
    }

    @Test
    public void MUser_UsersQuerys_isCorrect(){
        assertEquals(test , mUser.UsersQuerys("C/ Test","Martes","04/02/2021").get(0));
    }

    @Test
    public void MUser_getAllSumary_isCorrect(){
        assertEquals(test1 , mUser.getAllSumary().get(0));
    }

    @Test
    public void MUser_getSpinner_isCorrect(){
        assertEquals("", mUser.getSpinner().get(0));
    }

}
