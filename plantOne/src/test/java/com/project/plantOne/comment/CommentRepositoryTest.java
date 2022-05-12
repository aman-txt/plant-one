package com.project.plantOne.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@DataJpaTest
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @MockBean
    private DataSource dataSource;

}

