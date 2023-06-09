package com.knarusawa.auth0.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {
  @Value("\${auth0.audience}")
  private val audience: String? = null

  @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private val issuer: String? = null

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http.authorizeRequests()
      .mvcMatchers("/api/public").permitAll()
      .mvcMatchers("/api/private").authenticated()
      .mvcMatchers("/api/private-scoped").hasAuthority("SCOPE_read:messages")
      .and().cors()
      .and().oauth2ResourceServer().jwt()
    return http.build()
  }

  @Bean
  fun jwtDecoder(): JwtDecoder {
    val jwtDecoder = JwtDecoders.fromOidcIssuerLocation<JwtDecoder>(issuer) as NimbusJwtDecoder
    val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience!!)
    val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
    val withAudience: OAuth2TokenValidator<Jwt> =
      DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
    jwtDecoder.setJwtValidator(withAudience)
    return jwtDecoder
  }
}