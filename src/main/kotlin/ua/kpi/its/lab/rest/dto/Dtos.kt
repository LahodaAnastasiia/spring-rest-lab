package ua.kpi.its.lab.rest.dto

data class TrainRequest(
    var model: String,
    var manufacturer: String,
    var type: String,
    var commissioningDate: String,
    var seatCount: Int,
    var weight: Double,
    var hasAirConditioning: Boolean,
)

data class TrainResponse(
    var id: Long,
    var model: String,
    var manufacturer: String,
    var type: String,
    var commissioningDate: String,
    var seatCount: Int,
    var weight: Double,
    var hasAirConditioning: Boolean,
)

data class RouteRequest(
    var train: TrainRequest,
    var departurePoint: String,
    var destination: String,
    var departureDate: String,
    var distance: Double,
    var ticketPrice: Double,
    var isCircular: Boolean
)

data class RouteResponse(
    var id: Long,
    var train: TrainResponse,
    var departurePoint: String,
    var destination: String,
    var departureDate: String,
    var distance: Double,
    var ticketPrice: Double,
    var isCircular: Boolean
)