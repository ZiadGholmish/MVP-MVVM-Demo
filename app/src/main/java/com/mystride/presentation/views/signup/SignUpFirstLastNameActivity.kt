package com.mystride.presentation.views.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mystride.mystride.R

import com.mystride.presentation.views.phone.SignUpPhoneActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_sign_up_first_last_name.*

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
                BiFunction { isFirstNameValid: Boolean, isLastNameValid: Boolean -> isFirstNameValid && isLastNameValid })
                .distinctUntilChanged()
                .subscribe { isValid ->
                    btn_continue.isEnabled = isValid
                }
        compositeDisposable.add(observable)
    }

    private fun addFirstNameObservable(): Observable<Boolean> {
        val firstNameObservable = RxTextView
                .textChanges(first_name_edit)
                .map { inputText -> inputText.length > 3 }
                .distinctUntilChanged()
        firstNameObservable.subscribe {
            first_name_layout.isErrorEnabled = false
        }
        return firstNameObservable
    }

    private fun addLastNameObservable(): Observable<Boolean> {
        val lastNameObservable = RxTextView
                .textChanges(last_name_edit)
                .map { inputText -> inputText.length > 3 }
                .distinctUntilChanged()
        lastNameObservable.subscribe {
            last_name_layout.isErrorEnabled = false
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
