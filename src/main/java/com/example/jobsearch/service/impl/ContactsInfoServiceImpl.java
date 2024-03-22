package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ContactsInfoDao;
import com.example.jobsearch.dto.ContactsInfoDto;
import com.example.jobsearch.model.ContactsInfo;
import com.example.jobsearch.service.ContactTypesService;
import com.example.jobsearch.service.ContactsInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactsInfoServiceImpl implements ContactsInfoService {

    private final ContactsInfoDao contactsInfoDao;
    private final ContactTypesService contactTypesService;

    @Override
    public List<ContactsInfoDto> getContactInfoByResumeId(int resumeId) {
        List<ContactsInfo> info = contactsInfoDao.getContactInfoByResumeId(resumeId);
        List<ContactsInfoDto> infoDtos = new ArrayList<>();
        info.forEach(e -> infoDtos.add(ContactsInfoDto.builder()
                .type(contactTypesService.getContactTypeById(e.getTypeId()))
                .info(e.getInfo())
                .build()));
        return infoDtos;
    }

    @Override
    public void createContactInfo(List<ContactsInfoDto> contactsInfoDto, Integer newResumeKey) {
        if (!contactsInfoDto.isEmpty()) {
            List<ContactsInfo> contactsInfos = new ArrayList<>();
            contactsInfoDto.forEach(e -> contactsInfos.add(ContactsInfo.builder()
                    .typeId(e.getType().getId())
                    .resumeId(newResumeKey)
                    .info(e.getInfo())
                    .build()));

            contactsInfos.forEach(contactsInfoDao::createContactsInfo);
        }
    }

    @Override
    public void changeContactInfo(List<ContactsInfoDto> contactsInfoDtos, int resumeId) {
        if (!contactsInfoDtos.isEmpty()) {
            List<ContactsInfo> contactsInfos = new ArrayList<>();
            contactsInfoDtos.forEach(e -> contactsInfos.add(ContactsInfo.builder()
                    .typeId(e.getType().getId())
                    .resumeId(resumeId)
                    .info(e.getInfo())
                    .build()));

            contactsInfos.forEach(contactsInfoDao::changeContactInfo);
        }
    }
}
