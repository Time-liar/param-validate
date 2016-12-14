package date.timeliar.validate.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import date.timeliar.validate.annotation.Rule;
import date.timeliar.validate.annotation.Rules;
import date.timeliar.validate.enums.ParamPosition;
import date.timeliar.validate.exception.ValidateException;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Author: TimeLiar
 * @CreateTime: 2016-12-11 11:06
 * @Description: auth-center
 */
public class ValidateUtil {
    public static ParamContainer ValidateParam(HttpServletRequest request, Map pathParam, Rules rules) throws ValidateException {
        ParamContainer container = new ParamContainer();
        for (Rule rule : rules.value()) {
            switch (rule.type()) {
                case ARRAY: {
                    String[] array = request.getParameterValues(rule.name());
                    if (rule.min() > 0) {
                        if (rule.min() > array.length) {
                            throw new ValidateException(rule.name(), null);
                        }
                    }
                    if (rule.max() > 0) {
                        if (rule.max() < array.length) {
                            throw new ValidateException(rule.name(), null);
                        }
                    }
                    container.setArray(rule.name(), array);
                    break;
                }
                case JSON_OBJECT: {
                    String jsonStr = null;
                    if (rule.position() != ParamPosition.BODY_JSON) {
                        jsonStr = getStringValue(rule, request, pathParam);
                        if (rule.necessary()) {
                            if (jsonStr == null || jsonStr.length() <= 0)
                                throw new ValidateException(null, rule.name());
                        } else {
                            if (jsonStr == null || jsonStr.length() <= 0)
                                continue;
                        }
                    }
                    if (jsonStr == null) {
                        try {
                            jsonStr = IOUtils.toString(request.getInputStream(), Charset.forName("UTF-8"));
                        } catch (IOException e) {
                            throw new ValidateException(null, "body");
                        }
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        container.setObject(rule.name(), mapper.readValue(jsonStr, rule.objectType()));
                    } catch (IOException e) {
                        if (rule.position() != ParamPosition.BODY_JSON) {
                            throw new ValidateException(rule.name(), null);
                        } else {
                            throw new ValidateException("json object", null);
                        }
                    }
                }
                default: {
                    String value = getStringValue(rule, request, pathParam);
                    checkValue(rule, value, container);
                    break;
                }
            }
        }
        return container;
    }

    private static String getStringValue(Rule rule, HttpServletRequest request, Map pathParam) {
        String value = null;
        switch (rule.position()) {
            case BODY_FORM:
            case QUERY:
                value = request.getParameter(rule.name());
                break;
            case HEADER:
                value = request.getHeader(rule.name());
                break;
            case PATH:
                value = (String) pathParam.get(rule.name());
                break;
            case COOKIE: {
                for (Cookie cookie : request.getCookies()) {
                    if (Objects.equals(cookie.getName(), rule.name())) {
                        value = cookie.getValue();
                    }
                }
                break;
            }
            default:
                return null;
        }
        return value;
    }

    private static void checkValue(Rule rule, String value, ParamContainer container) throws ValidateException {
        if (rule.necessary()) {
            if (value == null || value.length() <= 0) {
                throw new ValidateException(null, rule.name());
            }
        } else if (value == null) {
            return;
        }
        if (rule.limit().length > 0 && Arrays.binarySearch(rule.limit(), value) <= -1) {
            throw new ValidateException(rule.name(), null);
        }
        switch (rule.type()) {
            default:
            case STRING: {
                if (rule.min() > 0) {
                    if (rule.min() > value.length()) {
                        throw new ValidateException(rule.name(), null);
                    }
                }
                if (rule.max() > 0) {
                    if (rule.max() < value.length()) {
                        throw new ValidateException(rule.name(), null);
                    }
                }
                Pattern correct = Pattern.compile(rule.correctPattern());
                Pattern wrong = Pattern.compile(rule.wrongPattern());
                if (!correct.matcher(value).matches() || wrong.matcher(value).matches()) {
                    throw new ValidateException(rule.name(), null);
                }

                container.setString(rule.name(), value);
                break;
            }
            case INT: {
                try {
                    int intVal = Integer.parseInt(value);
                    if (rule.min() > 0) {
                        if (rule.min() > intVal) {
                            throw new ValidateException(rule.name(), null);
                        }
                    }
                    if (rule.max() > 0) {
                        if (rule.max() < intVal) {
                            throw new ValidateException(rule.name(), null);
                        }
                    }
                    container.setInt(rule.name(), intVal);
                    break;
                } catch (Exception e) {
                    throw new ValidateException(rule.name(), null);
                }
            }
            case FLOAT: {
                try {
                    double doubleVal = Double.parseDouble(value);
                    if (rule.min() > 0) {
                        if (rule.min() > doubleVal) {
                            throw new ValidateException(rule.name(), null);
                        }
                    }
                    if (rule.max() > 0) {
                        if (rule.max() < doubleVal) {
                            throw new ValidateException(rule.name(), null);
                        }
                    }
                    container.setFloat(rule.name(), doubleVal);
                    break;
                } catch (Exception e) {
                    throw new ValidateException(rule.name(), null);
                }
            }
            case BOOLEAN: {
                try {
                    container.setBoolean(rule.name(), Boolean.parseBoolean(value));
                    break;
                } catch (Exception e) {
                    throw new ValidateException(rule.name(), null);
                }
            }
        }
    }
}
