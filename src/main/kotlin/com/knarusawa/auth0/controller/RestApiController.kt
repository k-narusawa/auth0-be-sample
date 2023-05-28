package com.knarusawa.auth0.controller

import com.knarusawa.auth0.domain.Message
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api")
@CrossOrigin(origins = ["*"])
class RestApiController {
  @GetMapping("public")
  fun publicEndpoint(): Message {
    return Message(
      message = "All good. You DO NOT need to be authenticated to call /api/public."
    )
  }

  @GetMapping("private")
  fun privateEndpoint(): Message {
    return Message(
      message = "All good. You can see this because you are Authenticated."
    )
  }
}