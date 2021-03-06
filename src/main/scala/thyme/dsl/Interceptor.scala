package thyme.dsl

import com.sun.net.httpserver.HttpExchange
import thyme.request.Node
import thyme.response.ContentTypes
import thyme.request.context.{Context, Extractor}
import thyme.response.{Complete, Entity}

object Interceptor {

  def interceptor(interceptors: (Context => Boolean)*)(route: Node*): Seq[Node] = {
    route.map { r =>
      Node(path = r.path, method = r.method, handler =
        (context: Context) =>
          var count = 0
          for (interceptor <- interceptors
               if interceptor(context)) {
            count += 1
          }
          if (count == interceptors.length) {
            r.handler(context)
          } else {
            // refactor
            Complete(404, Entity(ContentTypes.`text/plain`, "Not Found"))
          }
      )
    }
  }
}
