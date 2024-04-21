package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.TrainResponse
import ua.kpi.its.lab.rest.dto.RouteRequest
import ua.kpi.its.lab.rest.dto.RouteResponse

interface TrainService {
    /**
     * Creates a new SoftwareProduct record.
     *
     * @param train: The SoftwareProduct instance to be inserted
     * @return: The recently created SoftwareProduct instance
     */
    fun create(train: TrainRequest): TrainResponse

    /**
     * Reads all created SoftwareProduct records.
     *
     * @return: List of created SoftwareProduct records
     */
    fun read(): List<TrainResponse>

    /**
     * Reads a train record by its index.
     * The order is determined by the order of creation.
     *
     * @param id: The index of train record
     * @return: The SoftwareProduct instance at index
     */
    fun readById(id: Long): TrainResponse

    /**
     * Updates a SoftwareProduct record data.
     *
     * @param train: The SoftwareProduct instance to be updated
     * @return: The updated SoftwareProduct record
     */
    fun updateById(id: Long, train: TrainRequest): TrainResponse

    /**
     * Deletes a SoftwareProduct record by its index.
     *
     * @param id: The index of SoftwareProduct record to delete
     * @return: The deleted SoftwareProduct instance at index
     */
    fun deleteById(id: Long): TrainResponse
}

interface RouteService {
    /**
     * Creates a new SoftwareModule record.
     *
     * @param route: The SoftwareModule instance to be inserted
     * @return: The recently created SoftwareModule instance
     */
    fun create(route: RouteRequest): RouteResponse

    /**
     * Reads all created SoftwareModule records.
     *
     * @return: List of created SoftwareModule records
     */
    fun read(): List<RouteResponse>

    /**
     * Reads a SoftwareModule record by its index.
     * The order is determined by the order of creation.
     *
     * @param id: The index of SoftwareModule record
     * @return: The SoftwareModule instance at index
     */
    fun readById(id: Long): RouteResponse

    /**
     * Updates a SoftwareModule record data.
     *
     * @param route: The SoftwareModule instance to be updated
     * @return: The updated SoftwareModule record
     */
    fun updateById(id: Long, route: RouteRequest): RouteResponse

    /**
     * Deletes a SoftwareModule record by its index.
     *
     * @param id: The index of SoftwareModule record to delete
     * @return: The deleted SoftwareModule instance at index
     */
    fun deleteById(id: Long): RouteResponse
}