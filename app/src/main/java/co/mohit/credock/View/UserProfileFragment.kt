package co.mohit.credock.View

import android.content.Context
import android.media.MediaPlayer.OnTimedMetaDataAvailableListener
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import co.mohit.credock.CD_Global_enums
import co.mohit.credock.CD_UserDesignation_enum
import co.mohit.credock.CD_UserSecurityQue_enum
import co.mohit.credock.Controller.DatabaseService
import co.mohit.credock.Controller.UserDetailsController
import co.mohit.credock.R
import co.mohit.credock.databinding.FragmentUserProfileBinding
import org.mockito.verification.VerificationWithTimeout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserProfileFragment : Fragment(){

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var userProfileBinding: FragmentUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userProfileBinding = FragmentUserProfileBinding.inflate(inflater,container,false);
        return userProfileBinding.root
    }

    override fun onStart() {
        super.onStart()
        userProfileBinding.spUserDesignation.isEnabled = false
        userProfileBinding.spUserSecurityQue.isEnabled = false
    }

    override fun onResume() {
        super.onResume()

        userProfileBinding.imgBtnEdit.setOnClickListener(View.OnClickListener {
            enableRequiredControls()
            userProfileBinding.imgBtnEdit.visibility = View.INVISIBLE
        })

        userProfileBinding.btnSaveBtn.setOnClickListener(View.OnClickListener {
            disableRequiredControls()
            processForStoringUpdatedValuesToDB()
            userProfileBinding.imgBtnEdit.visibility = View.VISIBLE
        })

        userProfileBinding.btnCancelBtn.setOnClickListener(View.OnClickListener {
            disableRequiredControls()
            userProfileBinding.imgBtnEdit.visibility = View.VISIBLE
        })
    }


    private fun processForStoringUpdatedValuesToDB() {

        var updateUserDetails = UserDetailsController()

        updateUserDetails.userEmail = userProfileBinding.etUserEmail.text.trim().toString()
        updateUserDetails.userAge = userProfileBinding.etUserAge.text.toString().toInt();
        updateUserDetails.userDesignation = when(userProfileBinding.spUserDesignation.selectedItemPosition)
        {
            1 -> CD_UserDesignation_enum.STUDENT.value.toInt()
            2 -> CD_UserDesignation_enum.PROFESSIONAL.value.toInt()
            3 -> CD_UserDesignation_enum.DEFENCE.value.toInt()
            4 -> CD_UserDesignation_enum.BUSINESS.value.toInt()
            5 -> CD_UserDesignation_enum.HOME_MAKER.value.toInt()
            6 -> CD_UserDesignation_enum.FREELANCER.value.toInt()
            7 -> CD_UserDesignation_enum.GOVT_EMPOLYEE.value.toInt()
            8 -> CD_UserDesignation_enum.OTHER_DESIGNATION.value.toInt()

            else -> { - 1}
        }
        updateUserDetails.userSecurityQues = when(userProfileBinding.spUserSecurityQue.selectedItemPosition)
        {
            1 -> CD_UserSecurityQue_enum.CHILDHOOD_FRIEND_NAME.value.toInt()
            2 -> CD_UserSecurityQue_enum.FAVOURITE_HOBBY.value.toInt()
            3 -> CD_UserSecurityQue_enum.FAVOURITE_SUBJECT.value.toInt()
            4 -> CD_UserSecurityQue_enum.HIGH_SCHOOL_NAME.value.toInt()

            else -> { -1}
        }

        updateUserDetails.userSecurityAnswer = userProfileBinding.etUserSecurityAns.text.trim().toString()
        updateUserDetails.userLoginPin = userProfileBinding.etUserPin.text.trim().toString().toInt()

        val currentDateTime = LocalDateTime.now()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy/HH:mm:ss")
        updateUserDetails.lastModifiedOnTimeStamp = currentDateTime.format(dateTimeFormatter)

        var retVal = DatabaseService.updateUserInfoToCDUserTableService(this.requireContext(),DatabaseService.loggedInUserID!!.toString(),updateUserDetails)
        if(retVal == 1)
        {
            Toast.makeText(this.requireContext(),"Update Successfully",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this.requireContext(),"Failed to Update Successfully",Toast.LENGTH_SHORT).show()
        }
    }

    private fun enableRequiredControls() {
        userProfileBinding.btnSaveBtn.visibility = View.VISIBLE
        userProfileBinding.btnCancelBtn.visibility = View.VISIBLE
        userProfileBinding.etUserEmail.isEnabled = true
        userProfileBinding.etUserEmail.setTextColor(R.color.black)
        userProfileBinding.etUserAge.isEnabled = true
        userProfileBinding.etUserAge.setTextColor(R.color.black)
        userProfileBinding.spUserDesignation.isClickable = true
        userProfileBinding.spUserDesignation.isEnabled = true
        userProfileBinding.spUserSecurityQue.isClickable = true
        userProfileBinding.spUserSecurityQue.isEnabled = true
        userProfileBinding.etUserSecurityAns.isEnabled = true
        userProfileBinding.etUserSecurityAns.setTextColor(R.color.black)
    }


    private fun disableRequiredControls() {
        userProfileBinding.btnSaveBtn.visibility = View.INVISIBLE
        userProfileBinding.btnCancelBtn.visibility = View.INVISIBLE
        userProfileBinding.etUserEmail.isEnabled = false; userProfileBinding.etUserEmail.setTextColor(R.color.grey)
        userProfileBinding.etUserAge.isEnabled = false; userProfileBinding.etUserAge.setTextColor(R.color.grey)
        userProfileBinding.spUserDesignation.isEnabled = false
        userProfileBinding.spUserSecurityQue.isEnabled = false
        userProfileBinding.spUserDesignation.isClickable = false
        userProfileBinding.spUserSecurityQue.isClickable = false
        userProfileBinding.etUserSecurityAns.isEnabled = false; userProfileBinding.etUserSecurityAns.setTextColor(R.color.grey)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}