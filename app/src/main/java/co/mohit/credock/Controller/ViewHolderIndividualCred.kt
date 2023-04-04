package co.mohit.credock.Controller

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_credential_input_layout.view.*

class ViewHolderIndividualCred(itemView: View):RecyclerView.ViewHolder(itemView) {

    val inp_credTagName = itemView.et_credentialTag
    val inp_credValue = itemView.et_credentialValue
    val removeBtn = itemView.imgBtn_subtractCred
    val doneBtn = itemView.imgBtn_doneCred
}