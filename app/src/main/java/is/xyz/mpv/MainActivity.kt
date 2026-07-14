package `is`.xyz.mpv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private fun copyDefaultConfig() {
        val file = File(filesDir, "mpv.conf")
        if (!file.exists()) {
            assets.open("mpv.conf").use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        copyDefaultConfig()
        super.onCreate(savedInstanceState)
        Log.v("ssx", "start load mpv.conf ok")
        supportActionBar?.setTitle(R.string.mpv_activity)

        // The original plan was to have the file/doc picker live as fragments
        // under here but that requires refactoring I'm really not willing to figure out now.
        // ~sfan5, 2022-06-30

        if (savedInstanceState == null) {
            with (supportFragmentManager.beginTransaction()) {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, MainScreenFragment())
                commit()
            }
        }
    }
}
