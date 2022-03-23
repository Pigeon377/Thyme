# Thyme

Delicate Web Framework

base on package `com.sun.net.httpserver`

|ू･ω･` )

## Quick Start

```scala

import thyme.boot.ThymeApplication
import thyme.dsl.rest.Get.get
import thyme.response.{Complete, ContentType, Entity}
import thyme.dsl.Path.*
import thyme.dsl.ContextParam.*
import thyme.dsl.rest.Post.post
import thyme.dsl.Middleware.middleware

object Main {
  val route =
    path("/api") {
      get { () =>
        Complete(200, Entity(contentType = ContentType.`application/json`, responseBody = "{name:1}"))
      }
    } ~ path("/") {
      post {
        () =>
          Complete(200, Entity(contentType = ContentType.`application/json`, responseBody = "{name:114}"))
      }
    } ~ path("/apis") {
      middleware(context => context.header.contains("Pass")) {
        get(parameter("name").as[Boolean]) { name =>
          println(name)
          Complete(200, Entity(contentType = ContentType.`application/json`, responseBody = s"{$name:114}"))
        }
      }
    }

  ThymeApplication
      .create()
      .mount()
      .run(9090)
}
```