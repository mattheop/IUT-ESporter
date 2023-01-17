package com.example.sae.controller.arbitre;

import com.example.sae.controller.ESporterDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arbitre")
public class ArbitreDashboardController extends ArbitreDashboard {

    @GetMapping()
    public String home() {
        return "arbitre/home";
    }

}
