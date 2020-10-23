package com.mapbox.navigation.instrumentation_tests.utils.http

import com.mapbox.geojson.Point
import com.mapbox.navigation.testing.ui.http.MockRequestHandler
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

data class MockDirectionsRequestHandler(
    val profile: String,
    val jsonResponse: String,
    val expectedCoordinates: List<Point>?
) : MockRequestHandler {
    override fun handle(request: RecordedRequest): MockResponse? {
        val prefix = """/directions/v5/mapbox/$profile/${expectedCoordinates.parseCoordinates()}"""
        return if (request.path.startsWith(prefix)) {
            MockResponse().setBody(jsonResponse)
        } else {
            null
        }
    }

    private fun List<Point>?.parseCoordinates(): String {
        if (this == null) {
            return ""
        }

        return this.joinToString(";") { "${it.longitude()},${it.latitude()}" }
    }
}
