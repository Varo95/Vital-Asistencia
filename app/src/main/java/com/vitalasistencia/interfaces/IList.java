package com.vitalasistencia.interfaces;

public interface IList {
    public interface View {
        void startFormActivity();
        void startSearchActivity();
        void startAboutActivity();
        void startFormActivity(String id);
        void deleteUser(int pos);
    }

    public interface Presenter {
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutButton();
        void onClickReciclerViewItem(String id);
        void onClickSwipeEdit(String pos);
        void onClickSwipeDelete(int pos);
    }
}
