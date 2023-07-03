package org.akip.resolver;

import java.util.List;

public interface UserResolver {
    AkipUserDTO getUser(String login);

    List<AkipUserDTO> getUsers(List<String> login);
}
