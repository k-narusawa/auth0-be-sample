package com.knarusawa.auth0.config

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt


class AudienceValidator(private val audience: String) :
  OAuth2TokenValidator<Jwt> {
  override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
    return if (jwt.audience.contains(audience)) {
      OAuth2TokenValidatorResult.success()
    } else {
      val error = OAuth2Error("invalid_token", "The required audience is missing", null)
      OAuth2TokenValidatorResult.failure(error)
    }
  }
}