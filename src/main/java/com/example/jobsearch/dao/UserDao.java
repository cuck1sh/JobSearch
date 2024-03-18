package com.example.jobsearch.dao;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getUsers() {
        String sql = """
                select * from users;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> getUserById(int id) {
        String sql = """
                select * from users
                where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
                )
        );
    }

    public List<User> getUsersByName(String name) {
        String sql = """
                select * from users
                where LCASE(NAME) = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), name.toLowerCase().strip());
    }

    public List<User> getEmployee(String name, String surname) {
        String sql = """
                select * from users
                where LCASE(NAME) = ? and LCASE(SURNAME) = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(User.class), name.toLowerCase().strip(), surname.toLowerCase().strip());
    }

    public Optional<User> getUserByPhone(String phone) {
        String sql = """
                select * from users
                where PHONE_NUMBER = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), phone)
                )
        );
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = """
                select * from users
                where EMAIL = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), email)
                )
        );
    }

    public Boolean isUserInSystem(String email) {
        String sql = """
                select case
                when exists(select *
                            from USERS
                            where EMAIL = ?)
                    then true
                else false
                end;
                """;

        return template.queryForObject(sql, Boolean.class, email);
    }

    public Boolean isUserInSystem(int id) {
        String sql = """
                select case
                when exists(select *
                            from USERS
                            where id = ?)
                    then true
                else false
                end;
                """;

        return template.queryForObject(sql, Boolean.class, id);
    }

    public void createUser(UserDto user) {
        String sql = """
                insert into users(name, surname, age, email, password, phone_number, avatar, account_type)
                values (:name, :surname, :age, :email, :password, :phone_number, :avatar, :account_type);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("email", user.getAge())
                .addValue("password", user.getPassword())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("avatar", user.getAvatar())
                .addValue("account_type", user.getAccountType()));
    }

    public void changeUserName(int id, String name) {
        String sql = """
                update users
                set name = ?
                where id = ?;
                """;
        template.update(sql, name, id);
    }

    public void changeUserSurname(int id, String surname) {
        String sql = """
                update users
                set surname = ?
                where id = ?;
                """;
        template.update(sql, surname, id);
    }

    public void changeUserAge(int id, int age) {
        String sql = """
                update users
                set age = ?
                where id = ?;
                """;
        template.update(sql, age, id);
    }

    public void changeUserPassword(int id, String password) {
        String sql = """
                update users
                set password = ?
                where id = ?;
                """;
        template.update(sql, password, id);
    }

    public void changeUserPhoneNumber(int id, String PhoneNumber) {
        String sql = """
                update users
                set PHONE_NUMBER = ?
                where id = ?;
                """;
        template.update(sql, PhoneNumber, id);
    }

    public void changeUserAvatar(int id, String path) {
        String sql = """
                update users
                set AVATAR = ?
                where id = ?;
                """;
        template.update(sql, path, id);
    }

    public void saveAvatar(UserAvatar userAvatar) {
        String sql = """
                update users
                set avatar = ?
                where id = ?;
                """;
        template.update(sql, userAvatar.getFileName(), userAvatar.getUserId());
    }


}
