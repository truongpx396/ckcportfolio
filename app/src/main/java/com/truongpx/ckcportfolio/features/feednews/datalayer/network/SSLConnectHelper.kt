/*
 *
 *   Copyright (C) 2019 Truongpx Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.truongpx.ckcportfolio.features.feednews.datalayer.network

import android.content.Context
import com.truongpx.ckcportfolio.R
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class SSLConnectHelper {
    companion object {
        fun getSSLContext(context: Context): SSLContext {

            val cf = CertificateFactory.getInstance("X.509")
            val cert = context.resources.openRawResource(R.raw.my_cert)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(cert)
            } finally {
                cert.close()
            }

            // creating a KeyStore containing our trusted CAs
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            // creating a TrustManager that trusts the CAs in our KeyStore
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)

            // creating an SSLSocketFactory that uses our TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, null)
            return sslContext
        }

        fun getX509TrustManager(): X509TrustManager {
            return object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()


                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    try {
                        chain[0].checkValidity()
                    } catch (e: Exception) {
                        throw CertificateException("Certificate not valid or trusted.")
                    }

                }
            }
        }
    }
}