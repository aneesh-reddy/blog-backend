package com.aneesh.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.aneesh.blog.security.CustomUserDetailsService;
import com.aneesh.blog.security.JwtAuthenticationEntryPoint;
import com.aneesh.blog.security.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig  {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	   public static final String[] PUBLIC_URLS = { "/v3/api-docs", "/v2/api-docs",
	            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"

	    };

	
	// creating filter chain
	@Bean
	
   public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
   {
	 httpSecurity
	            .csrf().disable()
	            .authorizeHttpRequests()
	            .requestMatchers("/").permitAll()
	            .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
	            .requestMatchers(HttpMethod.GET,"/v3/api-docs").permitAll()
//	            .requestMatchers("/<context-path>/**")
                .anyRequest()
	            .authenticated()
	            .and()
	            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	            .and()
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 
	  httpSecurity.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);	            
	 
	 return httpSecurity.build();
	 
   }
	
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }
	
	//password encoder
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
     public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception
	 {
	    return configuration.getAuthenticationManager();
	 }
	 
	 
}