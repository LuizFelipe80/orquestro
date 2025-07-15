Documentação Técnica - Plataforma Orquestro
Versão: 1.0 Data: 12 de Julho de 2025
1. Visão Geral
A Plataforma Orquestro é um backend de base (core) projetado para servir como fundação para diversas aplicações de negócio modulares. Ela fornece um sistema robusto e seguro para gerenciamento de identidade, acesso, papéis e sessões, permitindo que novas aplicações sejam construídas sobre esta base sólida, focando em suas regras de negócio específicas.
2. Arquitetura
A plataforma foi construída seguindo padrões modernos de arquitetura de software para garantir escalabilidade, manutenibilidade e segurança.
•	Multi-Módulo (Maven): O projeto é dividido em módulos com responsabilidades claras:
o	system: O núcleo da plataforma. Contém as entidades mais fundamentais (User, UserRole, Session, BaseEntity) e seus repositórios.
o	administration: Um exemplo de módulo de negócio que estende a funcionalidade do sistema, fornecendo uma implementação concreta de User através da entidade Account.
o	app: O módulo executável. Une todos os outros módulos, contém a configuração principal (Spring, Security, Flyway), os Controllers da API e o ponto de entrada da aplicação.
•	Camada de Dados (JPA/Hibernate):
o	A persistência de dados é gerenciada pelo Spring Data JPA com Hibernate.
o	Utiliza um padrão de Herança (@Inheritance(strategy = InheritanceType.JOINED)) para permitir que entidades base como User sejam especializadas por módulos (Account).
o	Uma classe BaseEntity com @MappedSuperclass fornece campos de auditoria (createdAt, updatedAt) e de estado (entity_state) para todas as entidades principais, garantindo consistência.
•	Gerenciamento de Banco de Dados (Flyway):
o	A evolução do esquema do banco de dados é controlada por Flyway. Todas as alterações na estrutura são feitas através de scripts SQL versionados (V1__..., V2__...), garantindo um histórico confiável e implantações seguras.
o	O Hibernate está configurado para validate, agindo como uma trava de segurança que garante que as entidades Java estejam sempre em sincronia com o banco de dados gerenciado pelo Flyway.
•	Segurança (Spring Security):
o	Autenticação: É baseada em token de sessão. O cliente faz login via POST /api/auth/login e recebe um sessionToken (UUID).
o	TokenAuthenticationFilter: Um filtro customizado intercepta cada requisição, valida o sessionToken enviado no cabeçalho Authorization: Bearer <token> e estabelece o contexto de segurança.
o	Autorização: A segurança em nível de método está habilitada (@EnableMethodSecurity), permitindo proteger lógicas de negócio específicas com anotações como @PreAuthorize("hasRole('DEVELOPER')").
o	Hashing de Senhas: As senhas são armazenadas de forma segura usando o algoritmo BCrypt.

3. Guia de API (Endpoints)
Todos os endpoints, exceto /api/auth/login, requerem um token válido no cabeçalho Authorization.
Endpoint	Método	Descrição	Autenticação Requerida?
/api/auth/login	POST	Autentica um usuário e cria uma nova sessão, retornando o sessionToken.	Não
/api/auth/logout	POST	Invalida a sessão do token enviado.	Sim
/api/users	GET	Lista todos os usuários base.	Sim
/api/users/{id}	GET	Busca um usuário específico pelo ID.	Sim
/api/users	POST	Cria um novo usuário (requer autenticação de um admin/usuário existente).	Sim
/api/users/{id}	PUT	Atualiza os dados de um usuário.	Sim
/api/users/{id}	DELETE	Realiza a exclusão lógica de um usuário (muda o estado).	Sim
/api/users/{id}/physical	DELETE	Exclui fisicamente um usuário.	Sim (Papel DEVELOPER)
/api/users/{userId}/roles/{roleId}	POST	Associa um papel a um usuário.	Sim
/api/users/{userId}/roles/{roleId}	DELETE	Remove a associação de um papel de um usuário.	Sim
/api/user-roles	GET	Lista todos os papéis de usuário.	Sim
/api/user-roles	POST	Cria um novo papel de usuário (o nome é normalizado).	Sim
/api/admin/accounts	GET	Lista todas as contas do tipo Account.	Sim
/api/admin/accounts	POST	Cria uma nova Account.	Sim
/api/admin/accounts/{id}/restore	POST	Restaura uma conta (muda o estado para ACTIVE).	Sim


4. Próximos Passos (Roadmap do Backend)
Estes são os tópicos que estão no nosso radar para as próximas iterações:
•	ModuloRole: Implementar um modelo de permissões mais granular, onde um UserRole pode ter capacidades diferentes em diferentes módulos do sistema.
•	grantableRoles: Ativar a lógica da hierarquia de papéis para definir qual papel pode administrar quais outros papéis.
•	Idioma: Criar a entidade e a lógica para que cada usuário possa ter uma preferência de idioma.
•	Tratamento Global de Exceções: Implementar um @ControllerAdvice para capturar erros de forma centralizada e retornar respostas de erro padronizadas na API.
•	Documentação da API: Integrar o OpenAPI/Swagger para gerar uma documentação interativa e automática dos nossos endpoints.
