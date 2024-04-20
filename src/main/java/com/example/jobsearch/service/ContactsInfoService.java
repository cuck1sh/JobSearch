package com.example.jobsearch.service;

import com.example.jobsearch.dto.resume.InputContactInfoDto;

public interface ContactsInfoService {
    InputContactInfoDto getContactInfoByResumeId(int resumeId);

    void createContactInfo(InputContactInfoDto contacts, Integer resumeId);

    void updateContactInfo(InputContactInfoDto contacts, Integer resumeId);
    Boolean isContactsInSystem(int typeId, int resumeId);
}
