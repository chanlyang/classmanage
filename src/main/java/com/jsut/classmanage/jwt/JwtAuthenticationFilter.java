package com.jsut.classmanage.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if(isProtectedUrl(request)) {
                log.info("request method:{}",request.getMethod());
                if(!request.getMethod().equals("OPTIONS"))
                    request = JwtUtil.validateTokenAndAddUserIdToHeader(request);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isProtectedUrl(HttpServletRequest request) {
        List<String> protectedPaths = new ArrayList<String>();
        protectedPaths.add("/admin/user/info");
        protectedPaths.add("/admin/user/logout");
        protectedPaths.add("/api/notice/queryPageByAccept");
        protectedPaths.add("/api/notice/acceptNotice");
        protectedPaths.add("/api/notice/publicNotice");
        protectedPaths.add("/api/notice/pageNotice");
        protectedPaths.add("/api/notice/acceptUser");
        protectedPaths.add("/api/student/pageList");
        protectedPaths.add("/api/leave/apply");
        protectedPaths.add("/api/leave/myApply");
        protectedPaths.add("/api/leave/pageList");
        protectedPaths.add("/api/leave/approval");
        protectedPaths.add("/api/epidemic/todayInfo");
        protectedPaths.add("/api/epidemic/publishTodayEpidemic");
        protectedPaths.add("/api/epidemic/todayException");
        protectedPaths.add("/api/epidemic/fillDaily");
        protectedPaths.add("/api/epidemic/queryFillDaily");
        protectedPaths.add("/api/fraud/info");
        protectedPaths.add("/api/fraud/punch");
        protectedPaths.add("/api/fraud/publish");
        protectedPaths.add("/api/fraud/pageList");
        protectedPaths.add("/api/fraud/studentPunch");
        protectedPaths.add("/admin/user/updateInfo");






        boolean bFind = false;
        for( String passedPath : protectedPaths ) {
            bFind = pathMatcher.match(passedPath, request.getServletPath());
            if( bFind ) {
                break;
            }
        }
        return bFind;
    }

}