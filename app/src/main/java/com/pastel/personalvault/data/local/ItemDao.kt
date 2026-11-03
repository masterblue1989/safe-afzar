package com.pastel.personalvault.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pastel.personalvault.data.model.PersonalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM personal_items ORDER BY isFavorite DESC, updatedAt DESC")
    fun getAllItems(): Flow<List<PersonalItem>>

    @Query("SELECT * FROM personal_items WHERE id = :id")
    suspend fun getItemById(id: Long): PersonalItem?

    @Query("""
        SELECT * FROM personal_items 
        WHERE title LIKE '%' || :query || '%' 
           OR primaryValue LIKE '%' || :query || '%' 
           OR notes LIKE '%' || :query || '%'
        ORDER BY isFavorite DESC, updatedAt DESC
    """)
    fun searchItems(query: String): Flow<List<PersonalItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PersonalItem): Long

    @Update
    suspend fun updateItem(item: PersonalItem)

    @Delete
    suspend fun deleteItem(item: PersonalItem)

    @Query("DELETE FROM personal_items WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE personal_items SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)
}
