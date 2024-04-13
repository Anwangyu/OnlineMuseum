package com.school.onlinemuseums.server;

import com.school.onlinemuseums.domain.dto.LoginDto;

public interface IAuth_Service {
    String login(LoginDto loginDto);

}
