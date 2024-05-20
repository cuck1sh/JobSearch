package com.example.jobsearch.config;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    protected Log logger = LogFactory.getLog(this.getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);

        UserDto user = authenticatedUserProvider.getAuthUser();
        if (user != null && !user.getEmail().equals("anon")) {
            String cookie = getCookie(request) == null ? "ru" : getCookie(request);
            userService.addL10n(user.getEmail(), cookie);
        }

    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication, request);
        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String getCookie(HttpServletRequest request) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(e -> e.getName().equals("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE"))
                .findFirst();
        return cookie.map(Cookie::getValue).orElse(null);
    }

    protected String determineTargetUrl(final Authentication authentication, HttpServletRequest request) {
        UserDto user = authenticatedUserProvider.getAuthUser();

        Map<String, String> roleTargetUrlMap = new HashMap<>();

        String cookie = getCookie(request);

        if (cookie != null) {
            roleTargetUrlMap.put("EMPLOYEE", "/?lang=" + cookie);
            roleTargetUrlMap.put("EMPLOYER", "/employer/resumes?lang=" + cookie);
        } else {
            roleTargetUrlMap.put("EMPLOYEE", "/?lang=" + user.getUserL10n());
            roleTargetUrlMap.put("EMPLOYER", "/employer/resumes?lang=" + user.getUserL10n());
        }
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}