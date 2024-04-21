package ua.kpi.its.lab.rest.repo

import org.springframework.data.jpa.repository.JpaRepository
import ua.kpi.its.lab.rest.entity.Train
import ua.kpi.its.lab.rest.entity.Route

interface TrainRepository : JpaRepository<Train, Long> {
    // додаткові методи, якщо потрібно
}

interface RouteRepository : JpaRepository<Route, Long> {
    // додаткові методи, якщо потрібно
}

// Your code here