package gestorAplicacion.Usuarios;
import java.io.Serializable;
import java.util.ArrayList;


import gestorAplicacion.Tarjetas.Tarjeta;

public class Usuario implements Serializable {
    protected String nombre;
    protected String password;
    protected int edad;
    protected Tarjeta tarjeta;


    public Usuario(){
        this("NN", "NN", 19);
    }

    public Usuario(String nombre, String password, int edad){
        this.nombre = nombre;
        this.password = password;
        this.edad = edad;
        this.tarjeta = null;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getPassword(){
        return this.password;
    }
    

    public void setPassword(String password){
        this.password = password;
    }

    public int getEdad(){
        return this.edad;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }

    public String getTipo(){
        return "Usuario";
    }
    
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    public Tarjeta getTarjeta() {
        return this.tarjeta;
    }
    

    
    @Override
    public String toString() {
        String tarjetaInfo = (tarjeta != null) ? tarjeta.toString() : "Ninguna tarjeta adquirida";
        return "Nombre: " + nombre + "\n" +
               "Edad: " + edad + "\n" +
               "Tarjeta: " + tarjetaInfo;
    }
    


}

