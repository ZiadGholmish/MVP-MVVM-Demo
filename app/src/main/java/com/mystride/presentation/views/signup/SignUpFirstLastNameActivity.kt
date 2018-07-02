package com.mystride.presentation.views.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.constatns.AppConstant
import com.mystride.mystride.R

import com.mystride.presentation.views.phone.SignUpPhoneActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_sign_up_first_last_name.*
import java.util.concurrent.TimeUnit

class SignUpFirstLastNameActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_first_last_name)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openPhoneActivity()
        setupForValidation()
    }

    private fun setupForValidation() {
        val observable = Observable.combineLatest(
                addFirstNameObservable(),
                addLastNameObservable(),
                BiFunction { isFirstNameValid: Boolean, isLastNameValid: Boolean ->
                    (isFirstNameValid && !first_name_edit.text.isEmpty())
                            && (isLastNameValid && !last_name_edit.text.isEmpty())
                })
                .distinctUntilChanged()
                .subscribe { isValid ->
                    btn_continue.isEnabled = isValid
                }
        compositeDisposable.add(observable)
    }

    private fun addFirstNameObservable(): Observable<Boolean> {
        val firstNameObservable = RxTextView
                .textChanges(first_name_edit)
                .debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map { inputText -> inputText.isEmpty() || inputText.length > AppConstant.NAME_ALLOWED_LENGTH }
                .distinctUntilChanged()
        firstNameObservable.subscribe { isValid ->
            first_name_layout.isErrorEnabled = !isValid
            if (!isValid) {
                first_name_layout.error = getString(R.string.first_name_required)
            }
        }
        return firstNameObservable
    }

    private fun addLastNameObservable(): Observable<Boolean> {
        val lastNameObservable = RxTextView
                .textChanges(last_name_edit)
                .debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map { inputText -> inputText.isEmpty() || inputText.length > AppConstant.NAME_ALLOWED_LENGTH }
                .distinctUntilChanged()
        lastNameObservable.subscribe { isValid ->
            last_name_layout.isErrorEnabled = !isValid
            if (!isValid) {
                last_name_layout.error = getString(R.string.please_enter_your_last_name)
            }
        }
        return lastNameObservable
    }

    private fun openPhoneActivity() {
        btn_continue.setOnClickListener {
            val intent = Intent(this, SignUpPhoneActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
