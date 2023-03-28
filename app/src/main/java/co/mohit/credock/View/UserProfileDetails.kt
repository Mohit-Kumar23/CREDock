package co.mohit.credock.View

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.mohit.credock.CD_UserDesignation_enum
import co.mohit.credock.CD_UserGender_enum
import co.mohit.credock.CD_UserSecurityQue_enum
import co.mohit.credock.Controller.EmailVerificationService
import co.mohit.credock.R
import co.mohit.credock.View.IUserDetailFragmentToActivity
import co.mohit.credock.databinding.FragmentUserProfileDetailsBinding
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.fragment_user_profile_details.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class UserProfileDetails : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var userProfileCallback:IUserDetailFragmentToActivity
    private lateinit var userDetailsBinder: FragmentUserProfileDetailsBinding
    private var userProfileContentValue:ContentValues = ContentValues()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //Implementing concept of Dynamic Polymorphism
            userProfileCallback = context as IUserDetailFragmentToActivity
        }
        catch (ex:Exception)
        {
            throw java.lang.ClassCastException("${context.toString()} must implements IUserDetailFragmentToActivity")
        }
    }
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
        userDetailsBinder = FragmentUserProfileDetailsBinding.inflate(inflater, container, false)
        return userDetailsBinder.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()

    }

    fun performOperationOnNextBtnClick()
    {
        if((!userDetailsBinder.etUserName.text.isNullOrEmpty())
            and (!userDetailsBinder.etUserEmail.text.isNullOrEmpty())
            and (!userDetailsBinder.etUserAge.text.isNullOrEmpty())
            and (userDetailsBinder.spUserGender.selectedItemPosition != 0)
            and (userDetailsBinder.spUserDesignation.selectedItemPosition != 0)
            and (userDetailsBinder.spUserSecurityQuestion.selectedItemPosition != 0)
            and (!userDetailsBinder.etUserSecurityAnswer.text.isNullOrEmpty()))
        {
            userProfileContentValue.put("userName",userDetailsBinder.etUserName.text.toString())
            userProfileContentValue.put("userEmail",userDetailsBinder.etUserEmail.text.toString())
            userProfileContentValue.put("userAge",userDetailsBinder.etUserAge.text.toString())

            when(userDetailsBinder.spUserGender.selectedItemPosition)
            {
                1 -> userProfileContentValue.put("userSelectedGenderId",CD_UserGender_enum.MALE.value.toInt())
                2 -> userProfileContentValue.put("userSelectedGenderId",CD_UserGender_enum.FEMALE.value.toInt())
                3 -> userProfileContentValue.put("userSelectedGenderId",CD_UserGender_enum.OTHER.value.toInt())
            }
            when(userDetailsBinder.spUserDesignation.selectedItemPosition)
            {
                1 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.STUDENT.value.toInt())
                2 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.PROFESSIONAL.value.toInt())
                3 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.DEFENCE.value.toInt())
                4 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.BUSINESS.value.toInt())
                5 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.HOME_MAKER.value.toInt())
                6 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.FREELANCER.value.toInt())
                7 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.GOVT_EMPOLYEE.value.toInt())
                8 -> userProfileContentValue.put("userSelectDesignationId",CD_UserDesignation_enum.OTHER_DESIGNATION.value.toInt())

            }
            when(userDetailsBinder.spUserSecurityQuestion.selectedItemPosition)
            {
                1 -> userProfileContentValue.put("userSelectedSecurityQueId",CD_UserSecurityQue_enum.CHILDHOOD_FRIEND_NAME.value.toInt())
                2 -> userProfileContentValue.put("userSelectedSecurityQueId",CD_UserSecurityQue_enum.FAVOURITE_HOBBY.value.toInt())
                3 -> userProfileContentValue.put("userSelectedSecurityQueId",CD_UserSecurityQue_enum.FAVOURITE_SUBJECT.value.toInt())
                4 -> userProfileContentValue.put("userSelectedSecurityQueId",CD_UserSecurityQue_enum.HIGH_SCHOOL_NAME.value.toInt())
            }
            userProfileContentValue.put("userSecurityAnswer",userDetailsBinder.etUserSecurityAnswer.text.toString())


           userProfileCallback.getUserProfileDetails(userProfileContentValue)
        }
        else
        {
            Toast.makeText(this.context,"Please Fill all Details", Toast.LENGTH_SHORT).show()
            userProfileCallback.getUserProfileDetails(null)
        }
    }
}