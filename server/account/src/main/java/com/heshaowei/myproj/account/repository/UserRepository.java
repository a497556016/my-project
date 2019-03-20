package com.heshaowei.myproj.account.repository;

import com.heshaowei.myproj.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
