import com.mn.domain.common.Failure

const val BAD_REQUEST = 400
const val UNAUTHORIZED = 401
const val NOT_FOUND = 404
const val INTERNAL_SERVER = 500

internal fun Int.getServerError(): Failure {
    return when (this) {
        BAD_REQUEST -> Failure.ServerError.BadRequest
        UNAUTHORIZED -> Failure.ServerError.Unauthorized
        NOT_FOUND -> Failure.ServerError.NotFound
        INTERNAL_SERVER -> Failure.ServerError.InternalServerError
        else -> Failure.ServerError.InternalServerError
    }
}