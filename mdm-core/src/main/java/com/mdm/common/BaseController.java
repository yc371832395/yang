package com.mdm.common;

import com.mdm.cache.LocalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    private MessageSource messageSource;
//    protected LocalConfig localConfig;

//    @Autowired
//    public void setLocalConfig(LocalConfig localConfig) {
//        this.localConfig = localConfig;
//    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object[] objects) {
        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, new Object[]{}, LocaleContextHolder.getLocale());
    }
}
