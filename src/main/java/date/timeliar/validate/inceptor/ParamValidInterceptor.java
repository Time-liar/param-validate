package date.timeliar.validate.inceptor;

import date.timeliar.validate.annotation.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ParamValidInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(ParamValidInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("start param interceptor");
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Rules rules = method.getMethodAnnotation(Rules.class);
            if (rules == null) {
                return true;
            }
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            logger.debug("path param:{}", pathVariables);
            //operation
        }
        return true;
    }
}
