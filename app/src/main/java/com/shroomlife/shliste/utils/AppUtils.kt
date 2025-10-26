package com.shroomlife.shliste.utils

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object AppUtils {

    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        val result = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )

        return when (result) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                println("✅ Biometrie verfügbar und eingerichtet")
                true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                println("❌ Keine biometrische Hardware vorhanden")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                println("⚠️ Biometrische Hardware aktuell nicht verfügbar")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                println("⚠️ Kein biometrisches Merkmal registriert")
                false
            }
            else -> {
                println("❓ Unbekannter Status")
                false
            }
        }
    }

    fun authenticateWithBiometrics(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)

        val builder = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Geheime Liste")
            .setSubtitle("Verifiziere dich, um auf diese Liste zuzugreifen")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        } else {
            builder
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .setNegativeButtonText("Abbrechen")
        }

        val promptInfo = builder.build()

        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errString.toString())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("Authentifizierung fehlgeschlagen")
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }

}