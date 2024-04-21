package ua.kpi.its.lab.rest.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router
import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.RouteRequest
import ua.kpi.its.lab.rest.svc.TrainService
import ua.kpi.its.lab.rest.svc.RouteService
import java.text.SimpleDateFormat

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .modulesToInstall(KotlinModule.Builder().build())

        converters
            .add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    @Bean
    fun functionalRoutes(trainService: TrainService): RouterFunction<*> = router {
        fun wrapNotFoundError(call: () -> Any): ServerResponse {
            return try {
                val result = call()
                ok().body(result)
            }
            catch (e: IllegalArgumentException) {
                notFound().build()
            }
        }

        "/fn".nest {
            "/trains".nest {
                GET("") {
                    ok().body(trainService.read())
                }
                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { trainService.readById(id) }
                }
                POST("") { req ->
                    val product = req.body<TrainRequest>()
                    ok().body(trainService.create(product))
                }
                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val product = req.body<TrainRequest>()
                    wrapNotFoundError { trainService.updateById(id, product) }
                }
                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { trainService.deleteById(id) }
                }
            }

        }
    }

    @Bean
    fun routeFunctionalRoutes(routeService: RouteService): RouterFunction<*> = router {
        fun wrapNotFoundError(call: () -> Any): ServerResponse {
            return try {
                val result = call()
                ok().body(result)
            } catch (e: IllegalArgumentException) {
                notFound().build()
            }
        }

        "/fn".nest {
            "/routes".nest {
                GET("") {
                    ok().body(routeService.read())
                }
                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { routeService.readById(id) }
                }
                POST("") { req ->
                    val product = req.body<RouteRequest>()
                    ok().body(routeService.create(product))
                }
                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val product = req.body<RouteRequest>()
                    wrapNotFoundError { routeService.updateById(id, product) }
                }
                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { routeService.deleteById(id) }
                }
            }
        }
    }
}