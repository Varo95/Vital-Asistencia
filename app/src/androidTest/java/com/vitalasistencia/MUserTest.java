package com.vitalasistencia;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vitalasistencia.models.BUser;
import com.vitalasistencia.models.MUser;
import com.vitalasistencia.presenters.PList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        realm.beginTransaction();
        realm.commitTransaction();
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

    @Test
    public void MUser_isCorrect(){
        //----------------------------------------InsertUser----------------------------------------
        //Comprobamos el método para insertar un usuario
        //Como el metodo existUser se usa dentro de este método no te he puesto el test de existUser
        assertTrue(mUser.insertUser(test)); //okey
        //Por lo tanto comprobamos aquí que no puede meter el mismo usuario
        assertFalse(mUser.insertUser(test)); //okey
        assertTrue(mUser.insertUser(test1)); //okey
        assertFalse(mUser.insertUser(test1)); //okey
        assertTrue(mUser.insertUser(removeMe)); //okey
        assertFalse(mUser.insertUser(removeMe)); //okey
        //----------------------------------------UpdateUser----------------------------------------
        //Comprobamos el método para actualizar un usuario
        test.setAddress("Av Test1");
        test1.setAddress("Avenida tolai");

        assertTrue(mUser.updateUser(test)); //okey
        assertTrue(mUser.updateUser(test1)); //okey
        assertFalse(mUser.updateUser(null)); //okey

        test1.setDayWeek("Martes");
        //Al borrarlo no debería de encontrarlo, por lo tanto devolvería un false a la hora de intentar actualizar.
        mUser.removeUser(test1.getAffiliate_number());

        assertFalse(mUser.updateUser(test1)); //okey

        //----------------------------------------RemoveUser----------------------------------------
        //Comprobamos el método para eliminar un usuario
        assertTrue(mUser.removeUser(removeMe.getAffiliate_number())); //okey
        assertFalse(mUser.removeUser(removeMe.getAffiliate_number())); //okey

        //----------------------------------------SearchUser----------------------------------------
        //
        assertEquals(test , mUser.searchUser("123FFFFF")); //okey

        mUser.insertUser(test1);
        mUser.insertUser(removeMe);

        assertEquals(test1 ,mUser.searchUser(test1.getAffiliate_number())); //okey
        assertEquals(removeMe, mUser.searchUser(removeMe.getAffiliate_number())); //okey

        mUser.removeUser(test1.getAffiliate_number());
        mUser.removeUser(removeMe.getAffiliate_number());

        assertNull(mUser.searchUser(test1.getAffiliate_number())); //okey
        assertNull(mUser.searchUser(removeMe.getAffiliate_number())); //okey

        //----------------------------------------UsersQuery----------------------------------------
        //Comprobamos el método para filtrar usuarios
        List<BUser> pocholandia=mUser.UsersQuerys(null,"Martes","04/02/2021");

        assertEquals(test , pocholandia.get(0)); //okey
        assertNotEquals(test1, pocholandia.get(0)); //okey
        assertNotEquals(removeMe, pocholandia.get(0)); //okey

        mUser.insertUser(test1);
        test1.setDayWeek("Lunes");
        mUser.updateUser(test1);
        List<BUser> vamoAPorEl10=mUser.UsersQuerys(null,"Lunes","04/02/2021");

        assertEquals(test1, vamoAPorEl10.get(0)); //okey
        assertNotEquals(test, vamoAPorEl10.get(0)); //okey

        mUser.removeUser(test1.getAffiliate_number());

        //---------------------------------------GetAllSumary---------------------------------------
        //Comprobamos el método para listar todos los usuarios en la lista
        assertEquals(test , mUser.getAllSumary().get(0)); //okey

        mUser.insertUser(test1);

        assertEquals(test1, mUser.getAllSumary().get(1)); //okey
        assertNotEquals(test1, mUser.getAllSumary().get(0)); //okey
        assertNotEquals(removeMe,mUser.getAllSumary().get(1)); //okey

        //----------------------------------------GetSpinner----------------------------------------
        //Comprobamos el método para recuperar los datos del spinner
        assertEquals("Martes", mUser.getSpinner().get(0)); //okey
        assertEquals("Lunes", mUser.getSpinner().get(1)); //okey
        assertNotEquals("Viernes", mUser.getSpinner().get(0)); //okey
        assertNotEquals("Sabado", mUser.getSpinner().get(1)); //okey
    }

    @After
    public void reloadTenUsers(){
        mUser.removeUser(test1.getAffiliate_number());
        mUser.removeUser(test.getAffiliate_number());
        mUser.removeUser(removeMe.getAffiliate_number());
        PList a=new PList();
        a.tenUsersForFirstTime();
    }
}
