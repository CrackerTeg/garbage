package com.crackerteg.adhellreborn.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.crackerteg.adhellreborn.db.DateConverter;

import java.util.Date;

@Entity(
        tableName = "WhiteUrl",
        indices = {@Index(value = {"url"}, unique = true)},
        foreignKeys = @ForeignKey(entity = PolicyPackage.class,
                parentColumns = "id",
                childColumns = "policyPackageId")
)
@TypeConverters(DateConverter.class)
public class WhiteUrl {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public long id;

    @ColumnInfo(name = "url")
    public String url;

    public Date insertedAt;

    @ColumnInfo(name = "policyPackageId")
    public String policyPackageId;

    @Ignore
    public WhiteUrl(String url) {
        this.url = url;
        this.insertedAt = new Date();
    }

    public WhiteUrl() {
    }
}
