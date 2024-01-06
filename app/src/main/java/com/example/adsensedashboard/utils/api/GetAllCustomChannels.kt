package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import com.google.api.services.adsense.v2.model.CustomChannel

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
 * This example gets all custom channels in an ad client.
 *
 * Tags: accounts.adclients.customchannels.list
 *
 */
object GetAllCustomChannels {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param adClientId the ID for the ad client to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @return the retrieved custom channels.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(
        adsense: Adsense, adClientId: String?,
        maxPageSize: Int
    ): List<CustomChannel> {
        println("=================================================================")
        System.out.printf("Listing all custom channels for ad client %s\n", adClientId)
        println("=================================================================")

        // Retrieve custom channel list in pages and display the data as we receive it.
        var pageToken: String? = null
        val allCustomChannels: MutableList<CustomChannel> = ArrayList()
        do {
            val response = adsense.accounts().adclients().customchannels()
                .list(adClientId)
                .setPageSize(maxPageSize)
                .setPageToken(pageToken)
                .execute()
            val customChannels = response.customChannels
            if (customChannels != null && !customChannels.isEmpty()) {
                allCustomChannels.addAll(customChannels)
                for (channel in customChannels) {
                    System.out.printf(
                        "Custom channel with id \"%s\" and name \"%s\" was found.\n",
                        channel.name, channel.displayName
                    )
                }
            } else {
                println("No custom channels found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()
        return allCustomChannels
    }
}