package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import com.google.api.services.adsense.v2.model.AdUnit

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
 * This example gets all ad units in an ad client.
 *
 * Tags: accounts.adclients.adunits.list
 *
 */
object GetAllAdUnits {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param adClientId the ID for the ad client to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @return the retrieved ad units.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, adClientId: String?, maxPageSize: Int): List<AdUnit> {
        println("=================================================================")
        System.out.printf("Listing all ad units for ad client %s\n", adClientId)
        println("=================================================================")

        // Retrieve ad unit list in pages and display data as we receive it.
        var pageToken: String? = null
        val allAdUnits: MutableList<AdUnit> = ArrayList()
        do {
            val response = adsense.accounts().adclients().adunits()
                .list(adClientId)
                .setPageSize(maxPageSize)
                .setPageToken(pageToken)
                .execute()
            val adUnits = response.adUnits
            if (adUnits != null && !adUnits.isEmpty()) {
                allAdUnits.addAll(adUnits)
                for (unit in adUnits) {
                    System.out.printf(
                        "Ad unit with id \"%s\", name \"%s\" and status \"%s\" was found.\n",
                        unit.name, unit.displayName, unit.state
                    )
                }
            } else {
                println("No ad units found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()

        // Return the last page of ad units, so that the main sample has something to run.
        return allAdUnits
    }
}