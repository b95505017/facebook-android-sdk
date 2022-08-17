@file:Suppress("deprecation")

package com.facebook.internal

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ApplicationInfoFlags
import android.content.pm.PackageManager.ComponentInfoFlags
import android.content.pm.PackageManager.GET_SIGNING_CERTIFICATES
import android.content.pm.PackageManager.PackageInfoFlags
import android.content.pm.PackageManager.ResolveInfoFlags
import android.content.pm.ResolveInfo
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.os.BuildCompat

fun PackageManager.resolveActivityCompat(
    intent: Intent,
    flags: Int,
) = if (BuildCompat.isAtLeastT()) {
    resolveActivity(intent, ResolveInfoFlags.of(flags.toLong()))
} else {
    resolveActivity(intent, flags)
}

@Suppress("QueryPermissionsNeeded")
fun PackageManager.queryIntentActivitiesCompat(
    intent: Intent,
    flags: Int,
): List<ResolveInfo> = if (BuildCompat.isAtLeastT()) {
    queryIntentActivities(intent, ResolveInfoFlags.of(flags.toLong()))
} else {
    queryIntentActivities(intent, flags)
}

fun PackageManager.resolveServiceCompat(
    intent: Intent,
    flags: Int,
) = if (BuildCompat.isAtLeastT()) {
    resolveService(intent, ResolveInfoFlags.of(flags.toLong()))
} else {
    resolveService(intent, flags)
}

@Throws(PackageManager.NameNotFoundException::class)
fun PackageManager.getPackageInfoCompat(
    packageName: String,
    flags: Int,
): PackageInfo = if (BuildCompat.isAtLeastT()) {
    getPackageInfo(packageName, PackageInfoFlags.of(flags.toLong()))
} else {
    getPackageInfo(packageName, flags)
}

@Throws(PackageManager.NameNotFoundException::class)
fun PackageManager.getApplicationInfoCompat(
    packageName: String,
    flags: Int,
): ApplicationInfo = if (BuildCompat.isAtLeastT()) {
    getApplicationInfo(packageName, ApplicationInfoFlags.of(flags.toLong()))
} else {
    getApplicationInfo(packageName, flags)
}

fun PackageManager.resolveContentProviderCompat(
    authority: String,
    flags: Int,
) = if (BuildCompat.isAtLeastT()) {
    resolveContentProvider(authority, ComponentInfoFlags.of(flags.toLong()))
} else {
    resolveContentProvider(authority, flags)
}

@Throws(PackageManager.NameNotFoundException::class)
fun PackageManager.getSignaturesCompat(
    packageName: String,
) = if (BuildCompat.isAtLeastT()) {
    getPackageInfo(packageName, PackageInfoFlags.of(GET_SIGNING_CERTIFICATES.toLong()))
        .signingInfo
        .let {
            if (it.hasMultipleSigners()) {
                it.apkContentsSigners
            } else {
                it.signingCertificateHistory
            }
        }
        .toList()
} else {
    PackageInfoCompat.getSignatures(this, packageName)
}