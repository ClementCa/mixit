package mixit.controller

import mixit.model.SponsorshipLevel.*
import mixit.repository.EventRepository
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Controller
class GlobalController(val repository: EventRepository) : RouterFunction<ServerResponse> {

    override fun route(req: ServerRequest) = route(req) {
        accept(TEXT_HTML).apply {
            GET("/") { homeView() }
        }
        resources("/**", ClassPathResource("static/"))
    }

    fun homeView() = repository.findOne("mixit17")
            .then { events ->
                val sponsors = events.sponsors.groupBy { it.level }
                ok().render("home", mapOf(
                        Pair("sponsors-gold", sponsors[GOLD]),
                        Pair("sponsors-silver", sponsors[SILVER]),
                        Pair("sponsors-hosting", sponsors[HOSTING]),
                        Pair("sponsors-lanyard", sponsors[LANYARD]),
                        Pair("sponsors-party", sponsors[PARTY])
                ))
            }
}
