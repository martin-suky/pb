package cz.suky.pb.server.util;

import cz.suky.pb.server.domain.User;
import cz.suky.pb.server.exception.UserException;
import cz.suky.pb.server.repository.UserRepository;
import cz.suky.pb.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by none_ on 04/13/16.
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Pattern BASIC_AUTHORIZATION = Pattern.compile("Basic (.+)");
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Optional<String> header = Optional.ofNullable(webRequest.getHeader(HttpHeaders.AUTHORIZATION));

        Matcher matcher = BASIC_AUTHORIZATION.matcher(header.orElseThrow(UserException::notAuthorized));
        if (!matcher.matches()) {
            ex();
        }

        byte[] decode = DECODER.decode(matcher.group(1));
        final String decoded = new String(decode, "UTF-8");

        String[] split = decoded.split(":");
        if (split.length != 2) {
            ex();
        }

        Optional<User> user = userService.getUser(split[0], split[1]);

        return user.orElseThrow(UserException::notAuthorized);
    }

    private static void ex() {
        throw UserException.notAuthorized();
    }
}
