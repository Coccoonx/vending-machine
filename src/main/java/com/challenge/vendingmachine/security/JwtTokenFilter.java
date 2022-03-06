package com.challenge.vendingmachine.security;

import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.VMUserDetailsService;
import com.challenge.vendingmachine.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VMUserDetailsService vmUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        HeaderMapRequestWrapper wrappedRequest = new HeaderMapRequestWrapper(httpServletRequest);

        String token = null;
        String id = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            id = jwtUtil.extractId(token);
        }

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = vmUserDetailsService.loadUserByUsername(id);

//            System.out.println("before test " + userDetails.toString());
            if (jwtUtil.validateToken(token, userDetails)) {

//                System.out.println("after test");
                User user = userRepository.findByUsername(id);
//                System.out.println(user.toString());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                wrappedRequest.addHeader("user", user.getUsername());

            }
        }
        filterChain.doFilter(wrappedRequest, httpServletResponse);
    }


}


class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private final Map<String, String> headerMap = new HashMap<>();

    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    public void removeteHeader(String name) {
        headerMap.remove(name);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            headerValue = headerMap.get(name);
        }
        return headerValue;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(headerMap.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }
}