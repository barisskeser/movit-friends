package com.bbn.movitfriends.di

import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.data.repository.*
import com.bbn.movitfriends.data.service.FirebaseAuthServiceImpl
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.*
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.LatLng
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthServise(firebaseAuth: FirebaseAuth): FirebaseAuthService {
        return FirebaseAuthServiceImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginFirebaseUser(firebaseAuth: FirebaseAuth): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository{
        return UserRepositoryImpl(firestore)
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

}