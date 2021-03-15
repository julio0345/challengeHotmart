#challengeHotmart
Challenge of Hotmart \o/

Bora pra mais um desafio galera!

Neste repositório encontra-se o fonte de uma API Top, que além de um CRUD de produtos, lista o ranking dos mesmos conforme suas vendas em um determinado período, além de consumir dados de uma API externa que busca notícias relacionadas a categoria destes produtos. Parece meio complexo né, mas planejando uma boa arquitetura, tudo dá certo no final :D [O ministério da saúde adverte: Pegue seu café antes de começar]
Antes de te contar mais detalhes do que ela faz, vou descrever os recursos que foram utilizados e como proceder pra buildar com sucesso ;)

Tecnologias - (Não foi desenvolvido nenhum código Front devido a deadline naquele padrão que já conhecemos: "pra ontem"
• Java 11 \o/
• Spring Boot -> Neste font foi utilizado a versão 2.4.3
• Lambok -> Chega de boiler plates \o/
• ModelMapper -> Pensa num cara inteligente...
• JUnit -> Infelizmente não sobrou muito tempo pra ele, mas ele tá ai sim :)
• Mockito -> Pensa num cara mentiroso ¬¬
• Swagger -> Deixa os docs com esse cara
• Hibernate (JPA) -> Nosso amigo do banco
• SpringData -> Nosso outro amigo do banco
• DevTools -> Hotdeploy everytime
• Maven -> Pensa num cara responsável
• Utilitários da Apache (Commons Lang, Commons Code, etc)
• Flyway -> Versionador de scripts muito top(DML, DDL, DCL, DQL e outras coisas com L no final :D)
• GitHub -> Nosso repositório lindo
• STS4 -> É so nosso velho Eclipse maquiado
• Postman -> nosso testador de APIS pra quem nao gosta das cores do Swagger :D

Banco de dados

Nesta API utilizamos o banco de dados relacional MySQL.
Para configurar o acesso no seu banco de dados, confira as informações no arquivo 
application.properties
no diretorio: .../hotmart-api/src/mains/resources

Ok. Você já deve estar se perguntando porque eu não subi tudo isso no Docker né? pois é :( 
Confesso que não o utilizei justamente devido a deadline, já que eu não tenho Docker instalado no momento, e pra ajudar ainda estou no Windows.
Ia gastar um tempinho bom pra deixar ele no jeito aki.. e nossa deadline de 2 dias não foi brincadeira, rsrs
Mas teremos mais oportunidades! 
Então chega de falação e bora rodar a API

Como executar o projeto

Contando que você já tenha o Java 11 instalado na sua máquina (se ainda não, baixe em : https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html),
pelo prompt ou Git Bash, clone o projeto do repositorio do Git com o comando abaixo:
git clone https://github.com/julio0345/challengeHotmart.git

Após o download, importe o projeto na sua IDE de preferência (eu utilizei o STS4).

Configure o acesso ao banco de dados pelo arquivo application.properties no diretorio: .../hotmart-api/src/mains/resources
 
Build o projeto e o nosso amigo Flayway se responsabilizará em criar e popular todo a estrutura do banco de dados necessário para utilizar a API.

Outra forma de utilizar a API, seria  pelo terminal:
Nele, acesse o diretorio do projeto e execute o comando 
mvn clean package
e sem seguida acesse o diretório onde foi criado o .jar (target) e execute o comando
java -jar hotmart-api-0.0.1-SNAPSHOT.jar
Maia uma vez, não se esqueça de instalar o MySQL (link para download: https://dev.mysql.com/downloads/) e configurar o seu usuário e senha do banco no arquivo application.properties

Documentação
Deixei essa parte com o Swagger. Para acessá-la, utilize a URL: http://localhost:8080/swagger-ui.html#/
Ok eu poderia ter colocado algumas annotations para esclarecer mais, mas acredite, o tempo foi muuuito curto.

Consumindo a API

• CRUD de Produtos

Como nosso banco ja estará populado com os scripts versionados pelo Flyway, você ja pode chamar a API pelo Swagger mesmo (http://localhost:8080/swagger-ui.html#/)
ou chamando a URI no Postman para o controller do Produto que disponibiliza um CRUD:

CREATE
• Insere um produto pelo body no corpo json: POST: http://localhost:8080/products
Exemplo Json
{
    "name" : "Curso de Spring REST",
    "description" : "Curso para iniciantes e experientes",
    "category" : {
        "id" : 1
    }
}

Como o produto tem uma entidade "Categoria" relacionada, eu solicitei a mesma como um objeto no json, na qual passamos um identificador da mesma.

READ
• Lista todos os produtos com paginação: GET: http://localhost:8080/products

• Busca algum produto específico: GET: http://localhost:8080/products/{id}

UPDATE
• Altera algum produto específico: PUT: http://localhost:8080/products/{id}
{
    "name" : "nome",
    "description" : "descrição",
    "category" : {
        "id" : 7
    }
}

Para o PUT você precisa de passar além do ID como parâmetro, um corpo (Json) informando o que deseja alterar no produto conforme mostrado acima.

DELETE
• Exclui o produto específico: GET: http://localhost:8080/products/{id}


• Ranking de Produto (Esse cara quase acabou comigo \o/)

É uma listagem onde o desafio pede para  calcular um score para cada produto com a seguinte fórmula:

X = Média de avaliação do produto nos últimos 12 meses
Y = Quantidade de vendas/dias que o produto existe
Z = Quantidade de notícias da categoria do produto no dia corrente (Dados consumidos de uma API externa)
Score = X + Y + Z

Este serviço consiste em buscar os produtos ordenados pelo ranqueamento, pelo nome e pela categoria. Visto que o input será uma categoria.
O output do serviço deve conter as informações 
Data Atual e a Categoria Pesquisada.

Sob esta Categoria, listaremos os produtos que atendem à pesquisa.
Os atributos de cada produto retornado são { identificador, nome, descrição, data de criação, score}

Como já realizei os testes e sei os dados fakes do banco, vou te dar um exemplo (dessa vez é de graça) de como encontrar os dados conforme a solicitação do desafio.
Acesse a URI do controller e passe o parametro "cursus" no verbo GET
GET: http://localhost:8080/ranking/cursus

Como essa API utiliza de um recurso de uma API externa, foi necessário criar uma chave para acesso da mesma. Mas não se preocupe. Já gerei uma pra mim e vou te emprestar ela ;)
Caso queira conferir os dados trazidos da API externa, segue a URI abaixo:

GET: https://newsapi.org/v2/top-headlines?q=cursus&apiKey=cef8f96aec3a430c94fc00af34680ea2&pageSize=0

Veja que nela têm os parâmetros:
q=cursus -> termo pesquisado
apiKey=cef8f96aec3a430c94fc00af34680ea2 -> minha chave (segredo em kkkk)
pageSize=0 -> Pra este serviço é desnecessário o conteúdo das notícias, apenas a quantidade das mesmas.
Para mais detalhes desta API externa, acesse:
https://newsapi.org/


Esclarecimentos finais

Pois é, o desafio que foi me passado (https://github.com/Hotmart-Org/hotmart-challenge/tree/master/back-end) 
esperava a utilização de muitos outros recursos que infelizmente não pude implementar justamente por causa da deadline (tive apenas um final de semana pra implementar :( )

Por isso tive que mokar algumas informações dentro do código, como por exemplo, o método needExecuteJOb() da classe RankingService.java
Caso queira simular uma execução sem o consumo da API externa, deixe-o retornando false. True para o contrário.

Confesso que apanhei igual filho sem mãe dos testes Unitários e de Integração porque não estou habituados com os mesmos. (Cuidado, o Mock mata kkkkk)
Como estava gastando muitas horas neles e ainda estavam impedindo o build da API, tive que comentá-los e deixar com //TODO não tão distante (by Shurek).

Então é isso ai pessoal :)

Vou deixar meu e-mail para contato caso tenham dúvidas e fiquem a vontade para críticas. São elas quem nos fazem crescer \o/
Bjos no coração
#TheEnd

Júlio Guimarães
julio0345@gmail.com
