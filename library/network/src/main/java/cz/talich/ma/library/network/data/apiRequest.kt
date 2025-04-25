package cz.talich.ma.library.network.data

import cz.talich.ma.library.network.model.MissingBodyException
import cz.talich.ma.library.network.model.UnsuccessfulResponseException
import cz.talich.ma.library.usecase.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <DTO, MODEL> request(
    apiCall: suspend () -> Response<DTO>,
    transform: DTO.() -> MODEL,
): Flow<Data<MODEL>> = flow {
    emit(Data.Loading)

    val result = try {
        apiCall().processApiCall(transform)
    } catch (e: Exception) {
        e.mapFailedApiCall()
    }

    emit(result)
}

fun <DTO, MODEL> Response<DTO>.processApiCall(transform: DTO.() -> MODEL) =
    if (isSuccessful) {
        mapSuccessfulResponse(transform)
    } else {
        mapUnsuccessfulResponse()
    }

private fun <DTO, MODEL> Response<DTO>.mapSuccessfulResponse(transform: DTO.() -> MODEL) =
    when (val body = body()) {
        null -> Data.Error(exception = MissingBodyException())
        else -> {
            try {
                Data.Success(data = transform(body))
            } catch (e: Exception) {
                Data.Error(exception = e)
            }
        }
    }

private fun <DTO> Response<DTO>.mapUnsuccessfulResponse() =
    Data.Error(
        exception = UnsuccessfulResponseException(
            "API call ${raw()} failed with response code ${code()}"
        )
    )

private fun Exception.mapFailedApiCall() =
    Data.Error(exception = this)
