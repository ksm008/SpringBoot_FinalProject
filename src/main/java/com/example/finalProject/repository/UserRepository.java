package com.example.finalProject.repository;

import com.example.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 리스트가 아닌 단일 객체가 필요하기 때문에 List로 받지 않음
    User findByUsername(String username);
}
