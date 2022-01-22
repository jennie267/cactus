package com.cactus.api.message.CactusMessageApi.config.data;

/**
 * CactusLocation
 * Class: CloudConfigData
 * Created by hapo on 2019-11-06.
 * Description:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Profile("cloud")
@Configuration
@ServiceScan
public class CloudConfigData extends AbstractCloudConfig {

    @Value("${db.mysql.servicename}")
    private String mysqlServiceName;

    @Bean(name = "dsMysql")
    @Primary
    DataSource mysqlDataSource() {

        CloudFactory cloudFactory = new CloudFactory();
        Cloud cloud = cloudFactory.getCloud();
        ServiceInfo serviceInfo = cloud.getServiceInfo(mysqlServiceName);
        String serviceId = serviceInfo.getId();
        return cloud.getServiceConnector(serviceId, DataSource.class, null);

    }

    @Bean(name = "jdbcMysql")
    @Autowired
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("dsMysql") DataSource dsSlave) {
        return new JdbcTemplate(dsSlave);
    }
    
}
