package com.backend.helpdesk.converter.DayOffTypeConverter;

import com.backend.helpdesk.DTO.DayOffTypeDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.DayOffType;
import org.springframework.stereotype.Component;

@Component
public class DayOffTypeToDayOffTypeDTOConvert extends Converter<DayOffType, DayOffTypeDTO> {
    @Override
    public DayOffTypeDTO convert(DayOffType source) {
        DayOffTypeDTO dayOffTypeDTO=new DayOffTypeDTO();
        dayOffTypeDTO.setId(source.getId());
        dayOffTypeDTO.setName(source.getName());
        return dayOffTypeDTO;
    }
}
