package com.backend.helpdesk.service;

import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.converter.Converter;
import com.backend.helpdesk.entity.DayOff;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.DTO.DayOffDTO;
import com.backend.helpdesk.exception.UserException.BadRequestException;
import com.backend.helpdesk.exception.UserException.NotFoundException;
import com.backend.helpdesk.repository.DayOffRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import com.backend.helpdesk.respone.NumberOfDayOff;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DayOffService {
    @Autowired
    private DayOffRepository dayOffRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter<DayOff, DayOffDTO> dayOffDayOffDTOConverter;

    @Autowired
    private Converter<DayOffDTO, DayOff> dayOffDTODayOffConverter;

    @Autowired
    private CommonMethods commonMethods;

    public List<DayOff> getAllDayOff() {
        return dayOffRepository.findAll();
    }

    public List<DayOff> getDayOffsByStatus(String enable) {
        Status status = statusRepository.findByName(enable);
        if (status == null) {
            throw new NotFoundException("Day off not found!");
        }
        return dayOffRepository.findByStatus(status);
    }

    public long getNumberOfDayOffByUser(int id, int year) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        Date startingDay = userEntity.get().getStartingDay();
        if(startingDay==null){
            throw new BadRequestException("Incomplete information");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startingDay);
        int startingYear = calendar.get(Calendar.YEAR);
        if (startingYear > year) {
            throw new BadRequestException("Bad request!");
        }
        LocalDate startingLocalDate = startingDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.of(year, 12, 31);
        return ChronoUnit.YEARS.between(startingLocalDate, now) + Constants.DAYOFFBYRULE;
    }

    public float getNumberOfDayOffUsed(int id, int year) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        List<DayOff> dayOffs = dayOffRepository.getDayOffByYear(year, id);
        float result = 0;
        for (DayOff dayOff : dayOffs) {
            result += commonMethods.calculateDaysBetweenTwoDate(dayOff.getDayStartOff(), dayOff.getDayEndOff());
        }
        int a = 0;
        return result;
    }

    public List<DayOffDTO> getListDayOffUsed(int id, Integer year) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        if(year==null){
            Status status=statusRepository.findByName("approved");
            return dayOffDayOffDTOConverter.convert(dayOffRepository.findByUserEntityAndStatus(userEntity.get(),status));
        }
        List<DayOff> dayOffs = dayOffRepository.getDayOffByYear(year, id);
        return dayOffDayOffDTOConverter.convert(dayOffs);
    }

    public float getNumberDayOffByUserRemaining(int id, int year) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found!");
        }
        return getNumberOfDayOffByUser(id, year) - getNumberOfDayOffUsed(id, year);
    }

    public NumberOfDayOff getNumberOffDayOff(int id,int year){
        NumberOfDayOff numberOfDayOff=new NumberOfDayOff();
        numberOfDayOff.setUsed(getNumberOfDayOffUsed(id, year));
        numberOfDayOff.setRemaining(getNumberDayOffByUserRemaining(id, year));
        return numberOfDayOff;
    }

    public DayOff addDayOff(DayOffDTO dayOffDTO) {
        //number of day off register in request
        float numberOfDayOff = commonMethods.calculateDaysBetweenTwoDate(dayOffDTO.getDayStartOff(), dayOffDTO.getDayEndOff());
        LocalDate localDateStart = dayOffDTO.getDayStartOff().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearStart = localDateStart.getYear();

        //number of day off remaining this year
        float numberOfDayOffRemainingThisYear = getNumberDayOffByUserRemaining(dayOffDTO.getUserEntity(), yearStart);
        if (numberOfDayOff > numberOfDayOffRemainingThisYear) {
            throw new BadRequestException("The number of days left is not enough!");
        }
        LocalDate localDateEnd = dayOffDTO.getDayStartOff().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearEnd = localDateStart.getYear();
        if (yearStart != yearEnd && yearEnd != Calendar.getInstance().get(Calendar.YEAR)) {
            throw new BadRequestException("Please register day off this year!");
        }
        Date date = new Date(System.currentTimeMillis());
        dayOffDTO.setCreateAt(date);
        dayOffDTO.setStatus(1);
        return dayOffRepository.save(dayOffDTODayOffConverter.convert(dayOffDTO));
    }

    public void deleteDayOff(int id) {
        DayOff dayOff = dayOffRepository.findById(id);
        if (dayOff == null) {
            throw new NotFoundException("Day off not found!");
        }
        dayOffRepository.delete(dayOff);
    }

    public List<DayOffDTO> pagination(int sizeList, int indexPage, String valueSearch) {
        if(indexPage<1){
            throw new BadRequestException("Page size must not be less than one");
        }
        List<DayOffDTO> dayOffDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(sizeList, indexPage);
        for (DayOff dayOff : dayOffRepository.searchDayOff(valueSearch, pageable)) {
            dayOffDTOS.add(dayOffDayOffDTOConverter.convert(dayOff));
        }
        return dayOffDTOS;
    }

    public DayOff acceptDayOff(int id){
        DayOff dayOff=dayOffRepository.findById(id);
        if(dayOff==null){
            throw new NotFoundException("Day off not found");
        }
        Status status=statusRepository.findByName(Constants.APPROVED);
        dayOff.setStatus(status);
        return dayOffRepository.save(dayOff);
    }

    public DayOff rejectedDayOff(int id){
        DayOff dayOff=dayOffRepository.findById(id);
        if(dayOff==null){
            throw new NotFoundException("Day off not found");
        }
        Status status=statusRepository.findByName(Constants.REJECTED);
        dayOff.setStatus(status);
        return dayOffRepository.save(dayOff);
    }
}
