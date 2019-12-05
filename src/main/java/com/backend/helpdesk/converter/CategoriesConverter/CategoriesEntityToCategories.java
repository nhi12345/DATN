package com.backend.helpdesk.converter.CategoriesConverter;

import com.backend.helpdesk.DTO.Categories;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.CategoriesEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriesEntityToCategories extends Converter<CategoriesEntity, Categories> {

    @Override
    public Categories convert(CategoriesEntity source) {
        Categories categories = new Categories();
        categories.setId(source.getId());
        categories.setName(source.getName());
        return categories;
    }
}
