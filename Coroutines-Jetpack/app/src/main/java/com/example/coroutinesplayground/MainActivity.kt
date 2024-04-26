package com.example.coroutinesplayground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutinesplayground.ui.theme.CoroutinesPlaygroundTheme
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val TAG = javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //showNews()
        //testSequence()
        //testSsuspendedFunctions()
        //testHelloWorld1()
//        testHelloWorld2()
        //testHelloWorld3()
       // testAsyncAwait()
       // testCoroutineName()
        testDispatch()

        setContent {
            CoroutinesPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android Kliment")
                }
            }
        }
    }
    fun showNews () {
        val scope = CoroutineScope(Dispatchers.Default)
        Log.d(TAG, "start loading, Thread: " + Thread.currentThread().name)
        scope.launch {
            val config =  async{ getconfigFromApi()}
            Log.d(TAG, "finished loading config, Thread: " + Thread.currentThread().name)
            val news = async{getNewsFromApi()}
            Log.d(TAG, "finished loading news, Thread: " + Thread.currentThread().name)
            val user =  async{getUserFromApi()}
            Log.d(TAG, "finished loading user, Thread: " + Thread.currentThread().name)
            Log.d(TAG, "finished loading scope")
        }
        Log.d(TAG, "finished functions.")
    }

    fun testSequence() {
        val seq = sequence {
            Log.d(TAG, "Add number 1")
            yield(1)
            Log.d(TAG, "Add number 2")
            yield(2)
            Log.d(TAG, "Add number 3")
            yield(3)
        }

        for (num in seq) {
            Log.d(TAG, "sequence $num")
        }

        val fibonacii = sequence {
            var prev =1
            var prevprev = 1
            yieldAll(listOf(prev, prevprev))
            while (true) {
                val next = prev + prevprev
                yield(next)
                prevprev = prev
                prev = next
            }
        }


        for (i in fibonacii) {
            Log.d(TAG, "number $i")
            if (i > 2000) {
                break
            }
        }


    }

    fun testSsuspendedFunctions() {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            testSuspend()
        }
    }
    suspend fun testSuspend() {
        Log.d(TAG, "BEFORE testSuspend")
        suspendCoroutine<Unit> { continuation ->
            thread {
                Thread.sleep(1000)
                continuation.resume(Unit)
            }
        }
        Log.d(TAG, "AFTER testSuspend")
    }

    suspend fun getconfigFromApi () : String {
        val config = "This is config"
        Thread.sleep(500)
        return config
    }

    suspend fun getNewsFromApi () : String {
        val news = "News number 1"
        Thread.sleep(500)
        return news
    }

    suspend fun getUserFromApi () : String {
        val user = "User data from api"
        Thread.sleep(500)
        return user
    }

    fun testHelloWorld() {
        GlobalScope.launch {
            delay(1000)
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "Hello ")
        Thread.sleep(2000)
        Log.d(TAG, "End of method ")
    }
    fun testHelloWorld1() {
        runBlocking {
            delay(1000)
            Log.d(TAG, "World!")
        }
        runBlocking {
            delay(1000)
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "Hello ")
        Thread.sleep(2000)
        Log.d(TAG, "End of method ")
    }
    fun testHelloWorld2() {
        GlobalScope.launch {
            delay(1000)
            Log.d(TAG, "World!")
        }
        runBlocking {
            Log.d(TAG, "HELLO!")
            Thread.sleep(2000)
        }
        Log.d(TAG, "End of method ")
    }

    fun testHelloWorld3() = runBlocking {
        Log.d(TAG, "Start run blocking!")
        GlobalScope.launch {
            delay(1000)
            Log.d(TAG, "World!")
        }

        Log.d(TAG, "Start second delay!")
        delay(2000)
        Log.d(TAG, "HELLO!")
        Log.d(TAG, "End of method ")
    }

    fun testAsyncAwait() = runBlocking {
        Log.d(TAG, "start async await")
        val val1 = GlobalScope.async {
            delay(1000)
            1
        }
        val val2 = async {
            delay(1000)
            200
        }
        val sum = val1.await() + val2.await()
        Log.d(TAG, "Calculating...")
        Log.d(TAG, "Value calculated is: $sum")
    }
    fun CoroutineScope.log(msg: String) {
        val name = coroutineContext[CoroutineName]?.name
        Log.d(TAG, "[$name] $msg")
    }
    
    fun testCoroutineName() = runBlocking {
        log("Started")
        val v1 = async {
            delay(500)
            log("Running async")
            42
        }
        launch {
            delay(1000)
            log("Running launch")
        }
        log("The answer is: ${v1.await()}")
    }

    fun testDispatch() {
        val singleDispatcher = Executors.newSingleThreadExecutor()
            .asCoroutineDispatcher()
        val poolDispatcher = Executors.newFixedThreadPool(100)
            .asCoroutineDispatcher()
        val scope = CoroutineScope(poolDispatcher)
        scope.launch {
            testDispatchers()
        }
    }

    suspend fun testDispatchers() {
        coroutineScope {
            repeat(1000) {
                launch {
                    List(1000) {
                        Random.nextLong()
                    }.maxOrNull()
                    val threadName = Thread.currentThread().name
                    log("Running on thread: $threadName")
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutinesPlaygroundTheme {
        Greeting("Android")
    }
}