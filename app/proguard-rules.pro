# ============================================================
# ProGuard / R8 — Livraria Pessoal
# ============================================================

# --- Firebase Authentication ---
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# --- Firebase Firestore: preserva nomes de campos dos data classes ---
# (R8 ofuscaria os campos, quebrando a serialização/deserialização)
-keep class br.com.faculdade.imepac.Livro { *; }
-keepclassmembers class br.com.faculdade.imepac.** {
    public <init>();
    public <fields>;
}

# --- Kotlin Coroutines / Extensions ---
-dontwarn kotlin.**
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }

# --- Material Components (ShapeableImageView, etc.) ---
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# --- Manter rastreamento de stack trace em builds de release ---
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# --- Evitar alertas de reflexão do AndroidX ---
-dontwarn androidx.**