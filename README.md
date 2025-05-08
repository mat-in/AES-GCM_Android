
````
# AES GCM Encryption and Decryption for Android

This project provides an implementation of AES encryption and decryption using the GCM (Galois/Counter Mode) mode of operation for secure data handling in Android applications. It allows you to securely encrypt and decrypt data either using a key stored in the Android Keystore or a manually provided key.

## Features

- **AES Encryption & Decryption**: Secure encryption using AES algorithm in GCM mode.
- **Keystore Integration**: Supports key generation and storage in the Android Keystore system.
- **Manual Key Management**: Allows using a manually provided Base64 encoded key for encryption and decryption.
- **Easy-to-use API**: Simple methods for encrypting and decrypting text data.

## Prerequisites

- Android 6.0 (API level 23) or higher.
- Android Studio with the latest stable version.

## Setup

### 1. Add the necessary dependencies

Make sure to add the following dependencies in your `build.gradle` file:

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.core:core-ktx:1.5.0'
}
````

### 2. Key Generation and Encryption

There are two ways to use this library for encryption/decryption: using the Android Keystore or using a manually generated key.

### Using Keystore for Key Management:

The AES encryption key can be generated and stored securely in the Android Keystore.

#### Key Generation:

Use the following method to generate a key and store it securely in the Keystore:

```kotlin
AesGcm.createKeystoreKey("secure_alias")
```

#### Encrypting and Decrypting Data:

Once the key is stored in the Keystore, you can easily encrypt and decrypt data using the following methods:

```kotlin
// Create an AES instance using the Keystore key
val aes = AesGcm.fromKeystore("secure_alias")

// Encrypt data
val encrypted = aes.encrypt("Secret Message")

// Decrypt data
val decrypted = aes.decrypt(encrypted)
```

### Using a Manual Key:

If you want to use a manually created AES key (encoded in Base64), you can do so with the following methods.

#### Generate a Base64 Encoded Key:

To generate a key and encode it in Base64 format:

```kotlin
val base64Key = AesGcm.generateBase64Key() // You can specify the key size (default: 256)
```

#### Encrypting and Decrypting Data:

Use the following methods to encrypt and decrypt with the manually provided key:

```kotlin
// Create an AES instance using the Base64 key
val aes = AesGcm.fromBase64Key(base64Key)

// Encrypt data
val encrypted = aes.encrypt("Secret Message")

// Decrypt data
val decrypted = aes.decrypt(encrypted)
```

## Code Explanation

### AesGcm Class:

The `AesGcm` class is the core of this implementation. It provides methods for:

* **Encryption**: The `encrypt()` method encrypts plaintext using AES in GCM mode.
* **Decryption**: The `decrypt()` method decrypts ciphertext.
* **Key Management**: Keys can be stored securely in the Android Keystore or manually provided in Base64 format.

### Companion Object Methods:

* **`fromKeystore(alias: String)`**: Initializes the AES encryption/decryption using a key stored in the Android Keystore.
* **`fromBase64Key(base64Key: String)`**: Initializes AES encryption/decryption using a manually provided Base64 key.
* **`generateBase64Key(keySize: Int)`**: Generates and returns a Base64 encoded AES key.
* **`createKeystoreKey(alias: String, keySize: Int)`**: Creates and stores a key in the Android Keystore for encryption and decryption.

### KeyWrapper Class:

The `KeyWrapper` class is used internally to differentiate between keys stored in the Keystore and manually provided keys.

## Example

Here’s a complete example of how to use this library:

```kotlin
class MainActivity : AppCompatActivity() {
    lateinit var aes: AesGcm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Step 1: Create the key in the Android Keystore
        AesGcm.createKeystoreKey("secure_alias")

        // Step 2: Create an encryptor using that alias
        val aes = AesGcm.fromKeystore("secure_alias")

        // Step 3: Encrypt and decrypt data
        val encrypted = aes.encrypt("Secret Message")
        val decrypted = aes.decrypt(encrypted)

        Log.d("AES", "Encrypted: $encrypted")
        Log.d("AES", "Decrypted: $decrypted")
    }
}
```

## Testing

Test the encryption and decryption by logging the encrypted and decrypted values. Ensure that the decrypted message matches the original plaintext.

## Contributing

If you want to contribute to this project, feel free to open a pull request with your changes. Please ensure that all code is well-documented and tests are added for new features.

---

### Notes

* **Security Consideration**: Always use secure channels for sharing encrypted data, and never expose encryption keys in your app's source code or commit them to version control.

```

This README covers all aspects of the library, including setup, usage, code explanations, and an example. You can easily extend it by adding more details, tests, or configuration options as you continue developing your library.
```
