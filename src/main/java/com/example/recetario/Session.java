package com.example.recetario;

import com.example.recetario.models.Receta;

public class Session {

    private static Receta recetaActual = null;

    public static Receta getRecetaActual() {
        return recetaActual;
    }

    public static void setRecetaActual(Receta recetaActual) {
        Session.recetaActual = recetaActual;
    }



}
