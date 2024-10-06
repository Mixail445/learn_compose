package com.example.learn_compose.service

import android.content.Context
import android.util.Log
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.httpMethod
import io.ktor.server.request.receive
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import java.net.BindException

private var server: NettyApplicationEngine? = null
lateinit var savedVerificationCode:String
fun startKtorServer(
    context: Context,
    isStart: Boolean,
) {
    var logger: Logger = LoggerFactory.getLogger("KtorServer")


    if (isStart) {
        try {
            server =
                embeddedServer(Netty, port = 8080) {
                    install(ContentNegotiation) {
                        json()
                    }

                    install(CallLogging) {
                        logger = LoggerFactory.getLogger("KtorServer")
                        level = Level.INFO
                        format { call ->
                            "${call.request.httpMethod.value} ${call.request.uri} - ${call.response.status()}"
                        }
                    }
//                install(Authentication) {
//                    jwt("jwt") {
//                        verifier(JwtConfig.verifier)
//                        validate { credential ->
//                            if (credential.payload.getClaim("id").asLong() != null) {
//                                UserIdPrincipal(credential.payload.getClaim("id").asString())
//                            } else {
//                                null
//                            }
//                        }
//                    }
//                }

                    routing {
                        get("/") {
                            call.respond(title)
                        }

                        get("/meetings") {
                            try {
                                withContext(Dispatchers.IO) {
                                    val meetingsFlow =
                                        AppDatabase.getDatabase(context).meetingDao().getMeetings()
                                    val meetingsList =
                                        meetingsFlow.first().map { it.toMeetingScreenData() }
                                    call.respond(meetingsList)
                                }
                            } catch (e: Exception) {
                                Log.e("KtorService", "Error fetching meetings: ${e.message}")
                                call.respond(
                                    HttpStatusCode.InternalServerError,
                                    "Unable to fetch meetings",
                                )
                            }
                        }

                        post("/postmeet") {
                            try {
                                val meetings = call.receive<List<MeetingScreenData>>()
                                meetings.forEach { meeting ->
                                    AppDatabase
                                        .getDatabase(context)
                                        .meetingDao()
                                        .insert(meeting.toMeetings())
                                }
                                call.respond(HttpStatusCode.Created, meetings)
                            } catch (e: SerializationException) {
                                Log.e("KtorService", "Serialization error: ${e.message}")
                                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
                            } catch (e: Exception) {
                                Log.e("KtorService", "Error processing request: ${e.message}")
                                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
                            }
                        }

                        post("/register") {
                            try {
                                val userPhone = call.receive<UserPhoneNumber>()
                                val existingUser =
                                    AppDatabase
                                        .getDatabase(context)
                                        .meetingDao()
                                        .getUserByPhone(userPhone.phoneNumber)

                                if (existingUser == null) {
                                    val verificationCode = generateVerificationCode()
                                    savedVerificationCode = verificationCode
                                    //change
                                    showNotification(context, true, title = userPhone.phoneNumber,verificationCode)
                                    // sendVerificationCode(userPhone.phoneNumber, verificationCode)

                                    call.respond(
                                        HttpStatusCode.OK,
                                        "Код подтверждения отправлен на номер ${userPhone.phoneNumber}",
                                    )
                                } else {
                                    call.respond(
                                        HttpStatusCode.Conflict,
                                        "Пользователь уже зарегистрирован",
                                    )
                                }
                            } catch (e: Exception) {
                                call.respond(
                                    HttpStatusCode.BadRequest,
                                    "Ошибка при обработке запроса: ${e.localizedMessage}",
                                )
                            }
                        }

                        post("/verify-code") {
                            try {
                                val verificationData = call.receive<VerificationCode>()

                                if (isValidCode(verificationData.code)) {
                                    call.respond(HttpStatusCode.OK, "Код подтвержден")
                                } else {
                                    call.respond(HttpStatusCode.BadRequest, "Неверный код")
                                }
                            } catch (e: Exception) {
                                call.respond(HttpStatusCode.BadRequest, "Ошибка при обработке запроса: ${e.localizedMessage}")
                            }
                        }
                        post("/complete-registration") {
                            try {
                                val userData = call.receive<UserData>()

                                // Логируем полученные данные
                                logger.info("Полученные данные для регистрации: $userData")

                                AppDatabase.getDatabase(context).meetingDao().insert(userData.mapToUser())

                                logger.info("Пользователь зарегистрирован: ${userData.phoneNumber}")
                                call.respond(HttpStatusCode.Created, mapOf("token" to "token", "message" to "Пользователь зарегистрирован"))
                            } catch (e: SerializationException) {
                                logger.error("Ошибка сериализации: ${e.localizedMessage}", e)
                                call.respond(HttpStatusCode.BadRequest, "Неверный формат данных")
                            } catch (e: Exception) {
                                logger.error("Ошибка при регистрации пользователя: ${e.localizedMessage}", e)
                                call.respond(HttpStatusCode.InternalServerError, "Ошибка при обработке запроса")
                            }
                        }
//
//                    authenticate("jwt") {
//                        get("/protected-route") {
//                            call.respondText("Это защищенный маршрут")
//                        }
//                    }
                    }
                }.start(wait = false)
        } catch (e: BindException) {
            Log.e("KtorService", "Порт уже используется: ${e.message}")
        }

        showNotification(context, true)
    } else {
        server?.stop(1000, 5000)
        server = null
        showNotification(context, false)
    }
}

fun generateVerificationCode(): String {
    val code = (1000..9999).random()
    return code.toString()
}

// Проверка кода подтверждения


fun isValidCode(code: String): Boolean = code == savedVerificationCode

val title =
    listOf(
        MeetingScreenData(
            id = 1,
            "Python",
            "Junior",
            "Moscow",
            "13.09.2024",
            true,
            "https://www.mgpu.ru/wp-content/uploads/2021/10/kamera-fotoapparat-contax-devushka-fotograf.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 2,
            "Kotlin",
            "Senior",
            "Saint Petersburg",
            "14.09.2024",
            false,
            "https://bigpicture.ru/wp-content/uploads/2014/12/luchshie-foto-nedeli-v-dek-2014-0.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 3,
            "Java",
            "Junior",
            "Moscow",
            "15.09.2024",
            true,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5y_CQNi9oiqn96_0204tGgLQuUxigGKLe1w&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 4,
            "JavaScript",
            "Mid",
            "Kazan",
            "16.09.2024",
            false,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5y_CQNi9oiqn96_0204tGgLQuUxigGKLe1w&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 5,
            "C++",
            "Senior",
            "Novosibirsk",
            "17.09.2024",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4Emw9u7VlCZBk6slDXWyZJHmLtJBDxMqlcg&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 6,
            "Swift",
            "Junior",
            "Yekaterinburg",
            "18.09.2024",
            image = "https://white-rainbow.ru/upload/resize_cache/iblock/b26/7sf66d03tagu4tqpxywzr9e18325c05v/600_400_1/BSA_3747_Pano_copy.750.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 7,
            "Go",
            "Mid",
            "Nizhny Novgorod",
            "19.09.2024",
            image = "https://production.photoholding.com/images/25165.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 8,
            "Ruby",
            "Senior",
            "Samara",
            "20.09.2024",
            image = "https://cameralabs.org/media/cameralabs/images/daily/2012/09/26/6_c30a5cdd99a4f89dc9a3d22bda29372c.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 9,
            "PHP",
            "Mid",
            "Rostov-on-Don",
            "21.09.2024",
            image = "https://www.interfax.ru/ftproot/photos/photostory/2019/10/16/wild1_700.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 10,
            "TypeScript",
            "Junior",
            "Ufa",
            "22.09.2024",
            image = "https://lh6.googleusercontent.com/proxy/bTQ1KQrF_wm0MI2ImQsXGPdJrlETLmdXDr1dzKGE2-Czl-ww67qAO9YIoRdy5pWysK6ddcT2P7PnBs2BNQ5Vrd01UloG3Ps",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 11,
            "Rust",
            "Mid",
            "Omsk",
            "23.09.2024",
            image = "https://avatars.dzeninfra.ru/get-zen_doc/224467/pub_5fd205bd1177467976109794_5fd206a0fe22070c497a16d0/scale_1200",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 12,
            "Scala",
            "Senior",
            "Krasnoyarsk",
            "24.09.2024",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvQTQD70RPiS77bMJl8DTOeFgggTTjUIcftA&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 13,
            "Perl",
            "Junior",
            "Voronezh",
            "25.09.2024",
            image = "https://www.fotoprizer.ru/img/338889_big.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 14,
            "R",
            "Mid",
            "Krasnodar",
            "26.09.2024",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcUtaK-Rrxl2Jv__omCo84Y5raFDKnIDobPA&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 15,
            "Dart",
            "Senior",
            "Sochi",
            "27.09.2024",
            image = "https://production.photoholding.com/images/25150.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 16,
            "Objective-C",
            "Mid",
            "Khabarovsk",
            "28.09.2024",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHeElOl8JW_xRRsceP7C65hSJ8v-JdOkCGUg&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 17,
            "Lua",
            "Junior",
            "Vladivostok",
            "29.09.2024",
            image = "https://www.interfax.ru/ftproot/photos/photostory/2023/11/30/ph/cm2_700.jpg",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 18,
            "Haskell",
            "Senior",
            "Kaliningrad",
            "30.09.2024",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1pODXfBX2y-AmfQ3teACWAhpp1WxSsnj_hQ&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 19,
            "Elixir",
            "Mid",
            "Chelyabinsk",
            "01.10.2024",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5gp6kQ0sy6vZsvkx_FzoPXskhaBr4bNO9-g&s",
            list = listOf("Python", "Java", "Kotlin"),
        ),
        MeetingScreenData(
            id = 20,
            "F#",
            "Junior",
            "Tolyatti",
            "02.10.2024",
            image = "https://assets.snapedit.app/images/enhance/feat_6.webp",
            list = listOf("Python", "Java", "Kotlin"),
        ),
    )
