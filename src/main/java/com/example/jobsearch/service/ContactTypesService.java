package com.example.jobsearch.service;

import com.example.jobsearch.dto.ContactTypeDto;

public interface ContactTypesService {
    ContactTypeDto getContactTypeById(int id);
    Integer isTypeInBase(int id);

    Integer getTypeByName(String type);
}
