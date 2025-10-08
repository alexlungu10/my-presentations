package com.example.rag

import com.example.rag.building.ai.assistant.BuildingAIAssistantService
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class RagApplication2(
    val buildingAIAssistantService: BuildingAIAssistantService
) :
    CommandLineRunner {
    override fun run(vararg args: String?) {
        buildingAIAssistantService.runRAGForBuildingAIAssistantApplication()
    }

}

fun main(args: Array<String>) {
    runApplication<RagApplication2>(*args)
}


fun foo(chatModel: OllamaChatModel) {
    val response: ChatResponse = chatModel.call(
        Prompt(
            "describe some of the advantages of generative AI",
            OllamaOptions.builder()
//					.model(OllamaModel.LLAMA3_1)
                .model("llama3.1:8b")//llama3.1
                .temperature(.01)
                .build()
        )
    )
    println(response.results[0].output.text)
}


