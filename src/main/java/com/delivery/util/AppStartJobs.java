package com.delivery.util;

import com.delivery.entity.Role;
import com.delivery.entity.User;
import com.delivery.exception.LoginIsBusyException;
import com.delivery.security.jwt.JwtUtils;
import com.delivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AppStartJobs {
    private static final Logger LOG = LoggerFactory.getLogger(AppStartJobs.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void prepareTestUsersForSwagger() {
        LOG.info("Registering test user");

        String mockName = "SwaggerCargoOwner";
        try {
            userService.signUp(
                    mockName,
                    mockName,
                    mockName,
                    mockName,
                    mockName,
                    Role.CARGO_OWNER
            );
        } catch (LoginIsBusyException e) {
            LOG.warn("Login is busy.");
        }

        User testCargoOwner = userService.signIn(mockName, mockName);
        String jwtForTest = jwtUtils.generateJwtToken(testCargoOwner);

        LOG.info("SwaggerToken for Cargo_Owner : Bearer {}", jwtForTest);
    }
}
