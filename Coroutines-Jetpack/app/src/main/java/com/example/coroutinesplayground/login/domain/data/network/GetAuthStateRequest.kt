package com.example.coroutinesplayground.login.domain.data.network

import com.example.coroutinesplayground.login.domain.model.AuthState
import com.example.coroutinesplayground.networking.NetworkHelper
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

class GetAuthStateRequest(
) {
    suspend fun execute (username: String, password: String): Result<AuthState> {
        val authApi = NetworkHelper.getInstance().create<GetAuthStateRequestCall>()
        try {
            //Fake login
            if (username.endsWith("@gmail.com")) {
                if (password == "Passw0rd!" || password == "Secure!123" || password == "1234") {
                    delay(1500)
                    return Result.success(AuthState("1asd21dasd2dad", ""))
                }
            }
            val response = authApi.auth(GetAuthStateRequestCall.RequestBody(username = username, password = password)).execute()
            if (response.isSuccessful) {
                response.body()?.sessionToken?.let {
                    return Result.success(AuthState(it, ""))
                }
                return Result.failure(Exception())
            } else {
                return Result.failure(Exception(response.errorBody().toString()))
            }
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }
}
internal interface  GetAuthStateRequestCall {
    @POST("auth/login")
    suspend fun auth(@Body requestBody: RequestBody): Call<ResponseBody>

    class RequestBody(
        val username: String,
        val password: String
    )

    /**
     * This class represents the response-body after requesting the Opaque Payment Card (OPC).
     *
     * @param opaquePaymentCard the Base64-encoded OPC
     * @constructor creates a new instance of [ResponseBody]
     */
    class ResponseBody(
        val sessionToken: String?,
        val error: String?
    )
}