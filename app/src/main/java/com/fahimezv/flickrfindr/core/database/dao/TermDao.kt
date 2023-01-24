package com.fahimezv.flickrfindr.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fahimezv.flickrfindr.core.database.model.TermEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(termEntity: TermEntity)

    @Query(value = """
        SELECT * FROM term
        ORDER BY timestamp DESC
        LIMIT :count
    """)
    fun getTerms(count: Int): Flow<List<TermEntity>>

}