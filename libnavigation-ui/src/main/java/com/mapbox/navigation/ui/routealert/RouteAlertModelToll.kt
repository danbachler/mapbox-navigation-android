package com.mapbox.navigation.ui.routealert

import com.mapbox.geojson.Point

/**
 * Data class for [RouteAlertToll].
 *
 * @property coordinate of the toll route alert
 * @property tollDescription give a description to the toll route alert, it shows under the toll icon
 */
data class RouteAlertModelToll(
    val coordinate: Point,
    val tollDescription: String
)
