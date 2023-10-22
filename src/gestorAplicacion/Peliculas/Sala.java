package gestorAplicacion.Peliculas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gestorAplicacion.Tarjetas.Diamante;
import gestorAplicacion.Tarjetas.Tarjeta;

import java.io.Serializable;

public class Sala implements Serializable{
    private Taquilla taquilla;
    private static ArrayList<Sala> salasCreadas = new ArrayList<Sala>();
    private ArrayList<Pelicula> peliculasSala = new ArrayList<Pelicula>();
    private String nombre;
    private int numeroAsientosD = 5;
    private String hora;
    private HashMap<Integer,String> asientos = new HashMap<Integer,String>();

    //Constructores

    public Sala(int numeroAsientosD, Taquilla taquilla){
        this("Nn",numeroAsientosD, "", taquilla);
    }

    public Sala(String nombre,int numeroAsientosD, String hora, Taquilla taquilla){
        this.nombre = nombre;
        this.numeroAsientosD += numeroAsientosD;
        this.hora = hora;
        this.taquilla = taquilla;

        for(int i = 1; i <= this.numeroAsientosD ; i++){
            if(i >= (this.numeroAsientosD - 5)){
                asientos.put(i, "privado");
            } else {
                asientos.put(i, "");
            }
        }
        salasCreadas.add(this);
    }

    //Getters y setters

    public String getHora(){
        return this.hora;
    }

    public void setHora(String hora){
        this.hora = hora;
    }

    public Taquilla getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(Taquilla taquilla) {
        this.taquilla = taquilla;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getNumeroAsientosDisponibles(){
        return this.numeroAsientosD;
    }
 
    public List<Integer> AsientosDisponibles(ArrayList<Tarjeta> tarjetasDelUsuario){
        List<Integer> asientosVacios = new ArrayList<>();
        boolean entraPrivado = false;
        for (Tarjeta tarjeta : tarjetasDelUsuario) {
            if(tarjeta instanceof Diamante){
                entraPrivado = true;
                break;
            }
        }

        for (Map.Entry<Integer, String> entry : this.asientos.entrySet()) {
            if(entraPrivado){
                if(entry.getValue() == "privado"){
                asientosVacios.add(entry.getKey());
                }
            }
            if(entry.getValue() == ""){
                asientosVacios.add(entry.getKey());
            }
        }
        return asientosVacios;
    }

    //Clases de instancia  

    public boolean quitarAsientoDisponible(int num){
        for (Map.Entry<Integer,String> asiento : asientos.entrySet()) {
            if(asiento.getKey() == num){
                if(asiento.getValue() == "privado" || asiento.getValue() == ""){
                    this.asientos.put(num,"Ocupado");
                    this.numeroAsientosD -= 1;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean quitarAsientoPrivado(int num){
        if (this.asientos.get(num) != "privado") {
            return false;
        } else {
            this.asientos.put(num,"Ocupado");
            this.numeroAsientosD -= 1;
            return true;
        }
    }

    public HashMap<Integer,String> getTotalAsientos(){
        return this.asientos;
    }

    public boolean añadirAsientoDisponible(int num){
        if(this.asientos.get(num) == ""){
            return false;
        } else {
            this.asientos.put(num,"");
            return true;
        }
    }

    public void añadirNumeroAsientoDisponible(){
        this.numeroAsientosD += 1;
    }

    //este metodo solo se efectúa si desde la clase pelicula se intenta unir una pelicula con una sala que tengan la misma hora 
    //si en efecto tienen la misma hora, la pelicula se agrega a el listado de peliculas con sala en la clase Taquilla
    public void enlazarPeliculaALaSala(Pelicula pelicula){
        peliculasSala.add(pelicula);
        taquilla.peliculasSala.add(pelicula);
    }

    public void removerPeliculaEnLaSala(Pelicula pelicula){
        peliculasSala.remove(pelicula);
        taquilla.peliculasSala.remove(pelicula);
    }

    //Metodos de clase
    public static ArrayList<Sala> getSalasCreadas(){
        return Sala.salasCreadas;
    }

    public static String mostrarSalasCreadas(){
        String texto = "Salas creadas: ";
        for (Sala string : salasCreadas) {
            texto += string.getNombre() +" "+ string.getHora() +" | ";
        }
        return texto;
    }

    //toString
    public String verAsientosPrivados(){
        String as = "";
        for (Map.Entry<Integer, String> entry : this.asientos.entrySet()) {
            if(entry.getValue() == "privado"){
                as += entry.getKey() + ", ";
            }
        }

        return as;
    }
    
    public String toString(){
        String asientos = "";
        for (Map.Entry<Integer, String> entry : this.asientos.entrySet()) {
            if(entry.getValue().equals("")){
                asientos += entry.getKey() + ", ";
            }
        }
        return "Asientos disponibles: " + asientos;
    }
}
