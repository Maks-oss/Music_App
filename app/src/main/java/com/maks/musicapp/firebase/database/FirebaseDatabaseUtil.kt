package com.maks.musicapp.firebase.database

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.maks.musicapp.data.domain.Track

object FirebaseDatabaseUtil {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUserId: String
    private val TAG = "FirebaseDatabaseUtil"

    fun setCurrentUserId(currentUserId: String) {
        this.currentUserId = currentUserId
    }
    fun setDatabaseReference(databaseReference: DatabaseReference) {
        this.databaseReference = databaseReference
    }

//    fun addNewUser(user: User) {
//        databaseReference.child("users/${user.hashCode()}").setValue(user)
//    }

    fun addUserNewFavouriteTrack(track: Track) {
        databaseReference.child("$currentUserId/${track.id}").setValue(track)
    }

    fun deleteNewUserFavouriteTrack(trackId: String) {
        databaseReference.child("$currentUserId/${trackId}").removeValue()
    }

    fun addTracksValueListener(onDataChange:(List<Track>?)->Unit){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val value = dataSnapshot.getValue<HashMap<String,HashMap<String, Track>>>()
                val tracks = value?.get(currentUserId)?.values
                Log.d(TAG, "onDataChange: ${tracks?.first()}")
                onDataChange(tracks?.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(postListener)
    }

}