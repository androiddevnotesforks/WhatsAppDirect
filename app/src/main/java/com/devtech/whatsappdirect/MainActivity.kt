package com.devtech.whatsappdirect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devtech.countries_repository.CountriesRepository
import com.devtech.whatsappdirect.core.WhatsAppDirectLauncher
import com.devtech.whatsappdirect.databinding.ActivityMainBinding
import com.devtech.whatsappdirect.fragments.CountriesBottomSheet

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var bottomSheet: CountriesBottomSheet? = null
    private var countryCode = 91

    private val countryCodeText get() = "(+$countryCode)"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = WhatsAppDirectLauncher.parsePhoneNumber(intent)
        binding.phoneInput.editText?.setText(number)

        updateCountryCodeUi()
        bottomSheet = CountriesBottomSheet { country ->
            countryCode = country.numericCode
            binding.countryCodeText.text = countryCodeText
            binding.flagIcon.setImageResource(country.drawableId)
        }
        binding.flagIcon.setOnClickListener {
            bottomSheet?.show(supportFragmentManager, "Country Bottom Sheet")
        }

        binding.launchWhatsApp.setOnClickListener {
            val phoneTxt = binding.phoneInput.editText?.text.toString().trim()
            WhatsAppDirectLauncher.launch(
                this,
                "+$countryCode $phoneTxt",
                binding.messageInput.editText?.text.toString().trim()
            )
        }
    }

    private fun updateCountryCodeUi() {
        val country = CountriesRepository.getAllCountries().firstOrNull {
            it.numericCode == countryCode
        } ?: return
        binding.flagIcon.setImageResource(country.drawableId)
        binding.countryCodeText.text = countryCodeText
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val phoneTxt = binding.phoneInput.editText?.text.toString().trim()
        val messageText = binding.messageInput.editText?.text.toString().trim()
        outState.putString(KEY_PHONE_TEXT, phoneTxt)
        outState.putString(KEY_MSG_TEXT, messageText)
        outState.putInt(KEY_COUNTRY_CODE, countryCode)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.phoneInput.editText?.setText(savedInstanceState.getString(KEY_PHONE_TEXT))
        binding.messageInput.editText?.setText(savedInstanceState.getString(KEY_MSG_TEXT))
        countryCode = savedInstanceState.getInt(KEY_COUNTRY_CODE, 91)
        updateCountryCodeUi()
    }

    override fun onPause() {
        if (bottomSheet?.isVisible == true)
            bottomSheet?.dismiss()
        super.onPause()
    }

    companion object {
        private const val KEY_PHONE_TEXT = "key_phone_text"
        private const val KEY_COUNTRY_CODE = "key_country_code"
        private const val KEY_MSG_TEXT = "key_msg_text"
    }
}