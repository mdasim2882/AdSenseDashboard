package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense

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
 * This example gets all custom channels an ad unit has been added to.
 *
 * Tags: accounts.adclients.adunits.listLinkedCustomChannels
 *
 */
object GetAllCustomChannelsForAdUnit {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param adUnitId the ID for the ad unit to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, adUnitId: String?, maxPageSize: Int) {
        println("=================================================================")
        System.out.printf("Listing all custom channels for ad unit %s\n", adUnitId)
        println("=================================================================")

        // Retrieve custom channel list in pages and display the data as we receive it.
        var pageToken: String? = null
        do {
            val response =
                adsense.accounts().adclients().adunits().listLinkedCustomChannels(adUnitId)
                    .setPageSize(maxPageSize)
                    .setPageToken(pageToken)
                    .execute()
            val customChannels = response.customChannels
            if (customChannels != null && !customChannels.isEmpty()) {
                for (channel in customChannels) {
                    System.out.printf(
                        "Custom channel with code \"%s\" and name \"%s\" was found.\n",
                        channel.name, channel.displayName
                    )
                }
            } else {
                println("No custom channels found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()
    }
}