package com.challenge.vendingmachine.utils.mapper;

import com.challenge.vendingmachine.model.Purchase;
import com.challenge.vendingmachine.model.dto.PurchaseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UserMapper.class})
public interface PurchaseMapper {

    PurchaseDTO toDTO(Purchase purchase);

    Purchase toEntity(PurchaseDTO purchaseDTO);

    default Purchase fromId(Long id) {
        if (id == null)
            return null;
        Purchase purchase = new Purchase();
        purchase.setId(id);
        return purchase;
    }

}
