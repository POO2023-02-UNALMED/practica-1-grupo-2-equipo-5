package Logic.Peliculas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sala {
    private static ArrayList<Sala> salasCreadas = new ArrayList<Sala>();
    private String nombre;
    private int numeroAsientosD;
    private HashMap<Integer,String> asientos = new HashMap<Integer,String>();

    //Constructores

    public Sala(int numeroAsientosD){
        this("Nn",numeroAsientosD);
    }

    public Sala(String nombre,int numeroAsientosD){
        this.nombre = nombre;
        this.numeroAsientosD = numeroAsientosD;

        for(int i = 1; i <= this.numeroAsientosD ; i++){
            asientos.put(i, "");
        }
        salasCreadas.add(this);
    }

    //Getters y setters

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getNumeroAsientosDisponibles(){
        return this.numeroAsientosD;
    }
 
    public List<Integer> getAsientosDisponibles(){
        List<Integer> asientosVacios = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : this.asientos.entrySet()) {
            if(entry.getValue() == ""){
                asientosVacios.add(entry.getKey());
            }
        }
        return asientosVacios;
    }

    //Clases de instancia  

    public boolean quitarAsientoDisponible(int num){
        if (this.asientos.get(num) != "") {
            return false;
        } else {
            this.asientos.put(num,"Ocupado");
            this.numeroAsientosD -= 1;
            return true;
        }
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

    //Metodos de clase
    public static ArrayList<Sala> getsSalasCreadas(){
        return Sala.salasCreadas;
    }

    public static String mostrarSalasCreadas(){
        String texto = "";
        for (Sala string : salasCreadas) {
            texto += string.getNombre() + " | ";
        }
        return texto;
    }

    //toString

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
