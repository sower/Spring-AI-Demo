# Spring AI Demo

一个基于 Spring AI 框架的演示项目，展示了如何使用 AI 相关功能，包括聊天、嵌入、RAG（检索增强生成）等。

## 项目结构

```
spring-ai-demo/
├── mcp-server/         # MCP 服务端实现
├── model/              # 核心 AI 模型功能
└── rag/                # RAG 相关功能
```

## 技术栈

- Java 21
- Spring Boot 3.5.5
- Spring AI 1.1.0-M2
- Maven 3.9+
- OpenAI API (兼容 Azure OpenAI)
- Chroma 向量数据库

## 功能模块

### 1. 基础聊天功能 (Chat)
- 路径: `/ai/chat`
- 支持同步和流式响应
- 使用 OpenAI GPT-4o 模型

### 2. 嵌入功能 (Embedding)
- 路径: `/ai/embedding`
- 将文本转换为向量表示
- 使用 text-embedding-3-small 模型

### 3. RAG 功能 (检索增强生成)
- 文档添加: `/ai/add`
- 批量文档添加: `/ai/batch-add`
- 文档搜索: `/ai/search`
- RAG 问答: `/ai/rag`

## 配置要求

### 环境变量
- `API_KEY`: OpenAI API 密钥

### Chroma 向量数据库
RAG 功能需要 Chroma 向量数据库支持:
```yaml
spring:
  ai:
    vectorstore:
      chroma:
        client:
          host: http://localhost
          port: 8000
```

## 安装和运行

1. 克隆项目:
   ```bash
   git clone <repository-url>
   ```

2. 配置环境变量:
   ```bash
   export API_KEY=your_openai_api_key
   ```

3. 启动 Chroma 向量数据库 (可选，用于 RAG 功能):
   ```bash
   uv tool install chromadb
   chroma run --host 0.0.0.0
   ```

4. 构建和运行:
   ```bash
   mvn spring-boot:run
   ```

## API 文档

项目集成了 Swagger UI，启动后可通过以下地址访问 API 文档:
- http://localhost:9090/ai/swagger-ui.html

## 许可证

本项目采用 MIT 许可证，详情请见 [LICENSE](LICENSE) 文件。