package com.mn.domain.usecase

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onComplete: (Either<Failure, Type>) -> Unit = {}) {
        val job = CoroutineScope(Dispatchers.IO).async {
            run(params)
        }

        CoroutineScope(Dispatchers.Main).launch {
            onComplete(job.await())
        }
    }
}