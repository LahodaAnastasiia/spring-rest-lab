package ua.kpi.its.lab.rest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.TrainResponse
import ua.kpi.its.lab.rest.dto.RouteRequest
import ua.kpi.its.lab.rest.dto.RouteResponse
import ua.kpi.its.lab.rest.svc.TrainService
import ua.kpi.its.lab.rest.svc.RouteService

@RestController
@RequestMapping("/trains")
class TrainController @Autowired constructor(
    private val trainService: TrainService
) {
    @GetMapping(path = ["", "/"])
    fun Trains(): List<TrainResponse> = trainService.read()

    @GetMapping("/{id}")
    fun readTrainBy(@PathVariable("id") id: Long): ResponseEntity<TrainResponse> {
        return wrapNotFound { trainService.readById(id) }
    }

    @PostMapping(path = ["", "/"])
    fun createTrain(@RequestBody trainRequest: TrainRequest): TrainResponse {
        return trainService.create(trainRequest)
    }

    @PutMapping("/{id}")
    fun updateTrain(
        @PathVariable("id") id: Long,
        @RequestBody trainRequest: TrainRequest
    ): ResponseEntity<TrainResponse> {
        return wrapNotFound { trainService.updateById(id, trainRequest) }
    }

    @DeleteMapping("/{id}")
    fun deleteTrain(@PathVariable("id") id: Long): ResponseEntity<TrainResponse> {
        return wrapNotFound { trainService.deleteById(id) }
    }

    fun <T> wrapNotFound(call: () -> T): ResponseEntity<T> {
        return try {
            // call function for result
            val result = call()
            // return "ok" response with result body
            ResponseEntity.ok(result)
        } catch (e: IllegalArgumentException) {
            // catch not-found exception
            // return "404 not-found" response
            ResponseEntity.notFound().build()
        }
    }
}

@RestController
@RequestMapping("/routes")
class RouteController @Autowired constructor(
    private val routeService: RouteService
) {
    @GetMapping(path = ["", "/"])
    fun Routes(): List<RouteResponse> = routeService.read()

    @GetMapping("/{id}")
    fun readRoute(@PathVariable("id") id: Long): ResponseEntity<RouteResponse> {
        return wrapNotFound { routeService.readById(id) }
    }

    @PostMapping(path = ["", "/"])
    fun createRoute(@RequestBody routeRequest: RouteRequest): RouteResponse {
        return routeService.create(routeRequest)
    }

    @PutMapping("/{id}")
    fun updateRoute(
        @PathVariable("id") id: Long,
        @RequestBody routeRequest: RouteRequest
    ): ResponseEntity<RouteResponse> {
        return wrapNotFound { routeService.updateById(id, routeRequest) }
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable("id") id: Long): ResponseEntity<RouteResponse> {
        return wrapNotFound { routeService.deleteById(id) }
    }

    fun <T> wrapNotFound(call: () -> T): ResponseEntity<T> {
        return try {
            // call function for result
            val result = call()
            // return "ok" response with result body
            ResponseEntity.ok(result)
        } catch (e: IllegalArgumentException) {
            // catch not-found exception
            // return "404 not-found" response
            ResponseEntity.notFound().build()
        }
    }
}
