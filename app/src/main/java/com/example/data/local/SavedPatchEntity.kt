package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.PatchAnalysisReport
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity(tableName = "saved_patches")
data class SavedPatchEntity(
    @PrimaryKey
    val id: String,
    val gameTitle: String,
    val genre: String,
    val patchVersion: String,
    val summaryHeadline: String,
    val metaShiftScore: Int,
    val pacingImpact: String,
    val reportJson: String,
    val timestamp: Long
) {
    fun toDomain(): PatchAnalysisReport {
        return try {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(PatchAnalysisReport::class.java)
            adapter.fromJson(reportJson) ?: fallbackReport()
        } catch (e: Exception) {
            fallbackReport()
        }
    }

    private fun fallbackReport(): PatchAnalysisReport {
        return PatchAnalysisReport(
            id = id,
            gameTitle = gameTitle,
            genre = genre,
            patchVersion = patchVersion,
            summaryHeadline = summaryHeadline,
            metaShiftScore = metaShiftScore,
            pacingImpact = pacingImpact,
            rawMarkdownReport = summaryHeadline,
            timestamp = timestamp
        )
    }

    companion object {
        fun fromDomain(report: PatchAnalysisReport): SavedPatchEntity {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(PatchAnalysisReport::class.java)
            val json = adapter.toJson(report)
            return SavedPatchEntity(
                id = report.id,
                gameTitle = report.gameTitle,
                genre = report.genre,
                patchVersion = report.patchVersion,
                summaryHeadline = report.summaryHeadline,
                metaShiftScore = report.metaShiftScore,
                pacingImpact = report.pacingImpact,
                reportJson = json,
                timestamp = report.timestamp
            )
        }
    }
}
