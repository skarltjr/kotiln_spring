package com.example.demo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

//@Component
@ConstructorBinding
@ConfigurationProperties("app")
data class AppProperties(
    val host:String
)
