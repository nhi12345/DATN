package com.backend.helpdesk.converter.ConvertDayOff;

import com.backend.helpdesk.DTO.DayOffTypeDTO;
import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.DTO.UserDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.DTO.DayOffDTO;
import com.backend.helpdesk.entity.DayOffType;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DayOffToDayOffDTOConverter extends Converter<DayOff, DayOffDTO> {

    @Autowired
    private Converter<DayOffType, DayOffTypeDTO> dayOffTypeDayOffTypeDTOConverter;

    @Autowired
    private Converter<Status, StatusDTO> statusStatusDTOConverter;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityProfileConverter;

    @Override
    public DayOffDTO convert(DayOff source) {
        DayOffDTO dayOffDTO=new DayOffDTO();
        dayOffDTO.setId(source.getId());
        dayOffDTO.setCreateAt(source.getCreateAt());
        dayOffDTO.setDayStartOff(source.getDayStartOff());
        dayOffDTO.setDayEndOff(source.getDayEndOff());
        dayOffDTO.setDescription(source.getDescription());
        dayOffDTO.setDayOffType(dayOffTypeDayOffTypeDTOConverter.convert(source.getDayOffType()));
        dayOffDTO.setStatus(statusStatusDTOConverter.convert(source.getStatus()));
        dayOffDTO.setUserEntity(userEntityProfileConverter.convert(source.getUserEntity()));
        return dayOffDTO;
    }
}
