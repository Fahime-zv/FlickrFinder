package com.fahimezv.flickrfindr.core.data.repository

import com.fahimezv.flickrfindr.core.database.dao.TermDao
import com.fahimezv.flickrfindr.core.database.model.TermEntity
import com.fahimezv.flickrfindr.core.database.model.toTerm
import com.fahimezv.flickrfindr.core.model.Term
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TermRepository {
    suspend fun addTerm(term: Term)
    fun getTerms(): Flow<List<Term>>
}

class TermRepositoryImpl(
    private val termDao: TermDao,
) : TermRepository {

    override suspend fun addTerm(term: Term) {
        termDao.insert(TermEntity(text = term.text, timestamp = timestamp()))
    }

    override fun getTerms(): Flow<List<Term>> {
        return termDao.getTerms(5).map { list ->
            list.map { it.toTerm() }
        }
    }

    private fun timestamp() = System.currentTimeMillis()


}