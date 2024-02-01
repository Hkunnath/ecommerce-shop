package com.example.ecommerceshop.product.mapper;

import com.example.ecommerceshop.product.model.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPageTransformer {
    List<Product> toDtos(final Page<Product> products);
}
