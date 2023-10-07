package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import Frontend.Cine;
import Logic.Peliculas.*;
import Logic.Tienda.*;
import Logic.Usuarios.*;

public class Deserializar {
    static File archivo = new File("");
    public static ArrayList<Cine> deserializarCine(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Cine.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Cine> lista_celulares = (ArrayList) o.readObject();

            file.close();
            o.close();
            return lista_celulares;

        }catch(FileNotFoundException e){
            return new ArrayList<Cine>();
        }
        catch(IOException e){
            return new ArrayList<Cine>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Cine>();
        }
    }
    
    public static ArrayList<Pelicula> deserializarPelicula(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Peliculas.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Pelicula> lista_peliculas = (ArrayList<Pelicula>) o.readObject();

            file.close();
            o.close();
            return lista_peliculas;

        }catch(FileNotFoundException e){
            return new ArrayList<Pelicula>();
        }
        catch(IOException e){
            return new ArrayList<Pelicula>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Pelicula>();
        }
    }
    
    public static ArrayList<Sala> deserializarSala() {
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Salas.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Sala> lista_salas = (ArrayList<Sala>) o.readObject();

            file.close();
            o.close();
            return lista_salas;

        }catch(FileNotFoundException e){
            return new ArrayList<Sala>();
        }
        catch(IOException e){
            return  new ArrayList<Sala>();
        }
        catch(ClassNotFoundException e) {
            return  new ArrayList<Sala>();
        }
    }
    
    public static ArrayList<Taquilla> deserializarTaquilla() {
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Taquilla.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Taquilla> lista_taquilla = (ArrayList<Taquilla>) o.readObject();

            file.close();
            o.close();
            return lista_taquilla;

        }catch(FileNotFoundException e){
            return new ArrayList<Taquilla>();
        }
        catch(IOException e){
            return  new ArrayList<Taquilla>();
        }
        catch(ClassNotFoundException e) {
            return  new ArrayList<Taquilla>();
        }
    }

    
    public static ArrayList<Tienda> deserializarnoTienda(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Tienda.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Tienda> lista_tienda = (ArrayList) o.readObject();

            file.close();
            o.close();
            return lista_tienda;

        }catch(FileNotFoundException e){
            return new ArrayList<Tienda>();
        }
        catch(IOException e){
            return new ArrayList<Tienda>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Tienda>();
        }
    }
    
    public static ArrayList<Producto> deserializarproducto(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Productos.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Producto> lista_productos = (ArrayList) o.readObject();

            file.close();
            o.close();
            return lista_productos;

        }catch(FileNotFoundException e){
            return new ArrayList<Producto>();
        }
        catch(IOException e){
            return new ArrayList<Producto>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Producto>();
        }
        
    }
    
    public static ArrayList<Trabajador> deserializarTrabajador(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Trabajador.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Trabajador> lista_trabajador = (ArrayList) o.readObject();

            file.close();
            o.close();
            return lista_trabajador;

        }catch(FileNotFoundException e){
            return new ArrayList<Trabajador>();
        }
        catch(IOException e){
            return new ArrayList<Trabajador>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Trabajador>();
        }
        
    }
    
    public static ArrayList<Usuario> deserializarUsuario(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Usuario.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Usuario> lista_usuario = (ArrayList) o.readObject();

            file.close();
            o.close();
            return lista_usuario;

        }catch(FileNotFoundException e){
            return new ArrayList<Usuario>();
        }
        catch(IOException e){
            return new ArrayList<Usuario>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Usuario>();
        }
        
    }
    
    public static ArrayList<Cliente> deserializarCliente(){
        try {
            FileInputStream file = new FileInputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Cliente.txt"));
            ObjectInputStream o = new ObjectInputStream(file);

            ArrayList<Cliente> lista_cliente = (ArrayList) o.readObject();

            file.close();
            o.close();
            return lista_cliente;

        }catch(FileNotFoundException e){
            return new ArrayList<Cliente>();
        }
        catch(IOException e){
            return new ArrayList<Cliente>();
        }
        catch(ClassNotFoundException e) {
            return new ArrayList<Cliente>();
        }
        
    }
    
    
    
    

}
