plugins {
    id("my.android-module")
}

dependencies {
    implementation(projects.library.usecase)

    implementationBundleBom(libs.bundles.koin)
    implementationBundleBom(libs.bundles.retrofit)
    implementationBundleBom(libs.bundles.okHttp)
}
