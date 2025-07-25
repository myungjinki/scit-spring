package net.dsa.ex4.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.ex4.entity.ChickenEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChickenDTO {

    private Integer id;
    private String chickenType;
    private int chickenPrice;
    private int quantity;
    private String extraOptions;
    private int extraTotalPrice;
    private String deliveryType;
    private int deliveryPrice;
    private int totalPrice;
    private LocalDateTime orderDate;

    public static void ChickenDTO_toEntity(ChickenDTO dto, ChickenEntity entity) {
        entity.setId(dto.getId());
        entity.setChickenType(dto.getChickenType());
        entity.setChickenPrice(dto.getChickenPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setExtraOptions(dto.getExtraOptions());
        entity.setExtraTotalPrice(dto.getExtraTotalPrice());
        entity.setDeliveryType(dto.getDeliveryType());
        entity.setDeliveryPrice(dto.getDeliveryPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setOrderDate(dto.getOrderDate());
    }

    public static void ChickenEntity_toDTO(ChickenEntity entity, ChickenDTO dto) {
        dto.setId(entity.getId());
        dto.setChickenType(entity.getChickenType());
        dto.setChickenPrice(entity.getChickenPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setExtraOptions(entity.getExtraOptions());
        dto.setExtraTotalPrice(entity.getExtraTotalPrice());
        dto.setDeliveryType(entity.getDeliveryType());
        dto.setDeliveryPrice(entity.getDeliveryPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setOrderDate(entity.getOrderDate());
    }
}