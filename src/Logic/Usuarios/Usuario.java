package Logic.Usuarios;

public class Usuario {
    protected String nombre;
    protected int edad;

    public Usuario(){
        this("NN", 19);
    }

    public Usuario(String nombre, int edad){
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
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
}
