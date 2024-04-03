package activites

import android.app.Activity
import android.util.Log
import com.example.trello_clone.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import models.User
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
        fun loadUserData(activity: Activity) {
            mFireStore.collection(Constants.USERS).document(getCurrentUserID()).get()
                .addOnSuccessListener { document ->
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    val loggedInUser = document.toObject(User::class.java)!!
                    when (activity) {
                        is SignInActivity -> {
                            activity.SignInSuccess(loggedInUser)
                        }
                        is MainActivity -> {
                            activity.updateNavigationUserDetails(loggedInUser)
                        }
                        is my_profile -> {
                            activity.setUserDataInUI(loggedInUser)
                        }
                    }
                }
                .addOnFailureListener {
                    e ->
                    when (activity) {
                        is SignInActivity -> {
                            activity.hideProgressDialog()
                        }
                        is MainActivity -> {
                            activity.hideProgressDialog()
                        }

                    }


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


