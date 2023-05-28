package com.knarusawa.auth0.domain

data class ErrorMessage(
  val message: String,
  val code: ErrorCode
) {
  enum class ErrorCode {
    BAD_REQUEST,
    UNAUTHORIZED,
    SERVER_ERROR,
  }
}
