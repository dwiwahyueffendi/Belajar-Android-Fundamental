package com.example.submissionwahyu.repository

import com.example.submissionwahyu.data.database.QueryDatabase

object FavoriteRepository {
    fun getFavoriteRepo(queryDatabase: QueryDatabase?) = queryDatabase?.getFavoriteUser()
}