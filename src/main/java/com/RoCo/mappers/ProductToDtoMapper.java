package com.RoCo.mappers;


import com.RoCo.models.Product;
import com.RoCo.models.ProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface ProductToDtoMapper {
    Product AddProductRequestToProduct(ProductRequest productRequest);
}
