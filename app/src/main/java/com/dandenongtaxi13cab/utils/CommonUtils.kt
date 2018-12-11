package com.dandenongtaxi13cab.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.TextView
import com.dandenongtaxi13cab.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.text.Charsets.UTF_8

/**
 * @author by Mohit Arora on 3/8/18.
 */
class CommonUtils {

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isEmailValid(email: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {

        val manager = context.assets
        val `is` = manager.open(jsonFileName)

        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, UTF_8)
    }

    fun getTimeStamp(): String {
        return SimpleDateFormat(AppConstants().timeStampFormat, Locale.US).format(Date())
    }

    fun blinkText(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 600 //You can manage the time of the blink with this parameter
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        view.startAnimation(anim)
    }

    @SuppressLint("WorldReadableFiles")
    fun getSuburbsList(context: Context, fileName: String): ArrayList<String> {
        val listSuburbs = ArrayList<String>()
        val returnString = StringBuilder()
        var fIn: InputStream? = null
        var isr: InputStreamReader? = null
        var input: BufferedReader? = null
        try {
            fIn = context.resources.assets
                    .open(fileName, Context.MODE_PRIVATE)
            isr = InputStreamReader(fIn!!)
            input = BufferedReader(isr)
            var line: String?
            do {

                line = input.readLine()
                if (line == null)
                    break
                listSuburbs.add(line)
                returnString.append(line)

            } while (true)
        } catch (e: Exception) {
            e.message
        } finally {
            try {
                isr?.close()
                fIn?.close()
                input?.close()
            } catch (e2: Exception) {
                e2.message
            }

        }
        return listSuburbs
    }

    @SuppressLint("InflateParams")
    fun showDialog(mContext: Context, msg: String, btnTextYes: String, btnTxtNo: String) {

        val dialogBuilder = AlertDialog.Builder(mContext)
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var dialogView: View? = null
        if (true) {
            dialogView = inflater.inflate(R.layout.alert, null)
            val TvAlert = dialogView!!.findViewById<TextView>(R.id.tvAlert)
            TvAlert.text = msg

            val BtnYes = dialogView.findViewById<Button>(R.id.btnYes)
            BtnYes.text = btnTextYes
            val btnTxt = dialogView.findViewById<Button>(R.id.btnNo)
            btnTxt.text = btnTxtNo
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()

            BtnYes.setOnClickListener {
                (mContext as Activity).finish()
                alertDialog.dismiss()
            }

            btnTxt.setOnClickListener { alertDialog.dismiss() }
            alertDialog.show()
        }
    }

    fun share(ctx: Context) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ctx.getString(R.string.app_name))
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, AppConstants().shareLink + ctx.packageName)
        ctx.startActivity(Intent.createChooser(sharingIntent, "Share Via"))
    }
}