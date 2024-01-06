package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import com.google.api.services.adsense.v2.model.Account

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
 * This example gets a specific account for the logged in user.
 * This includes the full tree of sub-accounts.
 *
 * Tags: accounts.get, accounts.listChildAccounts
 *
 */
object GetAccountTree {
    /**
     * Auxiliary method to recursively fetch child accounts, displaying them in a tree.
     * @param parentAccount the account to be print a sub-tree for.
     * @param level the depth at which the top account exists in the tree.
     */
    @Throws(Exception::class)
    private fun displayTree(adsense: Adsense, parentAccount: Account, level: Int) {
        for (i in 0 until level) {
            print("  ")
        }
        System.out.printf(
            "Account with ID \"%s\" and name \"%s\" was found.\n",
            parentAccount.name, parentAccount.displayName
        )
        val response = adsense.accounts()
            .listChildAccounts(parentAccount.name).execute()
        val subAccounts = response.accounts
        if (subAccounts != null && !subAccounts.isEmpty()) {
            for (subAccount in subAccounts) {
                displayTree(adsense, subAccount, level + 1)
            }
        }
    }

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
        System.out.printf("Displaying AdSense account tree for %s\n", accountId)
        println("=================================================================")

        // Retrieve account.
        val account = adsense.accounts()[accountId].execute()
        displayTree(adsense, account, 0)
        println()
    }
}