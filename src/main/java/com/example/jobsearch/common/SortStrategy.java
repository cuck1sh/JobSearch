package com.example.jobsearch.common;

import com.example.jobsearch.dto.vacancy.VacancyDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@Slf4j
public enum SortStrategy {

    BY_EXP_FROM("id") {
        @Override
        public List<VacancyDto> sortingVacancyDtos(List<VacancyDto> vacancyDtos) {
            vacancyDtos.sort(Comparator.comparing(VacancyDto::getExpFrom));
            return vacancyDtos;
        }
    },
    BY_EXP_TO("name") {
        @Override
        public List<VacancyDto> sortingVacancyDtos(List<VacancyDto> vacancyDtos) {
            vacancyDtos.sort(Comparator.comparing(VacancyDto::getExpTo));
            return vacancyDtos;
        }
    },
    BY_SALARY("year") {
        @Override
        public List<VacancyDto> sortingVacancyDtos(List<VacancyDto> vacancyDtos) {
            vacancyDtos.sort(Comparator.comparing(VacancyDto::getSalary));
            return vacancyDtos;
        }
    };

    private final String value;

    SortStrategy(String sortedBy) {
        this.value = sortedBy;
    }

    public static SortStrategy fromString(String sortedBy) {
        for (var e : SortStrategy.values()) {
            if (e.value.equalsIgnoreCase(sortedBy)) {
                return e;
            }
        }
        log.error("Sorted criteria not found");
        return null;
    }

    public abstract List<VacancyDto> sortingVacancyDtos(List<VacancyDto> vacancyDtos);
}
