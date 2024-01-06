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
 * This example retrieves a saved report for the default account.
 *
 * Tags: accounts.reports.saved.generate
 *
 */
object GenerateSavedReport {
    /**
     * Runs this sample.
     *
     * @param adsense AdSense service object on which to run the requests.
     * @param savedReportId the saved report ID on which to run the report.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, savedReportId: String?) {
        println("=================================================================")
        System.out.printf("Running saved report %s\n", savedReportId)
        println("=================================================================")

        // Prepare report.
        val request = adsense.accounts().reports().saved().generate(savedReportId)
        request.setDateRange("LAST_7_DAYS")

        // Run saved report.
        val response = request.execute()
        val rows = response.rows
        if (rows != null && !rows.isEmpty()) {
            // Display headers.
            for (header in response.headers) {
                System.out.printf("%25s", header.name)
            }
            println()

            // Display results.
            for (row in rows) {
                for (cell in row.cells) {
                    System.out.printf("%25s", cell.value)
                }
                println()
            }
            println()
        } else {
            println("No rows returned.")
        }
        println()
    }
}