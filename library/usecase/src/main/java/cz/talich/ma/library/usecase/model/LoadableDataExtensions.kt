package cz.talich.ma.library.usecase.model

fun <I, O> Data<I>.mapSuccess(transform: (I) -> O): Data<O> =
    when (this) {
        Data.Loading -> Data.Loading
        is Data.Success -> Data.Success(transform(data))
        is Data.Error -> Data.Error(exception)
    }
