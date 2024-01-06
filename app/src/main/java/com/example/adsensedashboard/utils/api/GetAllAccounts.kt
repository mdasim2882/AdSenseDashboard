package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import com.google.api.services.adsense.v2.model.Account
import com.google.api.services.adsense.v2.model.ListAccountsResponse

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
 * This example gets all accounts for the logged in user.
 * Tags: accounts.list
 */
object GetAllAccounts {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param maxPageSize the maximum page size to retrieve.
     * @return the retrieved accounts.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, maxPageSize: Int): List<Account> {
        println("=================================================================")
        println("Listing all AdSense accounts")
        println("=================================================================")
        val allAccounts: MutableList<Account> = ArrayList()
        // Retrieve account list in pages and display data as we receive it.
        var pageToken: String? = null
        do {
            var response: ListAccountsResponse? = null
            response = adsense.accounts().list()
                .setPageSize(maxPageSize)
                .setPageToken(pageToken)
                .execute()
            val accounts = response.accounts
            if (accounts != null && !accounts.isEmpty()) {
                allAccounts.addAll(accounts)
                for (account in accounts) {
                    System.out.printf(
                        "Account with ID \"%s\" and name \"%s\" was found.\n",
                        account.name, account.displayName
                    )
                }
            } else {
                println("No accounts found.")
            }
            pageToken = response.nextPageToken
        } while (pageToken != null)
        println()
        return allAccounts
    }
}