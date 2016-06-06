package org.jtwig.spring.csrf;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.extension.Extension;
import org.jtwig.spring.csrf.model.CSRFNode;
import org.jtwig.spring.csrf.parboiled.CSRFAddonParserProvider;
import org.jtwig.spring.csrf.render.CSRFNodeRender;

public class SpringCsrfExtension implements Extension {
    @Override
    public void configure(EnvironmentConfigurationBuilder configurationBuilder) {
        configurationBuilder.parser()
                .addonParserProviders().add(new CSRFAddonParserProvider()).and()
                .and()
                .render().nodeRenders().add(CSRFNode.class, new CSRFNodeRender())
                ;
    }
}
