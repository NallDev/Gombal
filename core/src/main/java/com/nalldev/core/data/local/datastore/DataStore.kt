package com.nalldev.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "configuration")

fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore

fun providePreferenceDatastore(dataStore: DataStore<Preferences>, ioDispatcher: CoroutineDispatcher): PreferenceDataSource = PreferenceDataSource(dataStore, ioDispatcher)