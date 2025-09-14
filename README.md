# Spring AI Demo

This project is a demonstration of the [Spring AI](https://spring.io/projects/spring-ai) framework, showcasing its capabilities in a multi-module Maven project.

## Modules

The project consists of two main modules:

*   **`model`**: A chat application that uses Spring AI's OpenAI starter to expose REST endpoints for synchronous and streaming chat conversations. It integrates with Swagger UI for API documentation.
*   **`mcp-server`**: A server application utilizing the `spring-ai-starter-mcp-server-webmvc`, likely for more advanced AI orchestration or as a proxy server, running on a separate port.

## Prerequisites

*   Java 21 or later
*   Apache Maven
*   An Azure OpenAI API Key

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd spring-ai-demo
```

### 2. Configure API Key

This project is configured to use Azure OpenAI services. You need to provide your API key as an environment variable.

Open the configuration file for the `model` module:
`model/src/main/resources/application.yml`

The API key is configured via an environment variable `${API_KEY}`:

```yaml
spring:
  ai:
    openai:
      base-url: https://models.inference.ai.azure.com
      api-key: ${API_KEY}
      # ... other settings
```

Before running the application, set the `API_KEY` environment variable in your shell:

```bash
# For Linux/macOS
export API_KEY='your_azure_openai_api_key'

# For Windows (Command Prompt)
set API_KEY="your_azure_openai_api_key"

# For Windows (PowerShell)
$env:API_KEY="your_azure_openai_api_key"
```

### 3. Build the project

Use Maven to build all modules from the root directory:

```bash
mvn clean install
```

### 4. Run the applications

You can run each module independently.

**To run the `model` application:**
This service will handle chat requests.

```bash
mvn spring-boot:run -pl model
```
The application will be available at `http://localhost:9090`.

**To run the `mcp-server` application:**

```bash
mvn spring-boot:run -pl mcp-server
```
The application will be available at `http://localhost:8090/ai`.

## Technologies Used

*   Spring Boot 3.5.5
*   Spring AI 1.1.0-M1
*   Java 21
*   Apache Maven
*   Lombok
*   SpringDoc (for Swagger UI)
