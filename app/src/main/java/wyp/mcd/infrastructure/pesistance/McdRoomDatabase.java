/*
 * Copyright (C) 2018
 *
 * Source code is created by Elissa Software
 * Dictionary data is owned by UCST
 * Database is implemented by Salai Chit Oo Latt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package wyp.mcd.infrastructure.pesistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import wyp.mcd.component.persistence.DateRoomConverter;
import wyp.mcd.component.util.Constants;
import wyp.mcd.infrastructure.dao.BookmarksDao;
import wyp.mcd.infrastructure.dao.EngToEngDao;
import wyp.mcd.infrastructure.dao.EngToMmDao;
import wyp.mcd.infrastructure.entities.BookmarksEntity;
import wyp.mcd.infrastructure.entities.EngToEngEntity;
import wyp.mcd.infrastructure.entities.EngToMmEntity;

@Database(entities = {BookmarksEntity.class, EngToMmEntity.class, EngToEngEntity.class}
        , version = 1, exportSchema = false)
@TypeConverters({DateRoomConverter.class})
public abstract class McdRoomDatabase extends RoomDatabase {

    public abstract BookmarksDao bookmarksDao();
    public abstract EngToMmDao engToMmDao();
    public abstract EngToEngDao engToEngDao();

    private static McdRoomDatabase INSTANCE;

    public static McdRoomDatabase getDatabase(final Context mContext) {

        if (null == INSTANCE) {
            synchronized (McdRoomDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = buildDatabaseInstance(mContext);
                }
            }
        }
        return INSTANCE;
    }

    private static McdRoomDatabase buildDatabaseInstance(Context mContext) {

        return Room.databaseBuilder(mContext.getApplicationContext(),
                McdRoomDatabase.class,
                Constants.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public void cleanUp() {
        INSTANCE = null;
    }
}
