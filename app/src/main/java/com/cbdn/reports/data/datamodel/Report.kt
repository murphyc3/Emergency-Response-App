package com.cbdn.reports.data.datamodel
import com.google.android.gms.maps.model.LatLng

data class Report(
    // dispatch
    var respondingTruck: String? = null,
    var commandingOfficer: String? = null,
    var datetimeDispatch: Long? = null,
    var emergencyCode: String? = null,
    // location as an address string
    var location: String? = null,
    // on scene
    var datetimeArrival: Long? = null,
    var policePresent: String? = null,
    var ambulancePresent: String? = null,
    var electricCompanyPresent: String? = null,
    var transitPolicePresent: String? = null,
    var victimInfo: List<VictimInfo> = emptyList(),
    var notes: String? = null,
    // submittal
    var datetimeReturn: Long? = null,
    var reportWriter: String? = null,
    var finalized: Boolean = false,
    var next: String? = null,
    var previous: String? = null
){
    constructor(): this(
        respondingTruck = null,
        commandingOfficer = null,
        datetimeDispatch = null,
        emergencyCode = null,
        location = null,
        datetimeArrival = null,
        policePresent = null,
        ambulancePresent = null,
        electricCompanyPresent = null,
        transitPolicePresent = null,
        victimInfo = emptyList(),
        notes = null,
        datetimeReturn = null,
        reportWriter = null,
        finalized = false,
        next = null ,
        previous = null
    )
}