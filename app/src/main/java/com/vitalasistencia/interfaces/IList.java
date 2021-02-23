package com.vitalasistencia.interfaces;

import com.vitalasistencia.models.BUser;

import java.util.ArrayList;

public interface IList {
    interface View {
        void startFormActivity();
        void startSearchActivity();
        void startAboutActivity();
        void startFormActivity(String id);
        void startHelpActivity();
        void deleteUser(int pos);
    }

    interface Presenter {
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutButton();
        void onClickHelpButton();
        void onClickReciclerViewItem(String id);
        void onClickSwipeEdit(String pos);
        void onClickSwipeDelete(int pos, BUser user);
        int getAndroidVersion();
        ArrayList<BUser> getAllUsers();
        void tenUsersForFirstTime();
        ArrayList<BUser> getQuery(String address, String dayWeek, String date);
    }
}
