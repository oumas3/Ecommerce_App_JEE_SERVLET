package oum;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;

public class FiltreRechercheNom implements Filter {
 private FilterConfig filterConfig = null;

 public void init(FilterConfig filterConfig) throws ServletException {
     this.filterConfig = filterConfig;
 }

 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
     HttpServletRequest hrequest = (HttpServletRequest) request;
     HttpServletResponse hresponse = (HttpServletResponse) response;
     Cookie[] cookies = hrequest.getCookies();

     String nom = null;
     if (cookies != null) {
         nom = getNomFromCookies(cookies);
     }     request.setAttribute("nom", nom);

     chain.doFilter(request, response); }
 private String getNomFromCookies(Cookie[] cookies) {
     for (Cookie cookie : cookies) {
         if (cookie.getName().equals("nom")) {
             return cookie.getValue();
         }
     }
     return null;
 }

 public void destroy() {
     this.filterConfig = null;
 }
}

