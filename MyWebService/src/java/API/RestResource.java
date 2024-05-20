/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package API;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author palgato
 */
@Path("rest")
public class RestResource {

    private static List<Producto> productos = new ArrayList<>();

    static {
        // Inicializar con algunos productos
        productos.add(new Producto(1, "Producto 1", 10.00f, 100));
        productos.add(new Producto(2, "Producto 2", 20.00f, 200));
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
        //return Response.status(Response.Status.CREATED).entity(producto).build();
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
                return Response.ok("Producto eliminado con éxito").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
    }
}
