package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ContactsInfoDao;
import com.example.jobsearch.dto.ContactsInfoDto;
import com.example.jobsearch.dto.resume.InputContactInfoDto;
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
    public void createOrUpdateContactInfo(InputContactInfoDto contacts, Integer resumeId) {
        if (!contacts.getPhoneNumber().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Phone number"), resumeId))) {
                contactsInfoDao.changeContactInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Phone number"))
                        .resumeId(resumeId)
                        .info(contacts.getPhoneNumber())
                        .build());
            } else {
                contactsInfoDao.createContactsInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Phone number"))
                        .resumeId(resumeId)
                        .info(contacts.getPhoneNumber())
                        .build());
            }
        }

        if (!contacts.getEmail().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Email"), resumeId))) {
                contactsInfoDao.changeContactInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Email"))
                        .resumeId(resumeId)
                        .info(contacts.getEmail())
                        .build());
            } else {
                contactsInfoDao.createContactsInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Email"))
                        .resumeId(resumeId)
                        .info(contacts.getEmail())
                        .build());
            }
        }

        if (!contacts.getFacebook().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Facebook"), resumeId))) {
                contactsInfoDao.changeContactInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Facebook"))
                        .resumeId(resumeId)
                        .info(contacts.getFacebook())
                        .build());
            } else {
                contactsInfoDao.createContactsInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Facebook"))
                        .resumeId(resumeId)
                        .info(contacts.getFacebook())
                        .build());
            }
        }

        if (!contacts.getLinkedIn().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("LinkedIn"), resumeId))) {
                contactsInfoDao.changeContactInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("LinkedIn"))
                        .resumeId(resumeId)
                        .info(contacts.getLinkedIn())
                        .build());
            } else {
                contactsInfoDao.createContactsInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("LinkedIn"))
                        .resumeId(resumeId)
                        .info(contacts.getLinkedIn())
                        .build());
            }
        }

        if (!contacts.getTelegram().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Telegram"), resumeId))) {
                contactsInfoDao.changeContactInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Telegram"))
                        .resumeId(resumeId)
                        .info(contacts.getTelegram())
                        .build());
            } else {
                contactsInfoDao.createContactsInfo(ContactsInfo.builder()
                        .typeId(contactTypesService.getTypeByName("Telegram"))
                        .resumeId(resumeId)
                        .info(contacts.getTelegram())
                        .build());
            }
        }
    }

    @Override
    public Boolean isContactsInSystem(int typeId, int resumeId) {
        return contactsInfoDao.isContactsInSystem(typeId, resumeId);
    }
}
