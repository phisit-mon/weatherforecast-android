package com.phisit.weatherforecast.common.core.exception

class NetworkThrowable(
    val error: NetworkError,
    override val message: String? = null
) : Throwable()

enum class NetworkError(val code: Int) {
    Unauthorized(401),
    Forbidden(403),
    NotFound(404),
    InternalServerError(500),
    BadGateWay(502),
    Unavailable(503),
    ResponseNull(1),
    Unknown(0)
}

fun Int.errorType(): NetworkError {
    return NetworkError.values().find {
        it.code == this
    } ?: NetworkError.Unknown
}