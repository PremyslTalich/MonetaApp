plugins {
    id("my.android-module")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "BALLDONTLIE_API_KEY",
            value = localProperties
                ?.getProperty("balldontlieApiKey")
                ?.wrapInQuotes()
                ?: throw GradleException("Missing balldontlieApiKey in local.properties")
        )
    }
}

private fun String.wrapInQuotes() = "\"$this\""

dependencies {
    api(projects.library.usecase)

    implementation(projects.library.network)
    implementation(projects.library.test)

    implementationBundleBom(libs.bundles.koin)
    implementationBundleBom(libs.bundles.retrofit)
    implementationBundleBom(libs.bundles.okHttp)

    testImplementation(libs.bundles.test)
}