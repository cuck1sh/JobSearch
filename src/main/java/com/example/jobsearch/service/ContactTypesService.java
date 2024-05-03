package com.example.jobsearch.service;

import com.example.jobsearch.dto.ContactTypeDto;
import com.example.jobsearch.model.ContactType;

public interface ContactTypesService {
    ContactTypeDto getContactTypeById(int id);
    Integer isTypeInBase(int id);

    ContactType getTypeByName(String type);
}
