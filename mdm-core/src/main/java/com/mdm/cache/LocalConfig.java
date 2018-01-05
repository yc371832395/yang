package com.mdm.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:config.properties")
public class LocalConfig {
    @Value("${database}")
    private String database;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${mysql_path}")
    private String mysql_path;

    @Value("${system_backup}")
    private String system_backup;

    @Value("${picture_upload}")
    private String picture_upload;

    @Value("${report_download}")
    private String report_download;

    @Value("${sample_path}")
    private String sample_path;

    @Value("${sample_name}")
    private String sample_name;

    @Value("${upload_file_path}")
    private String upload_file_path;

    @Value("${download_file_path}")
    private String download_file_path;

    public String getDownload_file_path() {
        return download_file_path;
    }

    public String getUpload_file_path() {
        return upload_file_path;
    }

    public String getSample_name() {
        return sample_name;
    }

    public String getSample_path() {
        return sample_path;
    }

    public String getSystem_backup() {
        return system_backup;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMysql_path() {
        return mysql_path;
    }

    public String getPicture_upload() {
        return picture_upload;
    }

    public String getReport_download() {
        return report_download;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
