package cz.talich.ma.library.mvvm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class AbstractViewModel<S : AbstractViewModel.State>(initialState: S) : ViewModel() {
    interface State

    private val mutableStates: MutableStateFlow<S> = MutableStateFlow(initialState)
    val states: StateFlow<S> = mutableStates.asStateFlow()

    var state: S
        get() = states.value
        protected set(value) {
            mutableStates.value = value
        }

    fun AbstractViewModel<S>.launchInVmScope(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(
        context = context,
        block = block
    )
}
