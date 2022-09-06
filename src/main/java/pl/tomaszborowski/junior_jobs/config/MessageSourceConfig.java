package pl.tomaszborowski.junior_jobs.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceConfig {

    private static final String VALIDATION_MESSAGES_FILE_PATH = "classpath:securityValidationMessages";
    private static final String VALIDATION_MESSAGES_ENCODING = "UTF-8";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename(VALIDATION_MESSAGES_FILE_PATH);
        messageSource.setDefaultEncoding(VALIDATION_MESSAGES_ENCODING);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
