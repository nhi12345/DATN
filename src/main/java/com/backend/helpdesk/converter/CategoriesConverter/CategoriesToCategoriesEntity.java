package com.backend.helpdesk.converter.CategoriesConverter;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriesToCategoriesEntity extends Converter<Categories, CategoriesEntity> {

    @Override
    public CategoriesEntity convert(Categories source) {
        return new CategoriesEntity(source.getId(), source.getName());
    }
}
