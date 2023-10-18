package gestorAplicacion.Usuarios;

public class Usuario {
    protected String nombre;
    protected String password;
    protected int edad;

    public Usuario(){
        this("NN", "NN", 19);
    }

    public Usuario(String nombre, String password, int edad){
        this.nombre = nombre;
        this.password = password;
        this.edad = edad;
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
}
