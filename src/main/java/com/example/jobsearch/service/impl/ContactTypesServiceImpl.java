package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ContactTypesDao;
import com.example.jobsearch.dto.ContactTypeDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.model.ContactType;
import com.example.jobsearch.service.ContactTypesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactTypesServiceImpl implements ContactTypesService {

    private final ContactTypesDao contactTypesDao;

    @Override
    public ContactTypeDto getContactTypeById(int id) {
        ContactType type = contactTypesDao.getContactTypeById(id);
        return ContactTypeDto.builder().type(type.getType()).build();
    }

    @Override
    public Integer isTypeInBase(int id) {
        if (contactTypesDao.isTypeInBase(id)) {
            return id;
        }
        throw new ResumeNotFoundException("Несуществующий айди контактной информации");
    }
}
