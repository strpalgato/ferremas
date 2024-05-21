/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package API;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * REST Web Service
 *
 * @author palgato
 */
@Path("rest")
public class RestResource {
    
    private static final String JSON_FILE_PATH = System.getProperty("user.dir") + "/productos.json"; //server apache tomcat
    private static List<Producto> productos = cargarProductos();

    private static List<Producto> cargarProductos() {
        File file = new File(JSON_FILE_PATH);
        System.out.println("Ruta del archivo JSON: " + JSON_FILE_PATH); // Agregar esta línea para imprimir la ruta
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Producto>>() {}.getType();
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    

    private static void guardarProductos() {
        File file = new File(JSON_FILE_PATH);
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(productos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @GET
    @Path("/ruta-json")
    @Produces(MediaType.TEXT_PLAIN)
    public String obtenerRutaJSON() {
        return "Ruta del archivo JSON: " + JSON_FILE_PATH;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Producto> getProductos() {
        return productos;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducto(@PathParam("id") int id) {
        for (Producto producto : productos) {
            if (producto.getIdProducto() == id) {
                return Response.ok(producto).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addProducto(Producto producto) {
        int newId = productos.size() + 1;
        producto.setIdProducto(newId);
        productos.add(producto);
        guardarProductos();
        return Response.ok("Producto agregado con éxito").build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateProducto(@PathParam("id") int id, Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == id) {
                producto.setIdProducto(id);
                productos.set(i, producto);
                guardarProductos();
                return Response.ok("Producto actualizado con éxito").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteProducto(@PathParam("id") int id) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == id) {
                productos.remove(i);
                guardarProductos();
                return Response.ok("Producto eliminado con éxito").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
    }
}
