package co.mohit.credock.Controller

class SingleCredentialViewModel(var credentialTagName:String,var credentialValue:String) {

    fun isCredentialEmpty():Boolean
    {
        if(credentialTagName.isNullOrEmpty() || credentialValue.isNullOrEmpty())
        {
            return true
        }
        return false;
    }
}