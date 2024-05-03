package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.ContactTypeDto;
import com.example.jobsearch.exception.ContactTypeFoundException;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.model.ContactType;
import com.example.jobsearch.repository.ContactTypesRepository;
import com.example.jobsearch.service.ContactTypesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactTypesServiceImpl implements ContactTypesService {

    private final ContactTypesRepository contactTypesRepository;

    @Override
    public ContactTypeDto getContactTypeById(int id) {
        ContactType type = contactTypesRepository.findById(id)
                .orElseThrow(() -> new ContactTypeFoundException("Не найден тип контакта по айди: " + id));
        return ContactTypeDto.builder()
                .id(type.getId())
                .type(type.getType())
                .build();
    }

    @Override
    public Integer isTypeInBase(int id) {
        if (contactTypesRepository.existsById(id)) {
            return id;
        }
        throw new ResumeNotFoundException("Несуществующий айди контактной информации");
    }

    @Override
    public ContactType getTypeByName(String type) {
        return contactTypesRepository.findContactTypeByType(type)
                .orElseThrow(() -> new ContactTypeFoundException("Не найден тип контакта с названием: " + type));
    }
}
