plugins {
    id("my.compose-module")
}

dependencies {
    implementation(projects.library.dataNba)
    implementation(projects.library.design)
    implementation(projects.library.localization)
    implementation(projects.library.mvvm)
    implementation(projects.library.test)

    implementation(libs.bundles.coil)
    implementation(libs.bundles.glide)
    implementationBundleBom(libs.bundles.koin)
    implementationBundleBom(libs.bundles.compose)

    debugImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(libs.bundles.test)
}
