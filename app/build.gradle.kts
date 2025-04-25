plugins {
    id("my.app-module")
}

dependencies {
    addAllModules(projects.monetaApp)

    implementationBundleBom(libs.bundles.koin)

    implementation(libs.androidx.activity.compose)
}