package com.timwe.tti2sdk.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.net.data.ConnectivityInterceptor.Companion.ERROR_NO_INTERNET_CONNECTION
import retrofit2.HttpException

class DailogError(context: Context,
                  errorCode: String,
                  clickListenerDialogError: ClickListenerDialogError): Dialog(context) {

    interface ClickListenerDialogError{
        fun reloadClickListener()
    }

    private val inflater = layoutInflater
    private val dialogLayout = inflater.inflate(R.layout.dialog_error, null)
    private val message  = dialogLayout.findViewById<TextView>(R.id.dialogMessageError)
    private val title  = dialogLayout.findViewById<TextView>(R.id.dialogTitleError)
    private val btnReload = dialogLayout.findViewById<AppCompatButton>(R.id.btnError)
    private var _clickListenerDialogError: ClickListenerDialogError? = null

    init{
        _clickListenerDialogError = clickListenerDialogError
        this.setContentView(dialogLayout)
        this.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.setCancelable(true)
        this.window?.setBackgroundDrawableResource(android.R.color.transparent);

        btnReload.setOnClickListener {
            _clickListenerDialogError?.reloadClickListener()
            dismiss()
        }

        if(errorCode == ERROR_NO_INTERNET_CONNECTION){
            setErrorConnection()
        }else{
            setError()
        }

        this.show()
    }

    fun setErrorConnection(){
        message.text = context.getString(R.string.message_two)
        title.text = context.getString(R.string.title_two)
        btnReload.text = context.getString(R.string.btn_two)
    }

    fun setError(){
        message.text = context.getString(R.string.message_one)
        title.text = context.getString(R.string.title_one)
        btnReload.text = context.getString(R.string.btn_one)
    }

}