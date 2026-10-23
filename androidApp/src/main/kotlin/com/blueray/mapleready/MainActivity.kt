package com.blueray.mapleready

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.blueray.mapleready.database.AndroidDatabaseDriverFactory
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val languageCode = getSavedLanguageCode(newBase)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                driverFactory = AndroidDatabaseDriverFactory(
                    context = applicationContext
                ),
                onRestartApp = {
                    recreate()
                }
            )
        }
    }

    private fun getSavedLanguageCode(context: Context): String {
        return try {
            val driverFactory = AndroidDatabaseDriverFactory(context)
            val database = com.blueray.mapleready.database.createDatabase(driverFactory)
            database.practiceQueries.getMetadataValue("app_language").executeAsOneOrNull() ?: "en"
        } catch (e: Exception) {
            "en ${e.printStackTrace()}"
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}
