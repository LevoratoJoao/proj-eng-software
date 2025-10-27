# Como Executar o Projeto

Recomendamos o uso de uma IDE como IntelliJ IDEA ou Eclipse para facilitar o desenvolvimento e execução do projeto e também instalação do Java 21 ou superior e Maven.

## Instalação

1. Certifique-se de ter o Java 17 e Maven instalados. Você pode verificar as versões instaladas com:

   ```bash
    java -version
    mvn -version
   ```

   Caso não tenha maven e java instalado, use os comandos abaixo:

   ```bash
    sudo apt install maven
    sudo apt install openjdk-17-jdk
   ```

2. Instale as dependências do projeto usando Maven:
   ```bash
   mvn clean install
   ```

## Executando o Projeto

1. Após instalar as dependências, você pode executar o projeto com o seguinte comando:
   ```bash
   mvn spring-boot:run
   ```
2. O servidor estará disponível em `http://localhost:8080`. Para conferir as rotas disponíveis, você pode acessar a documentação da API gerada pelo Swagger em `http://localhost:8080/swagger-ui/index.html#/`.

3. Para acessar o banco de dados H2 (em memória), você pode usar o console do H2 em `http://localhost:8080/h2-console`. Use as seguintes credenciais:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - User Name: `sa`
   - Password: `password`
