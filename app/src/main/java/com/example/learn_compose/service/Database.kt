package com.example.learn_compose.service

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import javax.inject.Singleton

@Entity(tableName = "meetings")
data class Meeting(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val language: String,
    val level: String,
    val city: String,
    val date: String,
    val active: Boolean,
    val image: String,
    val list: List<String> = listOf("Python", "Junior", "Moscow"),
)

@Entity(tableName = "users")
@Serializable
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
){
    fun mapToUser()=
        UserData(id = id,firstName = firstName,lastName = lastName,phoneNumber = phoneNumber)
}

@Database(entities = [Meeting::class,User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun meetingDao(): MeetingDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "meetings.db",
                        ).build()
                instance = newInstance
                newInstance
            }
    }
}

@Dao
interface MeetingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meeting: Meeting)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(meetings: List<Meeting>)

    @Query("SELECT * FROM ${"meetings"}")
    fun getMeetings(): Flow<List<Meeting>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber LIMIT 1")
    fun getUserByPhone(phoneNumber: String): User?

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase) = database.meetingDao()
}

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> = value.split(",")

    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")
}
