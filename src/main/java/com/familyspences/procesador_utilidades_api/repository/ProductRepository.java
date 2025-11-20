package com.familyspences.procesador_utilidades_api.repository;

import com.familyspences.procesador_utilidades_api.domain.ProductDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductDomain, UUID> {

    List<ProductDomain> findByProductContainingIgnoreCase(String product);
}
