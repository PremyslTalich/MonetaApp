package cz.talich.ma.library.usecase.domain

operator fun <O> UseCase<Unit, O>.invoke(): O = this(Unit)

suspend operator fun <O> SuspendedUseCase<Unit, O>.invoke(): O = this(Unit)
