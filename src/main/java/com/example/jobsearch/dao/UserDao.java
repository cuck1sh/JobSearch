//package com.example.jobsearch.dao;
//
//import com.example.jobsearch.dto.user.UserAvatarDto;
//import com.example.jobsearch.model.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.dao.support.DataAccessUtils;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class UserDao {
//    private final JdbcTemplate template;
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    public List<User> getUsers() {
//        String sql = """
//                select * from users;
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
//    }
//
//    public Optional<User> getUserById(int id) {
//        String sql = """
//                select * from users
//                where id = ?;
//                """;
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
//                )
//        );
//    }
//
//    public List<User> getUsersByName(String name) {
//        String sql = """
//                select * from users
//                where LCASE(NAME) = ?;
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(User.class), name.toLowerCase().strip());
//    }
//
//    public List<User> getEmployee(String name, String surname, String email) {
//        String sql = """
//                select * from users
//                where (LCASE(NAME) = ? and LCASE(SURNAME) = ? or EMAIL = ?)
//                and ACCOUNT_TYPE not like 'EMPLOYER';
//                """;
//
//        return template.query(sql,
//                new BeanPropertyRowMapper<>(User.class),
//                name.toLowerCase().strip(),
//                surname.toLowerCase().strip(),
//                email);
//    }
//
//    public List<User> getEmployer(String name) {
//        String sql = """
//                select * from users
//                where LCASE(NAME) = ? and ACCOUNT_TYPE not like 'EMPLOYEE';
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(User.class), name.toLowerCase().strip());
//    }
//
//    public Optional<User> getUserByPhone(String phone) {
//        String sql = """
//                select * from users
//                where PHONE_NUMBER = ?;
//                """;
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        template.query(sql, new BeanPropertyRowMapper<>(User.class), phone)
//                )
//        );
//    }
//
//    public Optional<User> getUserByEmail(String email) {
//        String sql = """
//                select * from users
//                where EMAIL = ?;
//                """;
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        template.query(sql, new BeanPropertyRowMapper<>(User.class), email)
//                )
//        );
//    }
//
//    public Boolean isUserInSystem(String email) {
//        String sql = """
//                select case
//                when exists(select *
//                            from USERS
//                            where EMAIL = ?)
//                    then true
//                else false
//                end;
//                """;
//
//        return template.queryForObject(sql, Boolean.class, email);
//    }
//
//    public Boolean isUserInSystem(int id) {
//        String sql = """
//                select case
//                when exists(select *
//                            from USERS
//                            where id = ?)
//                    then true
//                else false
//                end;
//                """;
//
//        return template.queryForObject(sql, Boolean.class, id);
//    }
//
//    public Integer createUser(User user) {
//        String sql = """
//                insert into users(name, surname, age, email, password, phone_number, account_type)
//                values (:name, :surname, :age, :email, :password, :phone_number, :account_type);
//                """;
//
//        SqlParameterSource parameter = new MapSqlParameterSource()
//                .addValue("name", user.getName())
//                .addValue("surname", user.getSurname())
//                .addValue("age", user.getAge())
//                .addValue("email", user.getEmail())
//                .addValue("password", user.getPassword())
//                .addValue("phone_number", user.getPhoneNumber())
//                .addValue("account_type", user.getAccountType());
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, parameter, keyHolder);
//        return keyHolder.getKeyAs(Integer.class);
//
//    }
//
//    public void changeUser(User user) {
//        String sql = """
//                update users
//                set name = :name, surname = :surname, age = :age, phone_number = :phone_number
//                where id = :id;
//                """;
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
//                .addValue("id", user.getId())
//                .addValue("name", user.getName())
//                .addValue("surname", user.getSurname())
//                .addValue("age", user.getAge())
//                .addValue("phone_number", user.getPhoneNumber()));
//    }
//
//    public void changePassword(User user) {
//        String sql = """
//                update users
//                set password = :password
//                where id = :id;
//                """;
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
//                .addValue("password", user.getPassword())
//        );
//    }
//
//    public void saveAvatar(UserAvatarDto userAvatarDto) {
//        String sql = """
//                update users
//                set avatar = ?
//                where id = ?;
//                """;
//        template.update(sql, userAvatarDto.getFileName(), userAvatarDto.getUserId());
//    }
//}
