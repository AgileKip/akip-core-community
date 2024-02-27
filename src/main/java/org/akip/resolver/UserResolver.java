package org.akip.resolver;

import java.util.List;
import java.util.Set;

public interface UserResolver {
    AkipUserDTO getUser(String login);

    List<AkipUserDTO> getUsersByAuthorities(List<String> authorities);

    List<AkipUserDTO> getUsersByLogins(List<String> logins);
}
