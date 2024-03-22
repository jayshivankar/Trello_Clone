package activites

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import models.User
import activites.SignInActivity
import com.projemanag.activities.BaseActivity

import utils.Constants

    class FireStoreClass:BaseActivity() {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity : SignUpActivity ,userInfo :User){
        mFireStore.collection(Constants.USERS).document(getCurrentUserID()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
                //activity.finish
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error writing document", e)
            }


    }
        fun signInUser(activity: SignUpActivity) {
            mFireStore.collection(Constants.USERS).document(getCurrentUserID()).get()
                .addOnSuccessListener { document ->
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    val loggedInUser = document.toObject(User::class.java)
                    if (loggedInUser != null) {
//                       activity.signInSuccess(loggedInUser)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("TAG", "Error adding document", e)
                }
        }
        override fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }




}


