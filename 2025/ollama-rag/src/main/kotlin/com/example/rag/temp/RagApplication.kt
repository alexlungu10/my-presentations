//package com.example.rag.temp
//
//import org.springframework.ai.chat.model.ChatResponse
//import org.springframework.ai.chat.prompt.Prompt
//import org.springframework.ai.ollama.OllamaChatModel
//import org.springframework.ai.ollama.api.OllamaModel
//import org.springframework.ai.ollama.api.OllamaOptions
//import org.springframework.boot.CommandLineRunner
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.runApplication
//
//
//@SpringBootApplication
//class RagApplication(val chatModel: OllamaChatModel): CommandLineRunner {
//	override fun run(vararg args: String?) {
//
////		OllamaChatModel chatModel
//		val content = """
//			// Create Locations
//CREATE (b:Building {name: 'TechTower'})
//CREATE (f1:Floor {number: 1})
//CREATE (f2:Floor {number: 2})
//CREATE (r101:Room {name: 'Room 101'})
//CREATE (r102:Room {name: 'Room 102'})
//CREATE (r201:Room {name: 'Room 201'})
//CREATE (r104:Room {name: 'Room 104'})
//
//// Location Hierarchy
//CREATE (b)-[:HAS_FLOOR]->(f1)
//CREATE (b)-[:HAS_FLOOR]->(f2)
//CREATE (f1)-[:HAS_ROOM]->(r101)
//CREATE (f1)-[:HAS_ROOM]->(r102)
//CREATE (f1)-[:HAS_ROOM]->(r104)
//CREATE (f2)-[:HAS_ROOM]->(r201)
//
//// Electrical Equipment
//CREATE (mainPanel:Panel {id: 'P-Main', capacity_kW: 1000})
//CREATE (subPanel1:Panel {id: 'P-1F', capacity_kW: 500})
//CREATE (subPanel2:Panel {id: 'P-2F', capacity_kW: 500})
//CREATE (xfmr:Transformer {id: 'T-01', ratio: '11kV/400V'})
//CREATE (cb101:CircuitBreaker {id: 'CB-101'})
//CREATE (light101:Light {id: 'L-101', type: 'LED'})
//CREATE (outlet101:Outlet {id: 'O-101'})
//CREATE (sensor101:Sensor {id: 'S-Temp-101', type: 'Temperature'})
//
//CREATE (sensor104:Sensor {id: 'S-Temp-104', type: 'Temperature'})
//CREATE (sensor105:Sensor {id: 'S-Temp-105', type: 'Temperature'})
//CREATE (sensor106:Sensor {id: 'S-Temp-106', type: 'Temperature'})
//CREATE (light104:Light {id: 'L-104', type: 'LED'})
//CREATE (light105:Light {id: 'L-105', type: 'LED'})
//
//// Location Assignment
//CREATE (r101)-[:CONTAINS]->(cb101)
//CREATE (r101)-[:CONTAINS]->(light101)
//CREATE (r101)-[:CONTAINS]->(outlet101)
//CREATE (r101)-[:CONTAINS]->(sensor101)
//CREATE (f1)-[:CONTAINS]->(subPanel1)
//CREATE (f2)-[:CONTAINS]->(subPanel2)
//CREATE (b)-[:CONTAINS]->(mainPanel)
//CREATE (b)-[:CONTAINS]->(xfmr)
//
//
//CREATE (r104)-[:CONTAINS]->(sensor104)
//CREATE (r104)-[:CONTAINS]->(sensor105)
//CREATE (r104)-[:CONTAINS]->(sensor106)
//CREATE (r104)-[:CONTAINS]->(light104)
//CREATE (r104)-[:CONTAINS]->(light105)
//
//// Electrical Connections
//CREATE (xfmr)-[:FEEDS]->(mainPanel)
//CREATE (mainPanel)-[:FEEDS]->(subPanel1)
//CREATE (mainPanel)-[:FEEDS]->(subPanel2)
//CREATE (subPanel1)-[:FEEDS]->(cb101)
//CREATE (cb101)-[:CONTROLS]->(light101)
//CREATE (cb101)-[:CONTROLS]->(outlet101)
//
//// Monitoring
//CREATE (sensor101)-[:MONITORS]->(r101)
//
//		<question>
//		what nodes are under room Room 104
//		 <question>
//		""".trimIndent()
//
//		/*
//					What  nodes room with name  Room 104 contains ? Search for relationship Room contains node. respond in cypher, in one line.
//
//					What generic nodes are in Room 104  ? Search for type: Temperature or LED or Sensor or Light or Outlet. respond in cypher, in one line.
//			What  nodes are present in room with name  Floor 5  ? Search for relationship like Room contains node. respond in cypher query, in one line.
//how many nodes are present in room with name  Room 104  ? Search for relationship like Room contains node. respond in cypher query, in one line.			If you don't know, response with "I don't know"
//		how many nodes are present in each room  ? Search for relationship like Room contains node. respond in cypher query, in one line.			If you don't know, response with "I don't know"
//				how many nodes are present in each room, group them by room? Search for relationship like Room contains node. respond in cypher query, in one line.			If you don't know, response with "I don't know"
//
//	==================
//			what nodes are under room Room 104
//	 +----------------+
//	| Sensor104     |
//	| Sensor105     |
//	| Sensor106     |
//	| Light104      |
//	| Light105      |
//	| Room104       |
//	+----------------+
//		<question>
//
//		 */
//		val response: ChatResponse = chatModel.call(
//			Prompt(
//				content,
//				OllamaOptions.builder()
//					.model(OllamaModel.LLAMA3_2)
//					.temperature(.01)
//					.build()
//			)
//		)
//		println(response.results[0].output.text)
//	}
//}
//
//fun main(args: Array<String>) {
//	runApplication<RagApplication>(*args)
//}
//
//
//
