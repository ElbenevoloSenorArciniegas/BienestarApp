package com.example.bienestarapp.Util;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.bienestarapp.R;

import java.util.Calendar;

public class Hora {
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    public static void obtenerHora(Context context, final EditText etHora){
        //Calendario para obtener fecha & hora
        final Calendar c = Calendar.getInstance();

        //Variables para obtener la hora hora
        final int hora = c.get(Calendar.HOUR_OF_DAY);
        final int minuto = c.get(Calendar.MINUTE);

        TimePickerDialog recogerHora = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.setTitle(R.string.dialog_solicitar_txt_hora);
        recogerHora.show();
    }
}
