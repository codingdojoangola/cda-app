package com.codingdojoangola.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ja_developer on 20-06-2016.
 */
public class DataActual {

    Calendar cal;

    public DataActual(){
        cal = new GregorianCalendar();
    }

    public String DiaMesAnoHora(){

        String data = Dia() +"/"+MesAbreviado()+"/"+Ano();
        String tempo = Hora();

        return data + ", " + tempo;

    }

    public String DiaMesAno(){
        return Dia() +" de "+Mes()+" de "+Ano();
    }

    public String DiaMesHora(){

        String data = Dia() +" de "+Mes();
        String tempo = Hora();

        return tempo + ", " + data;

    }

    public String Dia(){
        return ""+cal.get(Calendar.DAY_OF_MONTH);
    }

    public String Mes(){
        return getMes(cal.get(Calendar.MONTH) + 1);
    }

    public String MesAbreviado(){
        return getMesAbreviado(cal.get(Calendar.MONTH) + 1);
    }

    public String Ano(){
        return ""+cal.get(Calendar.YEAR);
    }

    public String Hora(){

        String hora = ""+cal.get(Calendar.HOUR_OF_DAY);
        String min = ""+cal.get(Calendar.MINUTE);
        String sec = ""+cal.get(Calendar.SECOND);

        if (min.length() == 1){
            min = "0"+min;
        }

        if (hora.length() == 1){
            hora = "0"+hora;
        }

        return hora+":"+min;
    }

    public String getMes(int mes){
        String retorno ="";
        
        switch (mes){
            case 1:
                retorno = "Janeiro";
                break;
            case 2:
                retorno = "Fevereiro";
                break;
            case 3:
                retorno = "Março";
                break;
            case 4:
                retorno = "Abril";
                break;
            case 5:
                retorno = "Maio";
                break;
            case 6:
                retorno = "Junho";
                break;
            case 7:
                retorno = "Julho";
                break;
            case 8:
                retorno = "Agosto";
                break;
            case 9:
                retorno = "Setembro";
                break;
            case 10:
                retorno = "Outubro";
                break;
            case 11:
                retorno = "Novembro";
                break;
            case 12:
                retorno = "Dezembro";
                break;
        }
        return retorno;
    }

    public String getMesAbreviado(int mes){
        String retorno ="";

        switch (mes){
            case 1:
                retorno = "Jan";
                break;
            case 2:
                retorno = "Fev";
                break;
            case 3:
                retorno = "Mar";
                break;
            case 4:
                retorno = "Abr";
                break;
            case 5:
                retorno = "Mai";
                break;
            case 6:
                retorno = "Jun";
                break;
            case 7:
                retorno = "Jul";
                break;
            case 8:
                retorno = "Ago";
                break;
            case 9:
                retorno = "Set";
                break;
            case 10:
                retorno = "Out";
                break;
            case 11:
                retorno = "Nov";
                break;
            case 12:
                retorno = "Dez";
                break;
        }
        return retorno;
    }
}
