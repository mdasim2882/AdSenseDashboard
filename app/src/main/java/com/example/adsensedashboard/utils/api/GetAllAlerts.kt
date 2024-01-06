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
 * Gets all alerts available for the logged in user's account.
 *
 * Tags: accounts.alerts.list
 *
 */
object GetAllAlerts {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param accountId the ID for the account to be used.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, accountId: String?) {
        println("=================================================================")
        System.out.printf("Listing all alerts for account %s\n", accountId)
        println("=================================================================")

        // Retrieve and display alerts.
        val response = adsense.accounts().alerts().list(accountId).execute()
        val alerts = response.alerts
        if (alerts != null && !alerts.isEmpty()) {
            for (alert in alerts) {
                System.out.printf(
                    "Alert id \"%s\" with severity \"%s\" and type \"%s\" was found.\n",
                    alert.name, alert.severity, alert.type
                )
            }
        } else {
            println("No alerts found.")
        }
        println()
    }
}