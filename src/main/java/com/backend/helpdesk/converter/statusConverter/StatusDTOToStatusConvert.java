package com.backend.helpdesk.converter.statusConverter;

import com.backend.helpdesk.DTO.StatusDTO;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusDTOToStatusConvert extends Converter<StatusDTO, Status> {
    @Override
    public Status convert(StatusDTO source) {
        Status status=new Status();
        status.setId(source.getId());
        status.setName(source.getName());
        return status;
    }
}
