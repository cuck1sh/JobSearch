package com.example.jobsearch.service;

import com.example.jobsearch.dto.ContactsInfoDto;
import com.example.jobsearch.dto.resume.InputContactInfoDto;

import java.util.List;

public interface ContactsInfoService {
    List<ContactsInfoDto> getContactInfoByResumeId(int resumeId);

    void createContactInfo(List<InputContactInfoDto> contacts, Integer resumeId);

    void updateContactInfo(List<InputContactInfoDto> contacts, Integer resumeId);
    Boolean isContactsInSystem(int typeId, int resumeId);
}
