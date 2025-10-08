package com.example.rag.building.ai.assistant

import org.springframework.stereotype.Service

@Service
class RagResponseGenerator2() {

    val template = """
    Use the given knowledge-graph to answer the question.
The knowledge-graph contains a CSV formatted text with relevant data.
If you don't know the answer based on given knowledge-graph, just say that you don't know.


---

This knowledge-graph contains data about a building management system.
<knowledge-graph>
{knowledgeGraphContext}
<knowledge-graph>

---

<question>
{question}
<question>

    """

    fun generatePromptForFinalResponse(question: String, knowledgeGraphContext: String): String {

        return template
            .replace("{question}", question)
            .replace("{knowledgeGraphContext}", knowledgeGraphContext)
    }
}