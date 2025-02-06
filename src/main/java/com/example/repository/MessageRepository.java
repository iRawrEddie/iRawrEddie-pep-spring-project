package com.example.repository;

import java.util.List;
import com.example.entity.Message;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    List<Message> findMessagesByPostedBy(int postedBy);
}
