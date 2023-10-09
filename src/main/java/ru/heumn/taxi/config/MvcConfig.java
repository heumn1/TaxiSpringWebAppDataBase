package ru.heumn.taxi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.heumn")
public class MvcConfig implements WebMvcConfigurer {


}
