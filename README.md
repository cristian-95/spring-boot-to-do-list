# my to-do list

---

*(A seguinte proposta de projeto foi gerada utilizando chatGPT para estimular o estudo do desenvolvimento com o Spring Boot)*

**Projeto: API de Gerenciamento de Tarefas (To-Do List)**

**Descrição:**
A API permitirá que os usuários criem uma lista de tarefas, onde cada tarefa terá um título, uma descrição, uma data de criação e um status (concluída ou não). Os usuários poderão realizar operações CRUD (Create, Read, Update, Delete) nas tarefas.

**Recursos da API:**
1. **Criar Tarefa:** Os usuários poderão criar uma nova tarefa, fornecendo um título e uma descrição.
2. **Listar Tarefas:** Os usuários poderão listar todas as tarefas presentes na sua lista.
3. **Visualizar Tarefa:** Os usuários poderão visualizar os detalhes de uma tarefa específica, incluindo título, descrição, data de criação e status.
4. **Atualizar Tarefa:** Os usuários poderão atualizar o título, descrição e status de uma tarefa existente.
5. **Excluir Tarefa:** Os usuários poderão excluir uma tarefa da sua lista.

**Endpoints da API:**
- `POST /tasks`: Cria uma nova tarefa.
- `GET /tasks`: Retorna a lista de todas as tarefas.
- `GET /tasks/{id}`: Retorna os detalhes de uma tarefa específica.
- `PUT /tasks/{id}`: Atualiza os detalhes de uma tarefa específica.
- `DELETE /tasks/{id}`: Exclui uma tarefa específica.

**Modelo de Dados:**
```java
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private boolean completed;

    // Construtores, getters e setters
}
```

**Tecnologias:**
- Java Spring Boot: Para criar a estrutura da aplicação e definir os endpoints da API.
- PostgreSQL: Para armazenar as informações das tarefas.
- Spring Data JPA: Para facilitar a integração com o banco de dados.
- Spring Web: Para criar a API RESTful.
- Postman ou qualquer ferramenta similar para testar a API.

**Passos Iniciais:**
1. Configure um projeto Spring Boot com as dependências necessárias, incluindo Spring Web e Spring Data JPA.
2. Crie as classes de modelo, repositório, serviço e controlador para as tarefas.
3. Implemente os endpoints CRUD utilizando as operações básicas do Spring Data JPA.
4. Teste a API usando o Postman ou ferramentas similares.
5. Certifique-se de adicionar tratamento de erros e validações necessárias nos endpoints.
6. Configure o PostgreSQL como banco de dados e ajuste as configurações no arquivo `application.properties`.
7. Execute a aplicação e comece a interagir com a API.

Lembre-se de que este é um projeto básico para praticar o desenvolvimento de APIs RESTful com Spring Boot e PostgreSQL. Você pode expandir esse projeto adicionando recursos como autenticação de usuários, categorização de tarefas, prioridades, etc. Isso lhe dará a oportunidade de aprofundar seus conhecimentos e adicionar complexidade ao projeto conforme você se sentir confortável.