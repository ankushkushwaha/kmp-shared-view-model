package com.spireon.mobile.kmp_shared_view_model

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform