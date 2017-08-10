package com.example.cristian.scannerpichincha;

import java.util.Date;

/**
 * Created by cristian on 03/08/2017.
 */
public class Registro
{
    private int num_reg;
    private String fecha_hora,codigo;
    public Registro(int num_reg,String fecha_hora, String codigo){
        setNum_reg(num_reg);
        setFecha_hora(fecha_hora);
        setCodigo(codigo);
    }

    public int getNum_reg() {
        return num_reg;
    }

    public void setNum_reg(int num_reg) {
        this.num_reg = num_reg;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
}
