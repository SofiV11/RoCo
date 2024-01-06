package com.RoCo.mappers;


import com.RoCo.entities.CatalogEnt.ProductEnt;
import com.RoCo.models.Product;
import org.mapstruct.Mapper;

//методы для конвертации из ProductEntity в Product и обратно
@Mapper(componentModel = "spring")
public interface ProductToEntMapper {
    ProductEnt productToProductEnt(Product product);
    Product productEntToProduct(ProductEnt productEnt);
}

//Так как имена полей классов соотносятся один к одному,
//то интерфейс получился таким простым. Если поля имеют отличающиеся имена,
//то потребуется аннотацией @Mapping указать какие поля соответствуют друг другу.
//Более подробно можно найти в документации. Чтобы spring смог сам создавать бины этого класса,
//необходимо указать componentModel = "spring".