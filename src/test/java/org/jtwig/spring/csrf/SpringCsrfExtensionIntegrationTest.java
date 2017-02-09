package org.jtwig.spring.csrf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.boot.config.JtwigViewResolverConfigurer;
import org.jtwig.web.servlet.JtwigRenderer;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpringCsrfExtensionIntegrationTest {

    @Test
    public void example() throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(SampleController.class, "--server.port=0");
        context.start();

        String url = "http://localhost:" + ((AnnotationConfigEmbeddedWebApplicationContext) context).getEmbeddedServletContainer().getPort();
        Document doc = Jsoup.connect(url).get();
        assertThat(doc.select("input").attr("type"), is("hidden"));
        assertThat(doc.select("input").attr("name"), is("_csrf"));

        context.stop();
    }

    @Controller
    @EnableWebMvc
    @EnableAutoConfiguration
    @Configuration
    public static class SampleController extends WebSecurityConfigurerAdapter implements JtwigViewResolverConfigurer {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/*").permitAll();
        }

        @RequestMapping
        public String index () {
            return "index";
        }

        @Override
        public void configure(JtwigViewResolver viewResolver) {
            viewResolver.setRenderer(new JtwigRenderer(EnvironmentConfigurationBuilder.configuration()
                    .extensions().add(new SpringCsrfExtension()).and()
                    .build()));
        }
    }
}
