package com.example.aesgcm

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesGcm private constructor(private val keyWrapper: KeyWrapper) {

    private val transformation = "AES/GCM/NoPadding"
    private val ivSize = 12
    private val tagSize = 128

    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(transformation)
        val iv: ByteArray

        when (keyWrapper) {
            is KeyWrapper.Keystore -> {
                cipher.init(Cipher.ENCRYPT_MODE, keyWrapper.key)
                iv = cipher.iv
            }
            is KeyWrapper.Manual -> {
                iv = ByteArray(ivSize).also { SecureRandom().nextBytes(it) }
                val spec = GCMParameterSpec(tagSize, iv)
                cipher.init(Cipher.ENCRYPT_MODE, keyWrapper.key, spec)
            }
        }

        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(iv + encryptedBytes)
    }

    fun decrypt(cipherText: String): String {
        val decoded = Base64.getDecoder().decode(cipherText)
        val iv = decoded.copyOfRange(0, ivSize)
        val encryptedBytes = decoded.copyOfRange(ivSize, decoded.size)

        val cipher = Cipher.getInstance(transformation)
        val spec = GCMParameterSpec(tagSize, iv)
        cipher.init(Cipher.DECRYPT_MODE, keyWrapper.key, spec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    // ----- Companion Factory API -----
    companion object {

        fun fromKeystore(alias: String): AesGcm {
            return AesGcm(KeyWrapper.Keystore(getKeyFromKeystore(alias)))
        }

        fun fromBase64Key(base64Key: String): AesGcm {
            val bytes = Base64.getDecoder().decode(base64Key)
            val key = SecretKeySpec(bytes, "AES")
            return AesGcm(KeyWrapper.Manual(key))
        }

        fun generateBase64Key(keySize: Int = 256): String {
            val keyGen = KeyGenerator.getInstance("AES")
            keyGen.init(keySize)
            val key = keyGen.generateKey()
            return Base64.getEncoder().encodeToString(key.encoded)
        }

        fun createKeystoreKey(alias: String, keySize: Int = 256) {
            val generator = KeyGenerator.getInstance("AES", "AndroidKeyStore")
            val spec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(keySize)
                .build()
            generator.init(spec)
            generator.generateKey()
        }

        private fun getKeyFromKeystore(alias: String): SecretKey {
            val ks = KeyStore.getInstance("AndroidKeyStore")
            ks.load(null)
            return ks.getKey(alias, null) as SecretKey
        }
    }

    // Internal sealed class to differentiate key types
    private sealed class KeyWrapper(val key: SecretKey) {
        class Manual(secretKey: SecretKey) : KeyWrapper(secretKey)
        class Keystore(secretKey: SecretKey) : KeyWrapper(secretKey)
    }
}
