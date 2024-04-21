package ua.kpi.its.lab.rest.svc.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.TrainResponse
import ua.kpi.its.lab.rest.dto.RouteRequest
import ua.kpi.its.lab.rest.dto.RouteResponse
import ua.kpi.its.lab.rest.entity.Train
import ua.kpi.its.lab.rest.entity.Route
import ua.kpi.its.lab.rest.repo.TrainRepository
import ua.kpi.its.lab.rest.repo.RouteRepository
import ua.kpi.its.lab.rest.svc.RouteService
import ua.kpi.its.lab.rest.svc.TrainService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

@Service
class TrainServiceImpl @Autowired constructor(
    private val trainRepository: TrainRepository,
    private val routeRepository: RouteRepository
) : TrainService {

    override fun create(trainRequest: TrainRequest): TrainResponse {
        val train = Train(
            model = trainRequest.model,
            manufacturer = trainRequest.manufacturer,
            type = trainRequest.type,
            commissioningDate = stringToDate(trainRequest.commissioningDate),
            seatCount = trainRequest.seatCount,
            weight = trainRequest.weight,
            hasAirConditioning = trainRequest.hasAirConditioning
        )
        val savedTrain = trainRepository.save(train)
        return trainEntityToDto(savedTrain)
    }

    override fun read(): List<TrainResponse> {
        return trainRepository.findAll().stream()
            .map { trainEntityToDto(it) }
            .collect(Collectors.toList())
    }

    override fun readById(id: Long): TrainResponse {
        val train = getTrainById(id)
        return trainEntityToDto(train)
    }

    override fun updateById(id: Long, trainRequest: TrainRequest): TrainResponse {
        val train = getTrainById(id)
        train.model = trainRequest.model
        train.manufacturer = trainRequest.manufacturer
        train.type = trainRequest.type
        train.commissioningDate = stringToDate(trainRequest.commissioningDate)
        train.seatCount = trainRequest.seatCount
        train.weight = trainRequest.weight
        train.hasAirConditioning = trainRequest.hasAirConditioning

        val updatedTrain = trainRepository.save(train)
        return trainEntityToDto(updatedTrain)
    }

    override fun deleteById(id: Long): TrainResponse {
        val train = getTrainById(id)
        trainRepository.delete(train)
        return trainEntityToDto(train)
    }

    private fun getTrainById(id: Long): Train {
        return trainRepository.findById(id).orElseThrow {
            IllegalArgumentException("Train not found by id = $id")
        }
    }

    private fun trainEntityToDto(train: Train): TrainResponse {
        return TrainResponse(
            id = train.id,
            model = train.model,
            manufacturer = train.manufacturer,
            type = train.type,
            commissioningDate = this.dateToString(train.commissioningDate),
            seatCount = train.seatCount,
            weight = train.weight,
            hasAirConditioning = train.hasAirConditioning,
        )
    }

    private fun dateToString(date: LocalDate): String {
        val formatter = DateTimeFormatter.ISO_DATE
        return date.format(formatter)
    }

    private fun stringToDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ISO_DATE
        return LocalDate.parse(date, formatter)
    }
}

@Service
class RouteServiceImpl @Autowired constructor(
    private val routeRepository: RouteRepository,
    private val trainService: TrainService // Inject TrainService here
) : RouteService {

    override fun create(routeRequest: RouteRequest): RouteResponse {
        // Retrieve the Train entity using the trainId from RouteRequest
        val train = routeRequest.train
        val newTrain = Train(
            model = train.model,
            commissioningDate = this.stringToDate(train.commissioningDate),
            manufacturer = train.manufacturer,
            type = train.type,
            seatCount = train.seatCount,
            weight = train.weight,
            hasAirConditioning = train.hasAirConditioning
        )

        // Now you can use the retrieved Train entity to create the Route
        val route = Route(
            train = newTrain,
            departurePoint = routeRequest.departurePoint,
            destination = routeRequest.destination,
            departureDate = stringToDate(routeRequest.departureDate),
            distance = routeRequest.distance,
            ticketPrice = routeRequest.ticketPrice,
            isCircular = routeRequest.isCircular
        )
        val savedRoute = routeRepository.save(route)
        return routeEntityToDto(savedRoute)
    }

    override fun read(): List<RouteResponse> {
        return routeRepository.findAll().stream()
            .map { routeEntityToDto(it) }
            .collect(Collectors.toList())
    }

    override fun readById(id: Long): RouteResponse {
        val route = getRouteById(id)
        return routeEntityToDto(route)
    }

    override fun updateById(id: Long, routeRequest: RouteRequest): RouteResponse {
        val route = getRouteById(id)
        route.departurePoint = routeRequest.departurePoint
        route.destination = routeRequest.destination
        route.departureDate = stringToDate(routeRequest.departureDate)
        route.distance = routeRequest.distance
        route.ticketPrice = routeRequest.ticketPrice
        route.isCircular = routeRequest.isCircular

        val updatedRoute = routeRepository.save(route)
        return routeEntityToDto(updatedRoute)
    }

    override fun deleteById(id: Long): RouteResponse {
        val route = getRouteById(id)
        routeRepository.delete(route)
        return routeEntityToDto(route)
    }

    private fun getRouteById(id: Long): Route {
        return routeRepository.findById(id).orElseThrow {
            IllegalArgumentException("Route not found by id = $id")
        }
    }

    private fun routeEntityToDto(route: Route): RouteResponse {
        val trainResponse = route.train.let { train ->
            TrainResponse(
                id = train.id,
                model = train.model,
                manufacturer = train.manufacturer,
                type = train.type,
                commissioningDate = this.dateToString(train.commissioningDate),
                seatCount = train.seatCount,
                weight = train.weight,
                hasAirConditioning = train.hasAirConditioning
            )
        }

        return RouteResponse(
            id = route.id,
            departurePoint = route.departurePoint,
            destination = route.destination,
            departureDate = route.departureDate.toString(),
            distance = route.distance,
            ticketPrice = route.ticketPrice,
            isCircular = route.isCircular,
            train = trainResponse
        )
    }

    private fun dateToString(date: LocalDate): String {
        val formatter = DateTimeFormatter.ISO_DATE
        return date.format(formatter)
    }

    private fun stringToDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ISO_DATE
        return LocalDate.parse(date, formatter)
    }
}
