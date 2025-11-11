# 🚀 GUIA DE DEPLOYMENT - Portal de Estágios

## Pré-requisitos

### Sistema
- **SO:** Ubuntu 24.04 LTS (ou equivalente)
- **Java:** OpenJDK 21+
- **Banco de Dados:** PostgreSQL 15+
- **Build Tool:** Maven 3.8+

### Verificar instalações
```bash
java -version
mvn --version
psql --version
```

---

## 📦 Preparação

### 1. Clonar o Repositório
```bash
cd /workspaces/portal_estagios
git clone <seu-repositorio>
cd estagio
```

### 2. Configurar PostgreSQL

#### Criar usuário e banco de dados
```bash
sudo -u postgres psql
```

```sql
-- Criar usuário
CREATE USER estagio_user WITH PASSWORD 'sua_senha_segura';

-- Criar banco de dados
CREATE DATABASE estagio_db OWNER estagio_user;

-- Conceder permissões
GRANT ALL PRIVILEGES ON DATABASE estagio_db TO estagio_user;

-- Sair
\q
```

### 3. Atualizar Configuração

**Arquivo:** `src/main/resources/application.properties`

```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/estagio_db
spring.datasource.username=estagio_user
spring.datasource.password=sua_senha_segura
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Application
spring.application.name=estagio
server.port=8080

# Logging
logging.level.root=INFO
logging.level.mackenzie.estagio=DEBUG

# Swagger/OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

---

## 🔨 Build do Projeto

### 1. Compilar
```bash
mvn clean compile
```

### 2. Executar Testes
```bash
mvn test
```

### 3. Empacotar (Gera JAR executável)
```bash
mvn clean package -DskipTests
```

**Output esperado:**
```
[INFO] Building jar: .../target/estagio-0.0.1-SNAPSHOT.jar
[INFO] BUILD SUCCESS
```

---

## ▶️ Executar a Aplicação

### Opção 1: Via Maven
```bash
mvn spring-boot:run
```

### Opção 2: Via JAR (Recomendado para Produção)
```bash
java -jar target/estagio-0.0.1-SNAPSHOT.jar
```

### Opção 3: Com Variáveis de Ambiente
```bash
java -jar target/estagio-0.0.1-SNAPSHOT.jar \
  --spring.datasource.username=estagio_user \
  --spring.datasource.password=sua_senha_segura \
  --server.port=8080
```

---

## 🌐 Acessar a Aplicação

### URLs
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/api-docs
- **Health Check:** http://localhost:8080/actuator/health (se habilitado)

### Testar Login (Exemplo)
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "senha": "senha123"
  }'
```

---

## 🐳 Deployment via Docker (Opcional)

### 1. Criar Dockerfile
```dockerfile
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/estagio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. Construir Imagem
```bash
docker build -t portal-estagios:1.0 .
```

### 3. Executar Container
```bash
docker run -d \
  --name portal-estagios \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/estagio_db \
  -e SPRING_DATASOURCE_USERNAME=estagio_user \
  -e SPRING_DATASOURCE_PASSWORD=sua_senha_segura \
  portal-estagios:1.0
```

---

## 🔒 Configurações de Produção

### 1. Desabilitar Ferramentas de Debug
```properties
# application-prod.properties
spring.jpa.show-sql=false
logging.level.root=WARN
springdoc.api-docs.enabled=false
springdoc.swagger-ui.enabled=false
```

### 2. Executar com Profile de Produção
```bash
java -jar target/estagio-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 3. Configurar HTTPS
```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=sua_senha
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

### 4. Limitar Recursos
```bash
java -Xmx512m -Xms256m -jar target/estagio-0.0.1-SNAPSHOT.jar
```

---

## 📊 Monitoramento

### Verificar Status
```bash
curl http://localhost:8080/actuator/health
```

### Ver Logs
```bash
# Se executando com Spring Boot
tail -f logs/application.log

# Se executando com JAR
# Os logs serão exibidos no console
```

### Usar Systemd (Linux)

**Arquivo:** `/etc/systemd/system/portal-estagios.service`
```ini
[Unit]
Description=Portal de Estágios
After=network.target

[Service]
Type=simple
User=app-user
WorkingDirectory=/opt/portal-estagios
ExecStart=/usr/bin/java -jar /opt/portal-estagios/estagio-0.0.1-SNAPSHOT.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Ativar serviço:
```bash
sudo systemctl daemon-reload
sudo systemctl enable portal-estagios
sudo systemctl start portal-estagios
sudo systemctl status portal-estagios
```

---

## 🧪 Teste de Funcionalidades

### 1. Listar Todos os Estudantes
```bash
curl http://localhost:8080/api/estudantes
```

### 2. Criar Novo Estudante
```bash
curl -X POST http://localhost:8080/api/estudantes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "12345678901",
    "curso": "Engenharia de Software",
    "telefone": "11999999999",
    "usuario": {
      "email": "joao@example.com",
      "senha": "senha123",
      "tipo": "ESTUDANTE"
    }
  }'
```

### 3. Gerar Currículo em PDF
```bash
curl http://localhost:8080/api/estudantes/1/curriculo/pdf \
  -o curriculo.pdf
```

### 4. Buscar Vagas Abertas
```bash
curl http://localhost:8080/api/vagas/abertas
```

### 5. Fazer Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "senha": "senha123"
  }'
```

---

## 🐛 Troubleshooting

### Erro: "Connection refused"
**Problema:** PostgreSQL não está rodando
**Solução:**
```bash
sudo systemctl start postgresql
# ou
sudo service postgresql start
```

### Erro: "Database estagio_db does not exist"
**Problema:** Banco de dados não foi criado
**Solução:** Execute os comandos SQL da seção "Configurar PostgreSQL"

### Erro: "Port 8080 already in use"
**Problema:** Outra aplicação está usando a porta
**Solução:**
```bash
# Matar processo na porta 8080
lsof -ti:8080 | xargs kill -9
# ou usar outra porta
java -jar target/estagio-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Erro: "PDFBox font error"
**Problema:** Versão incompatível do PDFBox
**Solução:** Este projeto já está corrigido com PDFBox 3.0.1. Verificar pom.xml

---

## 📈 Scaling (Múltiplas Instâncias)

Para escalar em produção:

### 1. Load Balancer (Nginx)
```nginx
upstream portal_estagio {
    server localhost:8080;
    server localhost:8081;
    server localhost:8082;
}

server {
    listen 80;
    server_name api.example.com;

    location / {
        proxy_pass http://portal_estagio;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

### 2. Executar Múltiplas Instâncias
```bash
# Terminal 1
java -jar target/estagio-0.0.1-SNAPSHOT.jar --server.port=8080

# Terminal 2
java -jar target/estagio-0.0.1-SNAPSHOT.jar --server.port=8081

# Terminal 3
java -jar target/estagio-0.0.1-SNAPSHOT.jar --server.port=8082
```

### 3. Database Replication (PostgreSQL)
Configurar replicação do PostgreSQL para alta disponibilidade

---

## ✅ Checklist de Deployment

- [ ] Java 21 instalado
- [ ] PostgreSQL 15+ instalado e rodando
- [ ] Banco de dados criado
- [ ] `application.properties` configurado
- [ ] Maven build bem-sucedido
- [ ] Testes passando
- [ ] JAR gerado sem erros
- [ ] Aplicação inicia sem erros
- [ ] Swagger UI acessível
- [ ] API respondendo em localhost:8080
- [ ] Login funcionando
- [ ] CRUD de estudantes funcionando
- [ ] Geração de PDF funcionando
- [ ] Logs sendo gerados corretamente

---

## 📞 Suporte

Para problemas ou dúvidas:

1. Verificar logs: `tail -f logs/application.log`
2. Executar teste de conectividade: `curl -v http://localhost:8080/actuator/health`
3. Consultar documentação Swagger: http://localhost:8080/swagger-ui.html
4. Verificar status do PostgreSQL: `psql -U postgres -d estagio_db`

---

**Versão:** 1.0  
**Última Atualização:** 10 de Novembro de 2025  
**Status:** PRONTO PARA PRODUÇÃO ✅
