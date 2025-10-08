package com.example.rag.building.ai.assistant

import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.stereotype.Service
import kotlin.time.measureTimedValue

@Service
class BuildingAIAssistantService(
    val chatModel: OllamaChatModel,
    val cypherGeneratorPrompt: CypherPromptGenerator,
    val neo4JService: Neo4JService,
    val ragResponseGenerator: RagResponseGenerator2
) {

    fun runRAGForBuildingAIAssistantApplication() {
        //todo add power consumption, list the devices in a room, order them by power consumption, descending
        //todo maybe the user can save favorite queries, fro later reuse, or for sharing

        //		OllamaChatModel chatModel
        //ok demo
//        val content = cypherGeneratorPrompt.generateCypherQuery("list the devices that  Room 101 contains")
//        val content = cypherGeneratorPrompt.generateCypherQuery("count the elements that each room contains and display the room name and the number of elements in the room. Order the results by count, descending") // ok demo

//        val content = cypherGeneratorPrompt.generateCypherQuery("what devices are in 'Room 101' ?") //ok demo
//        val content = cypherGeneratorPrompt.generateCypherQuery("what rooms are on Floor number 1 ?")//ok demo
//        val content = cypherGeneratorPrompt.generateCypherQuery("what rooms are on Floor number 2 ?")//ok demo
//        val content = cypherGeneratorPrompt.generateCypherQuery("what is the power consumption of the 'Room 101' ?"
//        val content = cypherGeneratorPrompt.generateCypherQuery("count the rooms on Floor number 1 ?") //ok demo
//        val content = cypherGeneratorPrompt.generateCypherQuery("count the devices from each room and display the room name and the device count in descending order?")
//        val content = cypherGeneratorPrompt.generateCypherQuery("count the sensors from each room and display the room name and the sensor count in descending order?")

//        val content = cypherGeneratorPrompt.generateCypherQuery("what rooms has Fluorescent lights ?") //

        val question =  "count the devices from each room and display the room name and device node label and count in descending order"
//        val question = "count the sensors from each room and display the room name and the sensor count in descending order"

//        val question = "get all the Light devices and display the room, Light type and Light type counter"
//        val question = "display the room, Light type and Light type counter"

//        val question = ("what rooms are on Floor number 1 ? display floor name and room name") //ok demo
//        val question = ("what rooms are on on each floor ? display floor name and room name") //ok demo

//        val question = ("count the sensors from each room and display the room name and the sensor count in descending order?")
//        val question = ("count the devices from each room and display the room name and the device count in descending order?")

//        val question ="what rooms are on Floor number 1 ?"
//        val question ="what rooms are on Floor number 3 ?"

        val content = cypherGeneratorPrompt.generateCypherQuery(question)


        //todo check power consumption
        //todo check old light bulbs type =metal_spring

        //testing
//        val content = cypherGeneratorPrompt.generateCypherQuery("what devices are controlled by each circuit breaker ?")
//        val content = cypherGeneratorPrompt.generateCypherQuery("what room has a circuit breaker ?")
//        val content = cypherGeneratorPrompt.generateCypherQuery("what room has a circuit breaker ?")
//        val content = cypherGeneratorPrompt.generateCypherQuery("what room has no circuit breaker ?")
//        val content = cypherGeneratorPrompt.generateCypherQuery("what rooms contain no Sensor devices ?") //NOK
//        val content = cypherGeneratorPrompt.generateCypherQuery("what rooms has  Lights of type Fluorescent ?") //
//        val content = cypherGeneratorPrompt.generateCypherQuery("what rooms has Fluorescent lights ?") //


        //		extracted(content, "gemma3:12b")
        //		extracted(content, "mistral-small3.1:latest")


        ///////////////good

//        sendPromptToAI(content, "llama3.1:8b")
//        getCypherFromAI(content, "gemma3:12b")


//        sendPromptToAI(content, "gemma3n:e4b")
//        val cypherFromAI = sendPromptToAI(content, "gemma3n:e4b")
//        val cypherFromAI = sendPromptToAI(content, "gemma3:12b")
//        val cypherFromAI = sendPromptToAI(content, "gemma3:27b")
        val cypherFromAI = sendPromptToAI(content, "gpt-oss:20b")

//        getCypherFromAI(content, "mistral-small3.1:latest")


        val responseFromCypher = neo4JService.query(cypherFromAI)
        val ragRequest =
            ragResponseGenerator.generatePromptForFinalResponse(question, responseFromCypher)
        println(ragRequest)

//        sendPromptToAI(ragRequest, "gemma3n:e4b")
//        sendPromptToAI(ragRequest, "gemma3:27b")
        sendPromptToAI(ragRequest, "gpt-oss:20b")

    }

    private fun sendPromptToAI(content: String, model: String): String? {
        println("------------------")
        println("Model: $model")
        println("Loading...")

        val (response, duration) = measureTimedValue {
            chatModel
                .call(
                    Prompt(
                        content,
                        OllamaOptions.builder()
                            .model(model)//llama3.1  gemma3:12b
                            .temperature(.01)
                            .build()
                    )
                )
        }
        val query = response.results[0].output.text
        println(query)

        println("Duration: $duration")
        return query
    }
}