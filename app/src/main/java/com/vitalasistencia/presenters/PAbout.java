package com.vitalasistencia.presenters;

import com.vitalasistencia.interfaces.IAbout;

public class PAbout implements IAbout.Presenter {
    private IAbout.View view;

    public PAbout(IAbout.View view) {
        this.view = view;
    }
}
