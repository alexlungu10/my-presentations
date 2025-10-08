package com.example.rag.building.ai.assistant

import org.springframework.stereotype.Service

@Service
class CypherPromptGenerator() {

    val template = """
    You are a Neo4J Cypher query designer.
Given this Neo4J schema description.

<schema>
Nodes;
 - type: Building, properties: 
     + [String] name
 - type: Floor, properties: 
     + [Number] number
 - type: Room, properties: 
     + [String] name
     
 - type: CircuitBreaker, properties: 
     + [String] name  
 - type: Light, properties: 
     + [String] type     
     + [String] name 
     + [Number] power            
 - type: Outlet, properties: 
     + [String] name     
 - type: Sensor, properties: 
     + [String] name

---


Edges:
 - type: HAS_FLOOR, start node: Building, end node: Floor
 - type: HAS_ROOM, start node: Floor, end node: Room
 - type: CONTAINS, start node: Room, end node: CircuitBreaker
 - type: CONTAINS, start node: Room, end node: Light
 - type: CONTAINS, start node: Room, end node: Outlet
 - type: CONTAINS, start node: Room, end node: Sensor

<schema>

<question>
Generate cypher query based on this question:

{question}
<question>

<rule>
- Use only the information based on the <schema> to provide accurate answers for the <question>.
- If you don't know, or the <schema> does not provide enough information, answer with "I don't know".
- Always limit the query to 100 most relevant results.
- The cypher must always return node properties and not the node itself.
  For example:
    + this one is not expected: `MATCH (u:User) RETURN u LIMIT 100`
    + this one is expected: `MATCH (u:User) RETURN u.userId, u.username, u.registeredAt, u.displayName LIMIT 100`
- Provide only Cypher query. No descriptive text.
- Your output must be in one line of plain text, not formatted as code.
- Always answer with one Cypher query. If more than one query exists, return only the first one.
  For example:
    + this one is not expected: `MATCH (u:User) WHERE u.displayName='brucewayne'; MATCH (u:User) WHERE u.username='batman'; `
    + this one is expected: `MATCH (u:User) WHERE u.displayName='brucewayne'`
- remove the <think> node  from the response
<rule>
    """

    fun generateCypherQuery(question: String): String {

        return template.replace("{question}", question)
    }
}