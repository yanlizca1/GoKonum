package com.emrah.gokonum.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.emrah.gokonum.data.model.SavedRoute
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object GPXExporter {

    fun exportRouteToGPX(context: Context, route: SavedRoute): Boolean {
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.getDefault())
            val fileName = "GoKonum_${route.name.replace(" ", "_")}_${dateFormat.format(route.date)}.gpx"

            val file = File(context.getExternalFilesDir(null), fileName)

            FileWriter(file).use { writer ->
                writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                writer.append("<gpx version=\"1.1\" creator=\"GoKonum\">\n")
                writer.append("  <trk>\n")
                writer.append("    <name>${route.name}</name>\n")
                writer.append("    <trkseg>\n")

                route.locations.forEach { loc ->
                    writer.append("      <trkpt lat=\"${loc.latitude}\" lon=\"${loc.longitude}\">\n")
                    writer.append("        <name>${loc.name}</name>\n")
                    if (loc.note.isNotEmpty()) {
                        writer.append("        <desc>${loc.note}</desc>\n")
                    }
                    writer.append("      </trkpt>\n")
                }

                writer.append("    </trkseg>\n")
                writer.append("  </trk>\n")
                writer.append("</gpx>")
            }

            // Dosyayı paylaş
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/gpx+xml"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "GPX Dosyasını Paylaş"))

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
