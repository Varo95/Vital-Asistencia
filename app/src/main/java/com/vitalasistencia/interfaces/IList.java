package com.vitalasistencia.interfaces;

public interface IList {
    //TODO Implementar los métodos para editar y borrar usuarios desde el Swipe
    public interface View {
        void startFormActivity();
        void startSearchActivity();
        void startAboutActivity();
        void startFormActivity(String id);
        //void delete item(String id);
    }

    public interface Presenter {
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutButton();
        void onClickReciclerViewItem(String id);
        //void onClickSwipeEdit(String id);
        //void onClickSwipeDelete(String id);
    }
}
