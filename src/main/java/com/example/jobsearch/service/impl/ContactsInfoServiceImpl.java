package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.resume.InputContactInfoDto;
import com.example.jobsearch.model.ContactsInfo;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.repository.ContactsInfoRepository;
import com.example.jobsearch.service.ContactTypesService;
import com.example.jobsearch.service.ContactsInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContactsInfoServiceImpl implements ContactsInfoService {

    private final ContactsInfoRepository contactsInfoRepository;
    private final ContactTypesService contactTypesService;

    @Override
    public InputContactInfoDto getContactInfoByResumeId(int resumeId) {
        List<ContactsInfo> info = contactsInfoRepository.findContactsInfoByResumeId(resumeId);

        Map<String, String> contactsMap = new HashMap<>();

        info.forEach(e -> contactsMap.put(contactTypesService.getContactTypeById(e.getType().getId()).getType(), e.getInfo()));

        InputContactInfoDto inputContacts = InputContactInfoDto.builder()
                .phoneNumber(contactsMap.get("Phone number"))
                .email(contactsMap.get("email"))
                .facebook(contactsMap.get("facebook"))
                .linkedIn(contactsMap.get("linkedIn"))
                .telegram(contactsMap.get("telegram"))
                .build();

        return inputContacts;
    }

    @Override
    public void createContactInfo(InputContactInfoDto contacts, Integer resumeId) {
        if (!contacts.getPhoneNumber().isBlank()) {
            contactsInfoRepository.save(ContactsInfo.builder()
                    .type(contactTypesService.getTypeByName("Phone number"))
                    .resume(Resume.builder()
                            .id(resumeId)
                            .build())
                    .info(contacts.getPhoneNumber())
                    .build());
        }
        if (!contacts.getEmail().isBlank()) {
            contactsInfoRepository.save(ContactsInfo.builder()
                    .type(contactTypesService.getTypeByName("Email"))
                    .resume(Resume.builder()
                            .id(resumeId)
                            .build())
                    .info(contacts.getEmail())
                    .build());
        }
        if (!contacts.getFacebook().isBlank()) {
            contactsInfoRepository.save(ContactsInfo.builder()
                    .type(contactTypesService.getTypeByName("Facebook"))
                    .resume(Resume.builder()
                            .id(resumeId)
                            .build())
                    .info(contacts.getFacebook())
                    .build());
        }
        if (!contacts.getLinkedIn().isBlank()) {
            contactsInfoRepository.save(ContactsInfo.builder()
                    .type(contactTypesService.getTypeByName("LinkedIn"))
                    .resume(Resume.builder()
                            .id(resumeId)
                            .build())
                    .info(contacts.getLinkedIn())
                    .build());
        }
        if (!contacts.getTelegram().isBlank()) {
            contactsInfoRepository.save(ContactsInfo.builder()
                    .type(contactTypesService.getTypeByName("Telegram"))
                    .resume(Resume.builder()
                            .id(resumeId)
                            .build())
                    .info(contacts.getTelegram())
                    .build());
        }
    }

    @Override
    public void updateContactInfo(InputContactInfoDto contacts, Integer resumeId) {
        if (!contacts.getPhoneNumber().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Phone number").getId(), resumeId))) {
                contactsInfoRepository.updateBy(contacts.getPhoneNumber(),
                        contactTypesService.getTypeByName("Phone number").getId(),
                        resumeId);
            } else {
                contactsInfoRepository.save(ContactsInfo.builder()
                        .type(contactTypesService.getTypeByName("Phone number"))
                        .resume(Resume.builder()
                                .id(resumeId)
                                .build())
                        .info(contacts.getPhoneNumber())
                        .build());
            }
        }

        if (!contacts.getEmail().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Email").getId(), resumeId))) {
                contactsInfoRepository.updateBy(contacts.getEmail(),
                        contactTypesService.getTypeByName("Email").getId(),
                        resumeId);
            } else {
                contactsInfoRepository.save(ContactsInfo.builder()
                        .type(contactTypesService.getTypeByName("Email"))
                        .resume(Resume.builder()
                                .id(resumeId)
                                .build())
                        .info(contacts.getEmail())
                        .build());
            }
        }

        if (!contacts.getFacebook().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Facebook").getId(), resumeId))) {
                contactsInfoRepository.updateBy(contacts.getFacebook(),
                        contactTypesService.getTypeByName("Facebook").getId(),
                        resumeId);
            } else {
                contactsInfoRepository.save(ContactsInfo.builder()
                        .type(contactTypesService.getTypeByName("Facebook"))
                        .resume(Resume.builder()
                                .id(resumeId)
                                .build())
                        .info(contacts.getFacebook())
                        .build());
            }
        }

        if (!contacts.getLinkedIn().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("LinkedIn").getId(), resumeId))) {
                contactsInfoRepository.updateBy(contacts.getLinkedIn(),
                        contactTypesService.getTypeByName("LinkedIn").getId(),
                        resumeId);
            } else {
                contactsInfoRepository.save(ContactsInfo.builder()
                        .type(contactTypesService.getTypeByName("LinkedIn"))
                        .resume(Resume.builder()
                                .id(resumeId)
                                .build())
                        .info(contacts.getLinkedIn())
                        .build());
            }
        }

        if (!contacts.getTelegram().isBlank()) {
            if (Boolean.TRUE.equals(isContactsInSystem(contactTypesService.getTypeByName("Telegram").getId(), resumeId))) {
                contactsInfoRepository.updateBy(contacts.getTelegram(),
                        contactTypesService.getTypeByName("Telegram").getId(),
                        resumeId);
            } else {
                contactsInfoRepository.save(ContactsInfo.builder()
                        .type(contactTypesService.getTypeByName("Telegram"))
                        .resume(Resume.builder()
                                .id(resumeId)
                                .build())
                        .info(contacts.getTelegram())
                        .build());
            }
        }
    }

    @Override
    public Boolean isContactsInSystem(int typeId, int resumeId) {
        return contactsInfoRepository.existsByTypeIdAndResumeId(typeId, resumeId);
    }
}
