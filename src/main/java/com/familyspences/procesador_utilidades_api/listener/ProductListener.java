package com.familyspences.procesador_utilidades_api.listener;

import com.familyspences.procesador_utilidades_api.domain.ProductDomain;
import com.familyspences.procesador_utilidades_api.repository.ProductRepository;
import com.familyspences.procesador_utilidades_api.service.ProductService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    @RabbitListener(queuesToDeclare = @Queue("product.create.queue"))
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
}
