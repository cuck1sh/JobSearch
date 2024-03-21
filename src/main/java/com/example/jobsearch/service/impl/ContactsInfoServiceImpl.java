package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ContactsInfoDao;
import com.example.jobsearch.dto.ContactsInfoDto;
import com.example.jobsearch.model.ContactsInfo;
import com.example.jobsearch.service.ContactTypesService;
import com.example.jobsearch.service.ContactsInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactsInfoServiceImpl implements ContactsInfoService {

    private final ContactsInfoDao contactsInfoDao;
    private final ContactTypesService contactTypesService;

    @Override
    public ContactsInfoDto getContactInfoByResumeId(int resumeId) {
        ContactsInfo info = contactsInfoDao.getContactInfoByResumeId(resumeId);
        return ContactsInfoDto.builder()
                .type(contactTypesService.getContactTypeById(info.getTypeId()))
                .info(info.getInfo())
                .build();
    }
}
