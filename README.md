

# AES GCM Encryption and Decryption for Android

This project provides an implementation of AES encryption and decryption using the GCM (Galois/Counter Mode) mode of operation for secure data handling in Android applications. It allows you to securely encrypt and decrypt data either using a key stored in the Android Keystore or a manually provided key.

## Features

- 🔐 AES Encryption & Decryption using GCM mode
- 🔑 Android Keystore integration
- 🧰 Manual key support (Base64 encoded)
- 💡 Simple and developer-friendly API

---

## 📦 Import via JitPack

To include this library in your project via [JitPack](https://jitpack.io), follow these steps:

### 1. Add JitPack repository to your `settings.gradle` (project-level):

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
````

### 2. Add the dependency in your app/module `build.gradle`:

```kotlin
dependencies {
    implementation("com.github.mat-in:AES-GCM_Android:v1.0.0")
}
```

---

## 🛠️ Setup

### Dependencies

Add these (if not already present):

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.core:core-ktx:1.5.0'
}
```

---

## 🔑 Using the Keystore

### 1. Create a secure key

```kotlin
AesGcm.createKeystoreKey("secure_alias")
```

### 2. Encrypt & decrypt

```kotlin
val aes = AesGcm.fromKeystore("secure_alias")
val encrypted = aes.encrypt("Secret Message")
val decrypted = aes.decrypt(encrypted)
```

---

## 🧪 Using a Manual Key

### 1. Generate Base64 Key

```kotlin
val base64Key = AesGcm.generateBase64Key()
```

### 2. Encrypt & Decrypt

```kotlin
val aes = AesGcm.fromBase64Key(base64Key)
val encrypted = aes.encrypt("Secret Message")
val decrypted = aes.decrypt(encrypted)
```

---

## 📘 Code Explanation

### Main API: `AesGcm`

* `encrypt(text: String)`: Returns encrypted text (Base64-encoded)
* `decrypt(text: String)`: Returns original decrypted text

### Companion Methods

* `createKeystoreKey(alias: String)`
* `fromKeystore(alias: String)`
* `generateBase64Key(size: Int = 256)`
* `fromBase64Key(key: String)`

### Internal: `KeyWrapper`

Used to wrap either a Keystore-based or manually supplied `SecretKey`.

---

## ▶️ Example Usage

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AesGcm.createKeystoreKey("secure_alias")

        val aes = AesGcm.fromKeystore("secure_alias")
        val encrypted = aes.encrypt("Secret Message")
        val decrypted = aes.decrypt(encrypted)

        Log.d("AES", "Encrypted: $encrypted")
        Log.d("AES", "Decrypted: $decrypted")
    }
}
```

---

## ✅ Testing

Ensure encrypted values differ each time due to random IVs. The decrypted output should match the original input.

---

## 🤝 Contributing

Pull requests are welcome! Please include tests and documentation for new features.

---

## ⚠️ Notes

* Do **not** hardcode keys in production apps.
* Do **not** commit generated keys to version control.
* Always keep your app and libraries updated for security.

---

## 📄 License

MIT © [mat-in](https://github.com/mat-in)
