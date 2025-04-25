package cz.talich.ma.library.usecase.model


sealed interface Data<out T> {
    data object Loading : Data<Nothing>
    data class Success<out T>(val data: T) : Data<T>
    data class Error(val exception: Exception) : Data<Nothing>
}
