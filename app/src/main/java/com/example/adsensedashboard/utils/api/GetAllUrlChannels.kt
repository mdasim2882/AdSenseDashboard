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
 * This example gets all URL channels in an ad client.
 *
 * Tags: accounts.adclients.urlchannels.list
 *
 */
object GetAllUrlChannels {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param adClientId the ID for the ad client to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, adClientId: String?, maxPageSize: Int) {
        println("=================================================================")
        System.out.printf("Listing all URL channels for ad client %s\n", adClientId)
        println("=================================================================")

        // Retrieve URL channel list in pages and display the data as we receive it.
        var pageToken: String? = null
        do {
            val response = adsense.accounts().adclients().urlchannels()
                .list(adClientId)
                .setPageSize(maxPageSize)
                .setPageToken(pageToken)
                .execute()
            val urlChannels = response.urlChannels
            if (urlChannels != null && !urlChannels.isEmpty()) {
                for (channel in urlChannels) {
                    System.out.printf(
                        "URL channel with URI pattern \"%s\" was found.\n",
                        channel.uriPattern
                    )
                }
            } else {
                println("No URL channels found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()
    }
}