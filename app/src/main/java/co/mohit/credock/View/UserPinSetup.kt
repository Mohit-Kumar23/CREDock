package co.mohit.credock.View

import android.os.Bundle
import android.os.TestLooperManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import co.mohit.credock.R
import kotlinx.android.synthetic.main.fragment_user_pin_setup.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserPinSetup.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserPinSetup : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var keyEventCode:Int? = null

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
        return inflater.inflate(R.layout.fragment_user_pin_setup, container, false)
    }

    override fun onResume() {
        super.onResume()
        et_newPinDigit1.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_newPinDigit1.text.isNullOrEmpty())
                    et_newPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_newPinDigit2.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_newPinDigit2.text.isNullOrEmpty())
                    et_newPinDigit3.requestFocus()
                else
                    et_newPinDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_newPinDigit3.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_newPinDigit3.text.isNullOrEmpty())
                    et_newPinDigit4.requestFocus()
                else
                    et_newPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_newPinDigit4.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_newPinDigit4.text.isNullOrEmpty())
                    et_newPinDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_reEnterPinDigit1.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_reEnterPinDigit1.text.isNullOrEmpty())
                    et_reEnterPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_reEnterPinDigit2.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_reEnterPinDigit2.text.isNullOrEmpty())
                    et_reEnterPinDigit3.requestFocus()
                else
                    et_reEnterPinDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_reEnterPinDigit3.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_reEnterPinDigit3.text.isNullOrEmpty())
                    et_reEnterPinDigit4.requestFocus()
                else
                    et_reEnterPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_reEnterPinDigit4.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_reEnterPinDigit4.text.isNullOrEmpty())
                    et_reEnterPinDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_otpDigit1.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_otpDigit1.text.isNullOrEmpty())
                    et_otpDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_otpDigit2.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_otpDigit2.text.isNullOrEmpty())
                    et_otpDigit3.requestFocus()
                else
                    et_otpDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_otpDigit3.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_otpDigit3.text.isNullOrEmpty())
                     et_otpDigit4.requestFocus()
                else
                    et_otpDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_otpDigit4.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_otpDigit4.text.isNullOrEmpty())
                    et_otpDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserPinSetup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserPinSetup().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}