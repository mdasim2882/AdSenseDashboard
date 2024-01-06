package com.example.adsensedashboard.utils.api

import com.google.api.client.googleapis.json.GoogleJsonResponseException
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
 * This example shows how to handle different AdSense account errors.
 *
 * Tags: accounts.adclients.list
 *
 */
object HandleAccountErrors {
    /**
     * Runs this sample.
     *
     * @param service AdSense service object on which to run the requests.
     * @param accountId the ID for the account to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, accountId: String?, maxPageSize: Int) {
        println("=================================================================")
        println("Testing error handling")
        println("=================================================================")
        try {
            // Attempt API call.
            adsense.accounts().adclients().list(accountId).setPageSize(maxPageSize).execute()
            println(
                "The call succeeded. Please use an invalid, disapproved or " +
                        "approval-pending AdSense account to test error handling."
            )
            println()
        } catch (e: GoogleJsonResponseException) {
            // Handle a few known API errors. See full list at
            // https://developers.google.com/adsense/management/v1.1/reference/#errors
            val errors = e.details.errors
            for (error in errors) {
                if (error.reason == "noAdSenseAccount") {
                    println("Error handled! No AdSense account for this user.")
                } else if (error.reason == "disapprovedAccount") {
                    println("Error handled! This account is disapproved.")
                } else if (error.reason == "accountPendingReview") {
                    println("Error handled! This account is pending review.")
                } else {
                    // Unrecognized reason, so let's use the error message returned by the API.
                    println("Unrecognized error, showing system message:")
                    println(error.message)
                }
            }
        }
    }
}