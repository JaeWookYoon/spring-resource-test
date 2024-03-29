package com.jwyoon.www.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

	@Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {

        String key = "jwyoon0717secret_key";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key); // ??Έ??  ? ?¬?©?? ?€
        config.setAlgorithm("PBEWithMD5AndDES"); // ??Έ? ?κ³ λ¦¬μ¦?
        config.setKeyObtentionIterations("1000"); // λ°λ³΅?  ?΄?± ??
        config.setPoolSize("1"); // ?Έ?€?΄?€ pool
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt ??± ?΄??€
        config.setStringOutputType("base64"); //?Έμ½λ© λ°©μ
        encryptor.setConfig(config);
        return encryptor;
    }
}
