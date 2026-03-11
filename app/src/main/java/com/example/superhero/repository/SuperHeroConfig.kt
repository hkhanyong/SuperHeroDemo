package com.example.superhero.repository

object SuperHeroConfig {
    // 建议：实际项目中可从 buildConfig 或配置文件读取，而非硬编码

    /**
     * ACCESS_TOKEN 需要去自己申请
     */
    const val ACCESS_TOKEN = ""/**"d1c910342a0c8ebf9360dca33044cec2"**/
    const val BASE_URL = "https://superheroapi.com/api/${ACCESS_TOKEN}/"

    const val PAGE_OFFSET = 20

    const val RESPONSE_SUCCESS = "success"
    const val RESPONSE_ERROR = "error"

    private const val CONNECT_TIMEOUT = 10L // 连接超时 10 秒
    private const val READ_TIMEOUT = 15L    // 读取超时 15 秒
}