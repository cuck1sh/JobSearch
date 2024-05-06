package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/vacancies")
@RequiredArgsConstructor
public class VacancyRestController {

    //TODO УДАЛИТь ЕСЛИ НЕ НУЖНО

    private final VacancyService vacancyService;

    @PostMapping("search")
    public List<VacancyDto> search(String text) {
        return vacancyService.searchVacancies(text);
    }


//    @GetMapping
//    public ResponseEntity<List<VacancyDto>> getVacancies() {
//        return ResponseEntity.ok(vacancyService.getVacancies());
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<VacancyDto> getVacancyById(@PathVariable int id) {
//        return ResponseEntity.ok(vacancyService.getVacancyById(id));
//    }
//
//    @GetMapping("all/{userId}")
//    public ResponseEntity<List<VacancyDto>> getAllVacanciesByCompany(@PathVariable int userId) {
//        return ResponseEntity.ok(vacancyService.getAllVacanciesByCompany(userId));
//    }
//
//    @GetMapping("{userId}/category")
//    public ResponseEntity<List<VacancyDto>> getVacanciesByCategoryAndUser(@PathVariable int userId, @RequestParam(name = "name") String name) {
//        return ResponseEntity.ok(vacancyService.getVacanciesByCategoryAndUser(userId, name));
//    }

}
