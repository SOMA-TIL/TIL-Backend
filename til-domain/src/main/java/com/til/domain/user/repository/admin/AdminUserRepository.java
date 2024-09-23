package com.til.domain.user.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.til.domain.user.model.User;

public interface AdminUserRepository extends JpaRepository<User, Long>, AdminUserRepositoryCustom {

}
