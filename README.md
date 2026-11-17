# API de Conteúdos com Cache Próprio

API REST desenvolvida com **Java 21+ e Spring Boot 4**, utilizando arquitetura hexagonal, persistência com Spring Data JPA e um mecanismo de cache próprio baseado em `ConcurrentHashMap`.

## Como executar

1. Certifique-se de ter o JDK 21+ instalado.
2. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
3. Entre na pasta do projeto e execute:

   **Maven:**
   ```bash
   ./mvnw spring-boot:run
   ```

   No Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. A API estará disponível em:
   `http://localhost:8080`

A aplicação utiliza o banco de dados H2 em memória, configurado automaticamente durante a inicialização.

## Configuração do cache

No arquivo `application.properties`:

```properties
app.cache.max-itens=5
app.cache.ttl-segundos=60
```

- `max-itens`: quantidade máxima de conteúdos armazenados.
- `ttl-segundos`: tempo máximo de permanência de cada entrada no cache.

## Como o cache funciona

O cache utiliza um `ConcurrentHashMap` para armazenar os conteúdos em memória, permitindo acesso concorrente.

Quando um conteúdo é consultado:

1. **HIT:** se estiver no cache e não tiver expirado, é retornado diretamente.
2. **MISS:** se não estiver no cache ou tiver expirado, é buscado no banco de dados e armazenado novamente.
3. **LRU:** quando o limite de itens é atingido, o conteúdo menos recentemente utilizado é removido para dar espaço ao novo.
4. **TTL:** cada entrada possui um tempo de expiração configurável.
5. **Invalidação:** operações de atualização (PUT) e exclusão (DELETE) removem a entrada correspondente do cache.

As consultas retornam o header `X-Cache`, indicando `HIT` ou `MISS`. O sistema também registra logs com o resultado e o tempo de execução.

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/conteudos` | Cadastra um conteúdo |
| GET | `/conteudos/{id}` | Busca um conteúdo |
| PUT | `/conteudos/{id}` | Atualiza um conteúdo |
| DELETE | `/conteudos/{id}` | Exclui um conteúdo |
| GET | `/cache/estatisticas` | Consulta estatísticas do cache |
| DELETE | `/cache` | Limpa o cache e zera as estatísticas |

## Estatísticas

O endpoint `/cache/estatisticas` apresenta:

- **tamanhoAtual:** quantidade de entradas armazenadas.
- **limite:** capacidade máxima do cache.
- **hits:** quantidade de acertos.
- **misses:** quantidade de falhas.
- **evictions:** quantidade de remoções por limite de capacidade.
- **hitRatio:** proporção de acertos em relação ao total de consultas.
