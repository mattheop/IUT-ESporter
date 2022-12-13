package com.example.sae.controller;

import com.example.sae.models.AppUserRole;
import com.example.sae.models.DashboardFunction;
import com.example.sae.services.DashboardFunctionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Set;

public class ESporterDashboard {

    @ModelAttribute("dashboardFunctions")
    public List<DashboardFunction> getDashboardFunction(Authentication authentication, DashboardFunctionService dashboardFunctionService) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        return dashboardFunctionService.getFuncOnRole(AppUserRole.valueOf(roles.stream().findFirst().orElseThrow(() -> new RuntimeException(""))));
    }
}
