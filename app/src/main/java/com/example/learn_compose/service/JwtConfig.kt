package com.example.learn_compose.service

import java.util.Date


//import com.auth0.jwt.JWT
//import java.util.Date


//object JwtConfig {
//    private const val secret = "your_secret_key"
//    private const val issuer = "your_issuer"
//    private const val validityInMs = 36_000_00 * 24 // 1 day
//
//    fun generateToken(userId: Long): String {
//        return JWT.create()
//            .withSubject(userId.toString())
//            .withIssuer(issuer)
//            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
//            .sign(Algorithm.HMAC256(secret.toByteArray()))
//    }
//
//    val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secret.toByteArray()))
//        .withIssuer(issuer)
//        .build()
//}