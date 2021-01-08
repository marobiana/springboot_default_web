##  1. sts 설치
## 2. 프로젝트 만들기 
  - https://congsong.tistory.com/12
  - initializer 사이트 + 필요한 것들 체크하고 프로젝트 연다.  
  

## 3. 코드 동작 시키기
### package, 테스트 컨트롤러를 만든다.
### RequestMapping 테스트 수행  (String 리턴 확인)

## 에러가 발생하는 경우
### (1) debug 에러
`To display the conditions report re-run your application with 'debug' enabled.`  
 - 로컬 서버 > open configure 에서  arguments 탭   --debug 파라미터 추가

### (2) 디비 설정 에러
`Failed to determine a suitable driver class`  
*Application.java에 아래 어노테이션 추가 (디비 연동전에 임시로 넣는 코드)
```
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
``` 

## 4. Jackson 추가하기
 - jackson gradle 또는 maven 추가하기
 - 혹시 에러가 난다면 버전을 될 때까지 바꿔본다. 변경 후 **gradle config refresh**

### jackson 추가 후 동작 테스트
 -  map을 리턴하고 json으로 request mapping 되는지 본다.

## 5. window에 mysql 로컬 서버 구축
- https://mainia.tistory.com/5972
- mysql을 다운받고 mysql workbench 프로그램을 설치한 뒤 DB, table을 생성한다.

## 6. Spring boot에서 DB 연동 시키기
### 첫번째 방법
> 3-(2)에서 추가한 임시 코드는 제거한다.  

> *Application.java에 아래 코드를 추가한다.
```
@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sessionFactory.setMapperLocations(res);
		
		return sessionFactory.getObject();
	}
```

### 두번째 방법(이 방법을 권장한다.)
> DB용 설정 클래스를 따로 만든다.
```
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
// interface(mapper)가 있는 패키지 경로
@MapperScan(basePackages="com.marondal.*")
public class DatabaseConfig {
	 /**
     * Session Factory 설정
     */
    @Bean()
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // 리졸버에 등록된 패키지 하위의 dao 클래스를 스캔합니다.
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
 
        // 오른쪽에 있는 리소스 경로에 들어감(src/main/resources)      
        sessionFactory.setMapperLocations(resolver.getResources(
            // 실제 쿼리가 들어갈 xml 패키지 경로
            "classpath:mappers/*Mapper.xml"
        ));
 
        // Value Object를 선언해 놓은 package 경로
        sessionFactory.setTypeAliasesPackage( "com.marondal.*" );
        return sessionFactory.getObject();
    }
 
    /**
     * Mybatis template 설정
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        // underscore를 camelCase로 매칭 : 예) user_id -> userId
        sqlSessionTemplate.getConfiguration().setMapUnderscoreToCamelCase(true);
        // Insert시 생성되는 pk를 bean으로 반환
        sqlSessionTemplate.getConfiguration().setUseGeneratedKeys(true);
        return sqlSessionTemplate;
    }
}
```