package activites

import android.app.Activity
import android.util.Log
import android.widget.Toast
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
        fun updateUserProfileData(activity:my_profile,userHashMap:HashMap<String,Any>){
            mFireStore.collection(Constants.USERS)
                .document(getCurrentUserID())
                .update(userHashMap)
                   .addOnSuccessListener {
                        Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")
                        Toast.makeText(activity, "Profile Data updated successfully!", Toast.LENGTH_SHORT).show()
                        activity.profileUpdateSuccess()
                    }.addOnFailureListener {
                        e ->
                        activity.hideProgressDialog()
                        Log.e(activity.javaClass.simpleName, "Error while updating profile", e)
                        Toast.makeText(activity, "Error when updating profile", Toast.LENGTH_SHORT).show()
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







