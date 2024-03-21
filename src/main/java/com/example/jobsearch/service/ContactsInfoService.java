package com.example.jobsearch.service;

import com.example.jobsearch.dto.ContactsInfoDto;

public interface ContactsInfoService {
    ContactsInfoDto getContactInfoByResumeId(int resumeId);
}
