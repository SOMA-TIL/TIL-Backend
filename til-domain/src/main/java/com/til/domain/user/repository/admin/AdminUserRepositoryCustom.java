package com.til.domain.user.repository.admin;

import com.til.domain.user.model.User;

public interface AdminUserRepositoryCustom {

    User getByEmail(String email);

}
