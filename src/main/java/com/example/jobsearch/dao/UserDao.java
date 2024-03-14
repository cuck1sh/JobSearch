package com.example.jobsearch.dao;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.model.User;
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

    public Optional<User> getUserByName(String name) {
        String sql = """
                select * from users
                where LCASE(NAME) = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), name.toLowerCase())
                )
        );
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

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Boolean.class), email);
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
}
