package com.example.jobsearch.service;

import com.example.jobsearch.dto.ContactsInfoDto;
import com.example.jobsearch.dto.resume.InputContactInfoDto;

import java.util.List;

public interface ContactsInfoService {
    List<ContactsInfoDto> getContactInfoByResumeId(int resumeId);
    void createContactInfo(List<ContactsInfoDto> contactsInfoDto, Integer newResumeKey);

    void changeContactInfo(InputContactInfoDto contacts, int resumeId);
}
