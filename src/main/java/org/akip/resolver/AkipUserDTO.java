package org.akip.resolver;

import java.io.Serializable;

public class AkipUserDTO implements Serializable {
    private String login;
    private String firstName;
    private String email;
    private String langKey;

    public AkipUserDTO() {
    }

    public String getLogin() {return this.login; }

    public void setLogin(String login) {this.login = login; }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
}
