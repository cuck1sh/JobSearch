package com.example.jobsearch.model;

import com.example.jobsearch.enums.ContactTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactType {
    private Integer id;
    private ContactTypes type;
}
