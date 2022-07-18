package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.payload.response_dto.admin.ProductForModeratorResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.ProductForCollabGetDetailResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.ProductForCollaboratorResDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productForCollaboratorResDtoToProduct(ProductForCollaboratorResDto productForCollaboratorResDto);

    ProductForCollaboratorResDto productToProductForCollaboratorResDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductForCollaboratorResDto(ProductForCollaboratorResDto productForCollaboratorResDto, @MappingTarget Product product);

    @AfterMapping
    default void linkImage(@MappingTarget Product product) {
        product.getImage().forEach(image -> image.setProduct(product));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductForCollaboratorResDto1(com.springframework.csscapstone.data.domain.ProductForCollaboratorResDto productForCollaboratorResDto, @MappingTarget Product product);

    @AfterMapping
    default void linkRequestSellingProducts(@MappingTarget Product product) {
        product.getRequestSellingProducts().forEach(requestSellingProduct -> requestSellingProduct.setProduct(product));
    }

    Product productForModeratorResDtoToProduct(ProductForModeratorResDto productForModeratorResDto);

    ProductForModeratorResDto productToProductForModeratorResDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductForModeratorResDto(ProductForModeratorResDto productForModeratorResDto, @MappingTarget Product product);

    Product productFoeCollabResDtoToProduct(ProductForCollabGetDetailResDto productForCollabGetDetailResDto);

    ProductForCollabGetDetailResDto productToProductFoeCollabResDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductFoeCollabResDto(ProductForCollabGetDetailResDto productForCollabGetDetailResDto, @MappingTarget Product product);
}
