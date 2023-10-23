package baseDatos;

import java.io.File;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import uiMain.Cine;
import gestorAplicacion.Peliculas.*;
import gestorAplicacion.Tienda.*;
import gestorAplicacion.Usuarios.*;


public class Serializar {
    static File archivo = new File("");
    
    public static void serializarPelicula(ArrayList<Pelicula> lista) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
                    "C:\\Users\\Yiduar Rangel\\Desktop\\enainn\\practica-1-grupo-2-equipo-5\\src\\baseDatos\\temp\\Peliculas.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

        }catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("No se encuentra en archivo");
        }

    }
    
    public static void serializarSala(ArrayList<Sala> lista) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
                    "C:\\Users\\Yiduar Rangel\\Desktop\\enainn\\practica-1-grupo-2-equipo-5\\src\\baseDatos\\temp\\Salas.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

        }catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("No se encuentra en archivo");
        }

    }

    public static void serializarTaquilla(ArrayList<Taquilla> lista) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
                    "\\src\\baseDatos\\temp\\Taquilla.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

        }catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("No se encuentra en archivo");
        }

    }
    public static void serializarTienda(ArrayList<Tienda> lista){
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
                    "C:\\Users\\Yiduar Rangel\\Desktop\\enainn\\practica-1-grupo-2-equipo-5\\src\\baseDatos\\temp\\Tienda.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

        }catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("No se encuentra en archivo");
        }
    }
    public static void serializarProductos(ArrayList<Producto> lista){
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
                    "C:\\Users\\Yiduar Rangel\\Desktop\\enainn\\practica-1-grupo-2-equipo-5\\src\\baseDatos\\temp\\Producto.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

        }catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("No se encuentra en archivo");
        }
    }
    public static void serializarCombos(ArrayList<Combo> lista){
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
                    "C:\\Users\\Yiduar Rangel\\Desktop\\enainn\\practica-1-grupo-2-equipo-5\\src\\baseDatos\\temp\\Combo.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

        }catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("No se encuentra en archivo");
        }
    }

    public static boolean serializarCliente(ArrayList<Cliente> lista) {
        try {
            String rutaArchivo = "C:\\Users\\Yiduar Rangel\\Desktop\\enainn\\practica-1-grupo-2-equipo-5\\src\\baseDatos\\temp\\Cliente.txt";
            FileOutputStream f = new FileOutputStream(new File(rutaArchivo));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean serializarUsuario(ArrayList<Usuario> lista) {
        try {
            String rutaArchivo = "src/baseDatos/temp/Usuario.txt";
            FileOutputStream f = new FileOutputStream(new File(rutaArchivo));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            return false;
        }
    }
    
    public static void serializarCine(ArrayList<Cine> lista) {
        try {
            String rutaArchivo = "src/baseDatos/temp/Cine.txt";
            FileOutputStream f = new FileOutputStream(new File(rutaArchivo));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);

            o.close();
            f.close();

            System.out.println("La serialización se ha completado correctamente.");

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}

