package niemiec.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;

//@Controller
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.cors().and().csrf().disable()
//			.authorizeRequests().antMatchers(HttpMethod.GET, "/").permitAll()
//			.and()
//			.authorizeRequests().antMatchers(HttpMethod.GET, "/reservation").permitAll()
//			.and()
//			.authorizeRequests().antMatchers(HttpMethod.POST, "/reservation/new").permitAll()
//			.anyRequest().authenticated();
		http.cors().and().csrf().disable().authorizeRequests().anyRequest().permitAll();

		// for run h2 console
		http.headers().frameOptions().disable();
	}
}
