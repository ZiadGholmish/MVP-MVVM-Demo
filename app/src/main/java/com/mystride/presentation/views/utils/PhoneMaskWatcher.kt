package com.mystride.presentation.views.utils


import android.text.method.TextKeyListener.clear
import android.system.Os.poll
import android.text.Selection.getSelectionStart
import android.text.Editable
import android.widget.EditText
import android.text.TextWatcher
import java.util.*
import java.util.regex.Pattern


 class PhoneMaskWatcher( var mask: String, private val editText: EditText) : TextWatcher {

    private val notDigitRegex = Pattern.compile("[^\\d]+")
    private val maskSymbol = "#"
    private val maskPattern: Pattern = Pattern.compile(maskSymbol)
    private val region: String = ""
    private var result = ""
    private var state = EditState.IDLE
    private var cursorPosition: Int = 0
    private var cursorShifting: Int = 0
    private val valueListener: ValueListener? = null

    var phone = ""
        private set

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (state == EditState.IDLE) {
            cursorShifting = s.length
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        val value = s.toString()
        if (state == EditState.RELEASE) {
            cursorShifting = s.length - cursorShifting!!
            cursorPosition = cursorPosition!! + cursorShifting!!
            if (cursorShifting > 0) {
                if (cursorPosition < value.length) {
                    cursorPosition--
                    if (!Character.isDigit(value[cursorPosition!!])) {
                        cursorPosition++
                    }
                }
            } else {
                cursorPosition++
            }

            editText.setSelection(Math.max(0, Math.min(cursorPosition!!, value.length)))

            state = EditState.IDLE
            return
        }

        if (state == EditState.IDLE) {
            if (s.toString().isEmpty()) {
                phone = ""
                cursorPosition = 0
                return
            }

            cursorPosition = editText.selectionStart

            var rawString = value.replace(region, "")
            rawString = notDigitRegex.matcher(rawString).replaceAll("")
            val charsQueue = LinkedList<Char>()
            for (c in rawString.toCharArray()) {
                charsQueue.add(c)
            }

            val rawMaskBuilder = StringBuilder(region + mask)
            val matcher = maskPattern.matcher(region + mask)
            while (matcher.find()) {
                val start = matcher.start()
                if (!charsQueue.isEmpty()) {
                    rawMaskBuilder.replace(start, start + 1, charsQueue.poll().toString())
                    if (charsQueue.isEmpty()) {
                        result = rawMaskBuilder.substring(0, start + 1)
                        break
                    }
                } else {
                    result = rawMaskBuilder.substring(0, start)
                    break
                }
            }

            val phone = notDigitRegex.matcher(rawMaskBuilder.toString()).replaceAll("")
            this.phone = "$phone"

            valueListener?.onPhoneChanged(this.phone)
            state = EditState.EDIT
        }

        when (state) {
            PhoneMaskWatcher.EditState.EDIT -> {
                state = EditState.CLEAR
                s.clear()
            }
            PhoneMaskWatcher.EditState.CLEAR -> {
                state = EditState.RELEASE
                s.append(result, 0, result.length)
            }
            else -> {
            }
        }
    }

    internal enum class EditState {
        IDLE, EDIT, CLEAR, RELEASE
    }

}