package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import com.google.api.services.adsense.v2.model.AdClient

/*
 * Copyright (c) 2021 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */



/**
 *
 * This example gets all ad clients for the logged in user's default account.
 *
 * Tags: accounts.adclients.list
 *
 */
object GetAllAdClients {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param accountId the ID for the account to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, accountId: String?, maxPageSize: Int): List<AdClient> {
        println("=================================================================")
        System.out.printf("Listing all ad clients for account %s\n", accountId)
        println("=================================================================")

        // Retrieve ad client list in pages and display data as we receive it.
        val allAdClients: MutableList<AdClient> = ArrayList()
        var pageToken: String? = null
        do {
            val response = adsense.accounts().adclients().list(accountId)
                .setPageSize(maxPageSize)
                .setPageToken(pageToken)
                .execute()
            val adClients = response.adClients
            if (adClients != null && !adClients.isEmpty()) {
                allAdClients.addAll(adClients)
                for (adClient in adClients) {
                    System.out.printf(
                        "Ad client for product \"%s\" with ID \"%s\" was found.\n",
                        adClient.productCode, adClient.name
                    )
                    var supportsReporting = true
                    if (adClient.reportingDimensionId == null
                        || adClient.reportingDimensionId.isEmpty()
                    ) {
                        supportsReporting = false
                    }
                    System.out.printf(
                        "\tSupports reporting: %s\n",
                        if (supportsReporting) "No" else "Yes"
                    )
                }
            } else {
                println("No ad clients found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()
        return allAdClients
    }
}