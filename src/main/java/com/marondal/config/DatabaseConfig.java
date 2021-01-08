package com.marondal.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
// interface(mapper)�� �ִ� ��Ű�� ���
@MapperScan(basePackages="com.marondal.*")
public class DatabaseConfig {
	 /**
     * Session Factory ����
     */
    @Bean()
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // �������� ��ϵ� ��Ű�� ������ dao Ŭ������ ��ĵ�մϴ�.
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
 
        // �����ʿ� �ִ� ���ҽ� ��ο� ��(src/main/resources)      
        sessionFactory.setMapperLocations(resolver.getResources(
            // ���� ������ �� xml ��Ű�� ���
            "classpath:mappers/*Mapper.xml"
        ));
 
        // Value Object�� ������ ���� package ���
        sessionFactory.setTypeAliasesPackage( "com.marondal.*" );
        return sessionFactory.getObject();
    }
 
    /**
     * Mybatis template ����
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        // underscore�� camelCase�� ��Ī : ��) user_id -> userId
        sqlSessionTemplate.getConfiguration().setMapUnderscoreToCamelCase(true);
        // Insert�� �����Ǵ� pk�� bean���� ��ȯ
        sqlSessionTemplate.getConfiguration().setUseGeneratedKeys(true);
        return sqlSessionTemplate;
    }
}
