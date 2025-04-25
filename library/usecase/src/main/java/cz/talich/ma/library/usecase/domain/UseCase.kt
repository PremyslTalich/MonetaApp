package cz.talich.ma.library.usecase.domain

interface UseCase<in I, out O> {
    operator fun invoke(params: I): O
}

interface SuspendedUseCase<in I, out O> {
    suspend operator fun invoke(params: I): O
}
