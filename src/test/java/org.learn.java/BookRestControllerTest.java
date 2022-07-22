package org.learn.java;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
        @EnableJpaRepositories
        @ComponentScan("org.learn.java")
        @ContextConfiguration(classes={BookRestControllerTest.class})
        @WebAppConfiguration
        public class BookRestControllerTest {

        @Autowired
                BookRestController bookRestController;
        @Autowired
                BookRepository bookRepository;

        MockMvc mockMvc;

        @PostConstruct
                public void setup(){
                MockitoAnnotations.initMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(bookRestController).build();
        }

        @Test
        public void checkForController(){
                assertThat(bookRestController).isNotNull();
                assertThat(bookRepository).isNotNull();
        }

        @Test
        public void getAllTest() throws Exception {
                Book book = new Book();
                book.setName("The Book");
                book = bookRepository.save(book);

                this.mockMvc.perform(get("/api/rest/books"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$[0].name").value(book.getName()));

                bookRepository.delete(book);

        }

        @Test
        public void createOrAddTest() throws Exception {
                Book book = new Book();
                book.setName("Best Book");
                ObjectMapper mapper = new ObjectMapper();
                byte[] jsonObjectsBytes = mapper.writeValueAsBytes(book);

                this.mockMvc.perform(post("\"/api/rest/books\"")
                        .content(jsonObjectsBytes)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isCreated())
                        .andReturn();

                List<Book> books = bookRepository.findAll();
                assertThat(books).extracting("name").contains(book.getName());

                bookRepository.delete(books.get(0));
        }

}