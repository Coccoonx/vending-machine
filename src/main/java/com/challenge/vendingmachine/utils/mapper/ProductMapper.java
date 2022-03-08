package com.challenge.vendingmachine.utils.mapper;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProductMapper {

    @Mapping(source = "seller.id", target = "sellerId")
    ProductDTO toDTO(Product product);

    @Mapping(source = "sellerId", target = "seller")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null)
            return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }

}
