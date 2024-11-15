package org.akip.groovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component


@Component("messageApi")
class MessageApi {

    @Autowired
    MessageSource messageSource

    String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
