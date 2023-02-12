package ru.otus.spring.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityUserAuthorityTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @DisplayName("AuthorController - Проверка прав пользователя ")
    @ParameterizedTest(name = "{3} GET {2} {0}.authority({1})")
    @CsvSource({
            "User, READ, /author/all, 200",
            "User, NOT_READ, /author/all, 403",
            "User, WRITE, /author/edit/1, 200",
            "User, NOT_WRITE, /author/edit/1, 403",
            "User, CREATE, /author/create, 200",
            "User, NOT_CREATE, /author/create, 403",
            "User, DELETE, /author/delete/1, 200",
            "User, NOT_DELETE, /author/delete/1, 403"
    })
    void authorControllerCheckingUserAuthorities(String user, String authority, String url, int statusCod) throws Exception {
        MockHttpServletRequestBuilder request = get(url);
        SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor userRequestPostProcessor = user(user)
                .authorities(new SimpleGrantedAuthority(authority));
        mvc.perform(request.with(userRequestPostProcessor))
                .andDo(print())
                .andExpect(status().is(statusCod));
    }

    @DisplayName("GenreController - Проверка прав пользователя ")
    @ParameterizedTest(name = "{3} GET {2} {0}.authority({1})")
    @CsvSource({
            "User, READ, /genre/all, 200",
            "User, NOT_READ, /genre/all, 403",
            "User, WRITE, /genre/edit/1, 200",
            "User, NOT_WRITE, /genre/edit/1, 403",
            "User, CREATE, /genre/create, 200",
            "User, NOT_CREATE, /genre/create, 403",
            "User, DELETE, /genre/delete/1, 200",
            "User, NOT_DELETE, /genre/delete/1, 403"
    })
    void genreControllerCheckingUserAuthorities(String user, String authority, String url, int statusCod) throws Exception {
        MockHttpServletRequestBuilder request = get(url);
        SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor userRequestPostProcessor = user(user)
                .authorities(new SimpleGrantedAuthority(authority));
        mvc.perform(request.with(userRequestPostProcessor))
                .andDo(print())
                .andExpect(status().is(statusCod));
    }
    @DisplayName("BookController - Проверка прав пользователя ")
    @ParameterizedTest(name = "{3} GET {2} {0}.authority({1})")
    @CsvSource({
            "User, READ, /book/all, 200",
            "User, NOT_READ, /book/all, 403",
            "User, WRITE, /book/edit/1, 200",
            "User, NOT_WRITE, /book/edit/1, 403",
            "User, CREATE, /book/create, 200",
            "User, NOT_CREATE, /book/create, 403",
            "User, READ, /book/comments/1, 200",
            "User, NOT_READ, /book/comments/1, 403",
            "User, DELETE, /book/delete/1, 200",
            "User, NOT_DELETE, /book/delete/1, 403"
    })
    @Transactional
    void bookControllerCheckingUserAuthorities(String user, String authority, String url, int statusCod) throws Exception {
        MockHttpServletRequestBuilder request = get(url);
        SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor userRequestPostProcessor = user(user)
                .authorities(new SimpleGrantedAuthority(authority));
        mvc.perform(request.with(userRequestPostProcessor))
                .andDo(print())
                .andExpect(status().is(statusCod));
    }

    @DisplayName("CommentController - Проверка прав пользователя ")
    @ParameterizedTest(name = "{3} GET {2} {0}.authority({1})")
    @CsvSource({
            "User, USER, /comment/edit/1, 200",
            "User, ADMIN, /comment/edit/1, 200",
            "User, MANAGER, /comment/edit/1, 200",
            "User, NOT_WRITE, /comment/edit/1, 403",
            "User, USER, /comment/create/1, 200",
            "User, ADMIN, /comment/create/1, 200",
            "User, MANAGER, /comment/create/1, 200",
            "User, NOT_CREATE, /comment/create/1, 403",
            "User, DELETE, /comment/delete/1, 200",
            "User, NOT_DELETE, /comment/delete/1, 403"
    })
    void commentControllerCheckingUserAuthorities(String user, String authority, String url, int statusCod) throws Exception {
        MockHttpServletRequestBuilder request = get(url);
        SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor userRequestPostProcessor = user(user)
                .authorities(new SimpleGrantedAuthority(authority));
        mvc.perform(request.with(userRequestPostProcessor))
                .andDo(print())
                .andExpect(status().is(statusCod));
    }
}