package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPatchDao {
    @Query("SELECT * FROM saved_patches ORDER BY timestamp DESC")
    fun getAllSavedPatches(): Flow<List<SavedPatchEntity>>

    @Query("SELECT * FROM saved_patches WHERE id = :id LIMIT 1")
    suspend fun getPatchById(id: String): SavedPatchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatch(entity: SavedPatchEntity)

    @Delete
    suspend fun deletePatch(entity: SavedPatchEntity)

    @Query("DELETE FROM saved_patches WHERE id = :id")
    suspend fun deletePatchById(id: String)

    @Query("SELECT * FROM saved_patches WHERE gameTitle LIKE '%' || :query || '%' OR patchVersion LIKE '%' || :query || '%' OR summaryHeadline LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchPatches(query: String): Flow<List<SavedPatchEntity>>
}
