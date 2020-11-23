package com.example.plater.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plater.model.RecipeRoomModel

@Database(entities = [RecipeRoomModel::class], version = 1, exportSchema = false)
public abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}