package com.vitalasistencia.presenters;

import android.os.Build;
import android.util.Log;

import com.vitalasistencia.interfaces.IList;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.models.MUser;

import java.util.ArrayList;

public class PList implements IList.Presenter {

    String TAG = "Vital_Asistencia/PList";
    private IList.View view;
    private MUser MUser;

    public PList(IList.View view) {
        this.view = view;
        MUser = new MUser();
    }


    @Override
    public void onClickFloatingButton() {
        Log.d(TAG, "Button Clicked");
        view.startFormActivity();
    }

    @Override
    public void onClickSearchButton() {
        Log.d(TAG, "Search Clicked");
        view.startSearchActivity();
    }

    @Override
    public void onClickAboutButton() {
        Log.d(TAG, "About clicked");
        view.startAboutActivity();
    }

    @Override
    public void onClickReciclerViewItem(String affiliate_number) {
        Log.d(TAG, "Item" + affiliate_number + "selected");
        BUser result=null;
        try{
            result=MUser.searchUser(affiliate_number);
            if(result!=null && result.getAffiliate_number().equals(affiliate_number)){
                //view.startFormActivity(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error while searching user on DB");
        }
        view.startFormActivity(affiliate_number);
    }

    @Override
    public void onClickSwipeEdit(String affiliate_number) {
        Log.d(TAG, "Item" + affiliate_number + "editing");
        try{
            MUser.searchUser(affiliate_number);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error while searching user on DB");
        }
        view.startFormActivity(affiliate_number);
    }

    @Override
    public void onClickSwipeDelete(int pos, BUser user) {
        Log.d(TAG, "Item" + pos + "deleting");
        try{
            if(MUser.removeUser(user.getAffiliate_number())){
                view.deleteUser(pos);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error while deleting a user from list");
        }
    }

    @Override
    public int getAndroidVersion() {
        int result = (int) Build.VERSION.SDK_INT;
        return result;
    }

    @Override
    public ArrayList<BUser> getAllUsers() {
        ArrayList<BUser> result=null;
        try{
            result=new ArrayList<>(MUser.getAllSumary());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
