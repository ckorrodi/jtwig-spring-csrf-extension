package org.jtwig.spring.csrf.render;

import org.jtwig.render.RenderRequest;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.renderable.RenderException;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
import org.jtwig.spring.csrf.model.CSRFNode;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.web.servlet.ServletRequestHolder;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;

public class CSRFNodeRender implements NodeRender<CSRFNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, CSRFNode node) {
        HttpServletRequest httpServletRequest = ServletRequestHolder.get();
        CsrfToken csrf = (CsrfToken) httpServletRequest.getAttribute("_csrf");
        if (csrf != null) {
            return new StringRenderable(String.format("<input type=\"hidden\" " +
                    "name=\"%s\" " +
                    "value=\"%s\"/>",
                    csrf.getParameterName(), csrf.getToken()));
        } else {
            throw new RenderException(ErrorMessageFormatter.errorMessage(node.getPosition(), "Unable to retrieve csrf from the request (attribute '_csrf')"));
        }
    }
}
