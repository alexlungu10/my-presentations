package com.example.rag.building.ai.assistant

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.QueryConfig
import org.springframework.stereotype.Service


//import org.neo4j.driver.Neo4jDriver
//import org.neo4j.driver.Result

@Service
class Neo4JService() {


    fun query(cypherFromAI: String?): String {
        // URI examples: "neo4j://localhost", "neo4j+s://xxx.databases.neo4j.io"

        val dbUri = "bolt://localhost:7687"
        val dbUser = "neo4j"
        val dbPassword = "12345678"

        GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword)).use { driver ->
            driver.verifyConnectivity()
            println("Connection established.")


            var result = driver.executableQuery(cypherFromAI)
                .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
                .execute();


            // Loop through results and do something with them
            val records = result.records()
//            records.forEach({ r ->
//                System.out.println(r) // or r.get("name").asString()
//            })

            val strings = records.joinToString("\n") { it.toString() }
//            println("neo4J response: $strings")
//            println(strings)

            return strings

        }
    }

}