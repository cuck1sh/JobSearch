//package com.example.jobsearch.dao;
//
//import com.example.jobsearch.model.ContactType;
//import lombok.AllArgsConstructor;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class ContactTypesDao {
//
//    private final JdbcTemplate template;
//
//    public ContactType getContactTypeById(int id) {
//        String sql = """
//                select * from contact_types
//                where id = ?;
//                """;
//
//        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ContactType.class), id);
//    }
//
//    public Boolean isTypeInBase(int id) {
//        String sql = """
//                select case
//                when exists(select *
//                            from CONTACT_TYPES
//                            where id = ?)
//                    then true
//                else false
//                end;
//                """;
//
//        return template.queryForObject(sql, Boolean.class, id);
//    }
//
//    public Integer getTypeByName(String type) {
//        String sql = """
//                select id from contact_types
//                where lcase(type) = ?;
//                """;
//        return template.queryForObject(sql, Integer.class, type.strip().toLowerCase());
//    }
//}
