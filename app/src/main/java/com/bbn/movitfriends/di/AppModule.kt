package com.bbn.movitfriends.di

import android.app.Application
import android.content.Context
import com.bbn.movitfriends.data.repository.*
import com.bbn.movitfriends.data.service.FirebaseAuthServiceImpl
import com.bbn.movitfriends.domain.repository.*
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(context: Application): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabaseReference(): DatabaseReference {
        return Firebase.database.reference
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): StorageReference {
        return FirebaseStorage.getInstance().getReference("images")
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthService(firebaseAuth: FirebaseAuth): FirebaseAuthService {
        return FirebaseAuthServiceImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginFirebaseUser(firebaseAuth: FirebaseAuth): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore, storageReference: StorageReference, loginUser: FirebaseUser?, dataStoreManager: FilterDataStoreManager): UserRepository{
        return UserRepositoryImpl(firestore, storageReference, loginUser, dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(database: DatabaseReference): MessageRepository{
        return MessageRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(firestore: FirebaseFirestore, loginUser: FirebaseUser?, userRepository: UserRepository): RequestRepository{
        return RequestRepositoryImpl(firestore, loginUser, userRepository)
    }

    @Provides
    @Singleton
    fun provideFriendRepository(firestore: FirebaseFirestore, loginUser: FirebaseUser?, userRepository: UserRepository): FriendRepository{
        return FriendRepositoryImpl(firestore, loginUser, userRepository)
    }

    @Provides
    @Singleton
    fun provideChatRepository(database: DatabaseReference, loginUser: FirebaseUser?, userRepository: UserRepository, context: Context): ChatRepository{
        return ChatRepositoryImpl(database, userRepository, loginUser, context)
    }

    @Provides
    @Singleton
    fun provideDataStore(context: Application): FilterDataStoreManager {
        return FilterDataStoreManagerImpl(context)
    }

}