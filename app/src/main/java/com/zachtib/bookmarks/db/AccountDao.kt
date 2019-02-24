package com.zachtib.bookmarks.db

import androidx.room.*
import com.zachtib.bookmarks.db.models.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<Account>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Int): Account

    @Query("SELECT * FROM accounts WHERE serverUrl = :serverUrl AND username = :username LIMIT 1")
    suspend fun getAccountByServerAndUsername(serverUrl: String, username: String): Account?

    @Insert
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)
}