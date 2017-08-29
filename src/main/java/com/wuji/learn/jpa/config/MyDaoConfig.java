package com.wuji.learn.jpa.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.wuji.learn.jpa.shiro.PasswordHelper;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.wuji.learn.jpa.dao" })
@PropertySource("classpath:/properties/db.properties")
@ComponentScan(basePackages = { "com.wuji.learn.jpa.dao", "com.wuji.learn.jpa.service" })
public class MyDaoConfig {
	@Value("${jdbc.driver}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		/**
		 * <!-- 最小空闲连接数 --> <property name="minIdle" value="10" /> <!--
		 * 配置连接池初始化大小 --> <property name="initialSize" value="20" /> <!-- 最大连接数
		 * --> <property name="maxActive" value="20" /> <!-- 获取连接等待超时的时间，单位：毫秒
		 * --> <property name="maxWait" value="2000" />
		 */
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(this.driverClassName);
		dataSource.setUrl(this.jdbcUrl);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);
		dataSource.setInitialSize(20);
		dataSource.setMinIdle(10);
		dataSource.setMaxActive(20);
		dataSource.setMaxWait(2000);
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL55Dialect");
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(this.dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.wuji.learn.jpa.model");
		entityManagerFactoryBean.afterPropertiesSet();
		entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return entityManagerFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this.entityManagerFactory());
		return transactionManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public PasswordHelper passwordHelper() {
		return new PasswordHelper();
	}
}
