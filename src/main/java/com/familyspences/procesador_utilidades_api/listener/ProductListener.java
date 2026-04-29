package com.familyspences.procesador_utilidades_api.listener;

import com.familyspences.procesador_utilidades_api.domain.ProductDomain;
import com.familyspences.procesador_utilidades_api.repository.ProductRepository;
import com.familyspences.procesador_utilidades_api.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.data.crossstore.ChangeSetPersister.ID_KEY;

@Component
public class ProductListener {

    private static final String MENSAJE = "mensaje";
    private static final String PRODUCTO_KEY = "producto";
    private static final String ID_KEY = "id";
    private final ProductService productService;

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "product.create.queue")
    public void handleCreateProduct(Map<String, Object> producto) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            ProductDomain savedProduct = productService.addProduct(producto);

            respuesta.put(MENSAJE, "Producto agregado exitosamente");
            respuesta.put(ID_KEY, savedProduct.getId().toString());
            respuesta.put(PRODUCTO_KEY, savedProduct);
            /*ProductDomain savedProduct = productService.addProduct(producto);
            nuevo.setProduct((String) producto.get("producto"));
            nuevo.setPrice(Integer.parseInt(producto.get("precio").toString()));
            nuevo.setStore((String) producto.get("negocio"));

            productService.save(nuevo);*/

            System.out.println("✅ Producto guardado en base de datos: " + savedProduct.getProduct());
        } catch (Exception e) {
            System.err.println("❌ Error al guardar producto: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "product.edit.queue")
    public void handleEditProduct(Map<String, Object> productData) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            ProductDomain updatedProduct = productService.updateProduct(productData);

            respuesta.put(MENSAJE, "Producto editado exitosamente");
            respuesta.put(ID_KEY, updatedProduct.getId().toString());
            respuesta.put(PRODUCTO_KEY, updatedProduct);
            System.out.println("✅ Producto editado en base de datos: " + updatedProduct.getProduct());
        } catch (Exception e) {
            System.err.println("❌ Error al editar producto: " + e.getMessage());
            // Opcional: envía respuesta de error si tienes un mecanismo para ello
        }
    }

    @RabbitListener(queues = "product.delete.queue")
    public void handleDeleteProduct(UUID productId) {
        try {
            if (productId == null || productId.toString().isEmpty()) {
                throw new IllegalArgumentException("El ID del producto es requerido para eliminación");
            }
            productService.deleteProduct(productId);
            System.out.println("✅ Producto eliminado: " + productId);
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar producto: " + e.getMessage());
        }
    }

}
