package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import com.google.api.services.adsense.v2.model.SavedReport

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
 * This example gets all saved reports for an account.
 *
 * Tags: accounts.reports.saved.list
 *
 */
object GetAllSavedReports {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param accountId the ID for the account to be used.
     * @param maxPageSize the maximum page size to retrieve.
     * @return the retrieved saved reports.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, accountId: String?, maxPageSize: Int): List<SavedReport> {
        println("=================================================================")
        System.out.printf("Listing all saved reports for account %s\n", accountId)
        println("=================================================================")

        // Retrieve saved report list in pages and display the data as we receive it.
        var pageToken: String? = null
        val allSavedReports: MutableList<SavedReport> = ArrayList()
        do {
            val response = adsense.accounts().reports().saved().list(accountId)
                .setPageSize(maxPageSize)
                .setPageToken(pageToken)
                .execute()
            val savedReports = response.savedReports
            if (savedReports != null && !savedReports.isEmpty()) {
                allSavedReports.addAll(savedReports)
                for (savedReport in savedReports) {
                    System.out.printf(
                        "Saved report with id \"%s\" and name \"%s\" was found.\n",
                        savedReport.name, savedReport.title
                    )
                }
            } else {
                println("No saved reports found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()
        return allSavedReports
    }
}