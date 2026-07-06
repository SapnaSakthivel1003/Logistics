package com.cmms.logistics.user_context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String userIdStr = request.getHeader("X-User-Id");
        String username = request.getHeader("X-User-Name");
        String rolesStr =request.getHeader("X-User-Role");


        if (userIdStr != null && !userIdStr.trim().isEmpty()) {
            try {
                Long userId = Long.parseLong(userIdStr.trim());
                UserContext context = new UserContext();
                context.setUserId(userId);
                context.setUsername(username);
                context.setRoles(rolesStr);
                UserContextHolder.setContext(context);

                if (handler instanceof HandlerMethod handlerMethod) {
                    RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);

                    if (requireRole != null) {
                        String[] requiredRoles = requireRole.value();
                        String currentRoles = UserContextHolder.getContext().getRoles();

                        boolean hasAccess = false;
                        if (currentRoles != null) {
                            for (String role : requiredRoles) {
                                if (currentRoles.contains(role)) {
                                    hasAccess = true;
                                    break;
                                }
                            }
                        }
                        if (!hasAccess) {
                            String rolesAllowed = java.util.Arrays.toString(requiredRoles);
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write("Access Denied: Required role from " + rolesAllowed);
                            return false;
                        }
                    }
                }


            } catch (NumberFormatException e) {
                System.err.println("Failed to parse X-User-Id header: " + userIdStr);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        UserContextHolder.clearContext();
    }
}



