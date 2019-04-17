
package com.truongpx.ckcportfolio.core.interactor


import com.truongpx.ckcportfolio.AndroidTest
import com.truongpx.ckcportfolio.core.exception.Failure
import com.truongpx.ckcportfolio.core.functional.Either
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Test

class UseCaseTest : AndroidTest() {

    private val TYPE_TEST = "Test"
    private val TYPE_PARAM = "ParamTest"

    private val useCase = MyUseCase()

    @Test fun `running use case should return 'Either' of use case type`() {
        val params = MyParams(TYPE_PARAM)
        val result = runBlocking { useCase.run(params) }

    }

    @Test fun `should return correct data when executing use case`() {
        var result: Either<Failure, MyType>? = null

        val params = MyParams("TestParam")
//        val onResult = { myResult: Either<Failure, MyType> -> result = myResult }

        runBlocking { useCase(params) {myResult -> result = myResult} }

        result shouldEqual Either.Right(MyType(TYPE_TEST))
    }

    data class MyType(val name: String)
    data class MyParams(val name: String)

    private inner class MyUseCase : UseCase<MyType, MyParams>() {
        override suspend fun run(params: MyParams) = Either.Right(
            MyType(TYPE_TEST)
        )
    }
}