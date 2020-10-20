package com.mapbox.navigation.ui.routealert

import android.content.Context
import androidx.annotation.ColorInt
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.navigation.base.trip.model.alert.RouteAlert
import com.mapbox.navigation.base.trip.model.alert.RouteAlertType

/**
 * Default implementation to show different route alerts, refer to [RouteAlertType]
 *
 * @param context [Context] for retrieving route alert drawable
 * @param style to add source/layer into
 * @param options config which type of route alerts should be handled, refer to [RouteAlertOption]
 */
class MapboxRouteAlert(context: Context, style: Style, private val options: Int = Int.MAX_VALUE) {

    private val routeAlertToll = RouteAlertToll(context, style)

    /**
     * Display supported [RouteAlert] on the map.
     * Which types of [RouteAlert] are supported relies on the [options] value. Only supported
     * [RouteAlert] can be handle and displayed on the map.
     *
     * @param routeAlerts a list of route alerts
     */
    fun onNewRouteAlerts(routeAlerts: List<RouteAlert>) {
        if (options and RouteAlertOption.ROUTE_ALERT_TOLL
            == RouteAlertOption.ROUTE_ALERT_TOLL
        ) {
            routeAlertToll.onNewRouteAlerts(routeAlerts)
        }
    }

    companion object {
        /**
         * Mapbox pre-defined properties line type for route alert.
         * @see [RouteAlertOption.ROUTE_ALERT_TUNNEL], [RouteAlertOption.ROUTE_ALERT_RESTRICTED_AREA]
         */
        fun getMapboxRouteAlertLineLayerProperties(
            @ColorInt color: Int,
            width: Float
        ): Array<PropertyValue<out Any>> =
            arrayOf(
                PropertyFactory.lineColor(color),
                PropertyFactory.lineWidth(width)
            )

        /**
         * Mapbox pre-defined symbol layer properties for route alert.
         * @see [RouteAlertOption.ROUTE_ALERT_COUNTRY_BORDER_CROSSING]
         * [RouteAlertOption.ROUTE_ALERT_REST_STOP]
         * [RouteAlertOption.ROUTE_ALERT_TOLL]
         */
        fun getMapboxRouteAlertSymbolLayerProperties(): Array<PropertyValue<out Any>> = arrayOf(
            PropertyFactory.iconSize(2f),
            PropertyFactory.textAnchor(Property.TEXT_ANCHOR_TOP),
            PropertyFactory.iconAllowOverlap(true),
            PropertyFactory.iconIgnorePlacement(true)
        )

        /**
         * Mapbox pre-defined symbol layer properties for Tunnel name
         * @see [RouteAlertOption.ROUTE_ALERT_TUNNEL]
         */
        fun getMapboxTunnelNameLayerProperties(): Array<PropertyValue<out Any>> = arrayOf(
            PropertyFactory.iconSize(2f),
            PropertyFactory.textAnchor(Property.TEXT_ANCHOR_BOTTOM_RIGHT),
            PropertyFactory.iconAllowOverlap(true),
            PropertyFactory.iconIgnorePlacement(true),
            PropertyFactory.textAllowOverlap(true),
            PropertyFactory.textIgnorePlacement(true)
        )
    }
}

/**
 * Different route alert options
 */
object RouteAlertOption {
    /**
     * [RouteAlertType.CountryBorderCrossing] option
     */
    const val ROUTE_ALERT_COUNTRY_BORDER_CROSSING = 0x1

    /**
     * [RouteAlertType.RestrictedArea] option
     */
    const val ROUTE_ALERT_RESTRICTED_AREA = 0x10

    /**
     * [RouteAlertType.RestStop] option
     */
    const val ROUTE_ALERT_REST_STOP = 0x100

    /**
     * [RouteAlertType.TollCollection] option
     */
    const val ROUTE_ALERT_TOLL = 0x1000

    /**
     * [RouteAlertType.TunnelEntrance] option
     */
    const val ROUTE_ALERT_TUNNEL = 0x10000
}
