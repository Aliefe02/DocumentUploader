package com.springRest.DocumentUploader.security;

import com.springRest.DocumentUploader.security.CustomAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.UUID;

public class SecurityUtils {

    public static UUID getAuthenticatedUserId() {
        CustomAuthenticationToken authentication =
                (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return (authentication != null) ? authentication.getUserId() : null;
    }
}
