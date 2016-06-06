package org.jtwig.spring.csrf;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.web.servlet.ServletRequestHolder;
import org.junit.Test;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringCsrfExtensionTest {
    @Test
    public void integrationTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        CsrfToken csrfToken = mock(CsrfToken.class);

        when(request.getAttribute("_csrf")).thenReturn(csrfToken);
        when(csrfToken.getParameterName()).thenReturn("parameterName");
        when(csrfToken.getToken()).thenReturn("token");
        ServletRequestHolder.set(request);

        String result = JtwigTemplate.inlineTemplate("{% csrf %}", configuration()
                .extensions().add(new SpringCsrfExtension()).and()
                .build())
                .render(JtwigModel.newModel());


        assertThat(result, is("<input type=\"hidden\" name=\"parameterName\" value=\"token\"/>"));
    }
}