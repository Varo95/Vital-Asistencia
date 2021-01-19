package com.vitalasistencia.models;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

//Revisar métodos para que estén bien formados y hechos
public class MUser {
    /**
     * This method check if an user exists on database
     * @param affiliate_number the user affiliate_number to search in db
     * @return true if found elements, otherwhise, false
     */
    public boolean existUser(String affiliate_number) {
        boolean result = false;
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            if(realm.where(BUser.class).equalTo("affiliate_number", affiliate_number).findAll().size()==0){
                result=false;
            }else{
                result=true;
            }
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
        }
        return result;
    }

    /**
     * This method insert a user to the database
     * @param user user that needs to be inserted
     * @return true if success, otherwhise, false
     */
    public boolean insertUser(BUser user) {
        boolean valid = false;
        if (user!=null && !existUser(user.getAffiliate_number())) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.beginTransaction();
                realm.copyToRealm(user);
                realm.commitTransaction();
                realm.close();
                valid = true;
            } catch (Exception e) {
                e.printStackTrace();
                realm.close();
                valid = false;
            }
        }
        return valid;
    }

    public boolean updateUser(BUser user) {
        boolean valid = false;
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(user);
            realm.commitTransaction();
            realm.close();
            valid = true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            valid = false;
        }
        return valid;
    }

    public boolean removeUser(String affiliate_number) {
        boolean valid = false;
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            RealmResults<BUser> result = realm.where(BUser.class).equalTo("affiliate_number", affiliate_number).findAll();
            result.deleteAllFromRealm();
            realm.commitTransaction();
            realm.close();
            valid = true;
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            valid = false;
        }
        return valid;
    }

    public BUser searchUser(String affiliate_number) {
        BUser user_read = null;
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            user_read = realm.copyFromRealm(realm.where(BUser.class).equalTo("affiliate_number", affiliate_number).findFirst());
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
        }
        return user_read;
    }

    public ArrayList<BUser> readAllUsers() {
        ArrayList<BUser> result = new ArrayList<BUser>();
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            RealmResults<BUser> results = realm.where(BUser.class).findAll();
            result.addAll(realm.copyFromRealm(results));
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
        }
        return result;
    }

    /**
     * This method return a filtered BUser ArrayList, only loading the fields we need to load on MainActivity ReciclerView
     * @return
     */
    public ArrayList<BUser> getAllSumary(){
        ArrayList<BUser> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            RealmResults<BUser> results = realm.where(BUser.class).findAll();
            result.addAll(realm.copyFromRealm(results));
            realm.commitTransaction();
            realm.close();
        }catch(Exception e){
            e.printStackTrace();
            realm.close();
        }
        //Actualizamos los usuarios creando uno nuevo para cada uno, para que contenga menos datos
        for(BUser user:result){
            int i=0;
            result.set(i++,new BUser(user.getImage(),user.getAddress(),user.getEmail(),user.getAffiliate_number()));
        }
        return result;
    }

}
