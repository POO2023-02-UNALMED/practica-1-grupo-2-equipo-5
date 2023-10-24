package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import uiMain.Cine;
import gestorAplicacion.Peliculas.*;
import gestorAplicacion.Tienda.*;
import gestorAplicacion.Usuarios.*;

public class Deserializar {

    static String baseFilePath = "src" + File.separator + "baseDatos" + File.separator + "temp" + File.separator;

    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> deserialize(String fileName) {
        ArrayList<T> list;
        try (FileInputStream file = new FileInputStream(new File(baseFilePath + fileName));
             ObjectInputStream o = new ObjectInputStream(file)) {
            list = (ArrayList<T>) o.readObject();
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de I/O: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static ArrayList<Cine> deserializarCine() {
        return deserialize("Cine.txt");
    }

    public static ArrayList<Pelicula> deserializarPelicula() {
        return deserialize("Peliculas.txt");
    }

    public static ArrayList<Sala> deserializarSala() {
        return deserialize("Salas.txt");
    }

    public static ArrayList<Taquilla> deserializarTaquilla() {
        return deserialize("Taquilla.txt");
    }

    public static ArrayList<Tienda> deserializarTienda() {
        return deserialize("Tienda.txt");
    }

    public static ArrayList<Producto> deserializarProducto() {
        return deserialize("Productos.txt");
    }

    public static ArrayList<Usuario> deserializarUsuario() {
        return deserialize("Usuario.txt");
    }

    public static ArrayList<Cliente> deserializarCliente() {
        return deserialize("Cliente.txt");
    }
}
