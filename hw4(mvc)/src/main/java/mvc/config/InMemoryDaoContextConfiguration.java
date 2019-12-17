package mvc.config;

import mvc.dao.TodoDao;
import mvc.dao.TodoInMemoryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryDaoContextConfiguration {

    @Bean
    public TodoDao todoDao() {
        return new TodoInMemoryDao();
    }

}
