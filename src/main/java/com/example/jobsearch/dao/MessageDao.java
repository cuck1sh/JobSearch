//package com.example.jobsearch.dao;
//
//import com.example.jobsearch.model.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class MessageDao {
//    private final JdbcTemplate template;
//
//    public List<Message> getMessages(int respond) {
//        String sql = """
//                select * from messages
//                where responded_applicants_id = ?;
//                """;
//
//        return template.query(sql, new BeanPropertyRowMapper<>(Message.class), respond);
//    }
//
//    public void createMessage(Message newMessage) {
//        String sql = """
//                insert into messages(responded_applicants_id, content, timestamp)
//                values ( ?, ?, ? );
//                """;
//        template.update(sql, newMessage.getRespondedApplicantsId(), newMessage.getContent(), newMessage.getTimestamp());
//    }
//}
