package com.bbn.movitfriends.di

import android.app.Application
import android.content.Context
import com.bbn.movitfriends.data.repository.*
import com.bbn.movitfriends.data.service.FirebaseAuthServiceImpl
import com.bbn.movitfriends.domain.repository.*
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
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
    fun provideFirebaseDatabase(): StorageReference {
        return FirebaseStorage.getInstance().getReference("images")
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthServise(firebaseAuth: FirebaseAuth, userRepository: UserRepository): FirebaseAuthService {
        return FirebaseAuthServiceImpl(firebaseAuth, userRepository)
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
    fun provideMessageRepository(firestore: FirebaseFirestore, loginUser: FirebaseUser?, userRepository: UserRepository): MessageRepository{
        return MessageRepositoryImpl(firestore, loginUser, userRepository)
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
    fun provideChatRepository(firestore: FirebaseFirestore, loginUser: FirebaseUser?, userRepository: UserRepository, messageRepository: MessageRepository): ChatRepository{
        return ChatRepositoryImpl(firestore, userRepository, messageRepository, loginUser)
    }

    @Provides
    @Singleton
    fun provideDataStore(context: Application): FilterDataStoreManager {
        return FilterDataStoreManagerImpl(context)
    }

}