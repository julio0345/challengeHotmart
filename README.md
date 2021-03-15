<h3>#challengeHotmart  \o/ </h3>

<br/>
<h4>Bora pra mais um desafio galera!</h4><br/>

Neste repositório encontra-se o fonte de uma API Top, que além de um CRUD de produtos, lista o ranking dos mesmos conforme suas vendas em um determinado período, além de consumir dados de uma API externa que busca notícias relacionadas a categoria destes produtos. Parece meio complexo né, mas planejando uma boa arquitetura, tudo dá certo no final :D <br/> [O ministério da saúde adverte: Pegue seu café antes de começar] <br/>
Antes de te contar mais detalhes do que ela faz, vou descrever os recursos que foram utilizados e como proceder pra buildar com sucesso ;) <br/><br/>

<b>Tecnologias</b> - (Não foi desenvolvido nenhum código Front devido a deadline naquele padrão que já conhecemos: "pra ontem" <br/>
• Java 11 \o/ <br/>
• Spring Boot -> Neste font foi utilizado a versão 2.4.3<br/>
• Lombok -> Chega de boiler plates \o/ <br/>
• ModelMapper -> Pensa num cara inteligente... <br/>
• JUnit -> Infelizmente não sobrou muito tempo pra ele, mas ele tá ai sim :)<br/>
• Mockito -> Pensa num cara mentiroso ¬¬ <br/>
• Swagger -> Deixa os docs com esse cara <br/>
• Hibernate (JPA) -> Nosso amigo do banco <br/>
• SpringData -> Nosso outro amigo do banco <br/>
• DevTools -> Hotdeploy everytime <br/>
• Maven -> Pensa num cara responsável<br/>
• Utilitários da Apache (Commons Lang, Commons Code, etc) <br/>
• Flyway -> Versionador de scripts muito top(DML, DDL, DCL, DQL e outras coisas com L no final :D) <br/>
• GitHub -> Nosso repositório lindo<br/>
• STS4 -> É so nosso velho Eclipse maquiado<br/>
• Postman -> nosso testador de APIS pra quem nao gosta das cores do Swagger :D <br/><br/>

<h4>Banco de dados </h4>

Nesta API utilizamos o banco de dados relacional MySQL.<br/>
Para configurar o acesso no seu banco de dados, confira as informações no arquivo <br/>
application.properties<br/>
no diretorio: .../hotmart-api/src/mains/resources<br/><br/>

Ok. Você já deve estar se perguntando porque eu não subi tudo isso no Docker né? pois é :( <br/>
Confesso que não o utilizei justamente devido a deadline, já que eu não tenho Docker instalado no momento, e pra ajudar ainda estou no Windows.<br/>
Ia gastar um tempinho bom pra deixar ele no jeito aki.. e nossa deadline de 2 dias não foi brincadeira, rsrs<br/>
Mas teremos mais oportunidades! <br/>
Então chega de falação e bora rodar a API<br/><br/>

 <h4>Como executar o projeto </h4>

Contando que você já tenha o Java 11 instalado na sua máquina (se ainda não, baixe em : https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html),
pelo prompt ou Git Bash, clone o projeto do repositorio do Git com o comando abaixo:<br/>
```
git clone https://github.com/julio0345/challengeHotmart.git
```

Após o download, importe o projeto na sua IDE de preferência (eu utilizei o STS4).<br/><br/>

Configure o acesso ao banco de dados pelo arquivo <b>application.properties </b> no diretorio: .../hotmart-api/src/mains/resources<br/><br/>
 
Build o projeto e o nosso amigo  <b>Flayway </b> se responsabilizará em criar e popular todo a estrutura do banco de dados necessário para utilizar a API.<br/><br/>

Outra forma de utilizar a API, seria  pelo terminal:<br/>
Nele, acesse o diretorio do projeto e execute o comando <br/>
```
mvn clean package
```
e sem seguida acesse o diretório onde foi criado o .jar (target) e execute o comando<br/>
```
java -jar hotmart-api-0.0.1-SNAPSHOT.jar
```
Maia uma vez, não se esqueça de instalar o <b>MySQL </b> (link para download: https://dev.mysql.com/downloads/) e configurar o seu usuário e senha do banco no arquivo application.properties<br/><br/>

 <h4>Documentação </h4>

Deixei essa parte com o  <b>Swagger </b>. Para acessá-la, utilize a URL: http://localhost:8080/swagger-ui.html#/<br/>
Tá eu sei, eu poderia ter colocado algumas annotations para esclarecer mais, mas acredite, o tempo foi muuuito curto.<br/><br/>

 <h4>Consumindo a API </h4>

<b>• CRUD de Produtos</b>

Como nosso banco ja estará populado com os scripts versionados pelo Flyway, você ja pode chamar a API pelo Swagger mesmo (http://localhost:8080/swagger-ui.html#/)
ou chamando a URI no Postman para o controller do Produto que disponibiliza um CRUD:<br/><br/>

<b>CREATE</b><br/>
• Insere um produto pelo body no corpo json: POST: http://localhost:8080/products <br/>
Exemplo Json<br/>
```
{
    "name" : "Curso de Spring REST",
    "description" : "Curso para iniciantes e experientes",
    "category" : {
        "id" : 1
    }
}
```

Como o produto tem uma entidade "Categoria" relacionada, eu solicitei a mesma como um objeto no json, na qual passamos um identificador da mesma.<br/>

 <b>READ </b><br/>
• Lista todos os produtos com paginação: GET: http://localhost:8080/products <br/><br/>

• Busca algum produto específico: GET: http://localhost:8080/products/{id} <br/><br/>

 <b>UPDATE </b><br/>
• Altera algum produto específico: PUT: http://localhost:8080/products/{id} <br/>
```
{
    "name" : "nome",
    "description" : "descrição",
    "category" : {
        "id" : 7
    }
}
```
Para o PUT você precisa de passar além do ID como parâmetro, um corpo (Json) informando o que deseja alterar no produto conforme mostrado acima.<br/><br/>

 <b>DELETE </b><br/>
• Exclui o produto específico: GET: http://localhost:8080/products/{id} <br/><br/>


<H3>• Ranking de Produto (Esse cara quase acabou comigo \o/ ) </H3>

É uma listagem onde o desafio pede para  calcular um score para cada produto com a seguinte fórmula: <br/> <br/>

X = Média de avaliação do produto nos últimos 12 meses <br/>
Y = Quantidade de vendas/dias que o produto existe <br/>
Z = Quantidade de notícias da categoria do produto no dia corrente (Dados consumidos de uma API externa) <br/>
Score = X + Y + Z <br/> <br/>

Este serviço consiste em buscar os produtos ordenados pelo ranqueamento, pelo nome e pela categoria. Visto que o input será uma categoria. <br/>
O output do serviço deve conter as informações  <br/>
Data Atual e a Categoria Pesquisada. <br/> <br/>

Sob esta Categoria, listaremos os produtos que atendem à pesquisa. <br/>
Os atributos de cada produto retornado são { identificador, nome, descrição, data de criação, score} <br/> <br/>

Como já realizei os testes e sei os dados fakes do banco, vou te dar um exemplo (dessa vez é de graça) de como encontrar os dados conforme a solicitação do desafio. <br/>
Acesse a URI do controller e passe o parametro "cursus" no verbo GET <br/>
GET: http://localhost:8080/ranking/cursus <br/> <br/>

Como essa API utiliza de um recurso de uma API externa, foi necessário criar uma chave para acesso da mesma. Mas não se preocupe. Já gerei uma pra mim e vou te emprestar ela ;)  <br/>
Caso queira conferir os dados trazidos da API externa, segue a URI abaixo: <br/> <br/>

GET: https://newsapi.org/v2/top-headlines?q=cursus&apiKey=cef8f96aec3a430c94fc00af34680ea2&pageSize=0  <br/> <br/>

Veja que nela têm os parâmetros: <br/>
<b>q</b>=cursus -> termo pesquisado <br/>
<b>apiKey</b>=cef8f96aec3a430c94fc00af34680ea2 -> minha chave (segredo em kkkk) <br/>
<b>pageSize</b>=0 -> Pra este serviço é desnecessário o conteúdo das notícias, apenas a quantidade das mesmas. <br/>
Para mais detalhes desta API externa, acesse: <br/>
https://newsapi.org/ <br/> <br/>


<H3>Esclarecimentos Finais</H3>

Pois é, o desafio que foi me passado (https://github.com/Hotmart-Org/hotmart-challenge/tree/master/back-end)  <br/>
esperava a utilização de muitos outros recursos que infelizmente não pude implementar justamente por causa da deadline (tive apenas um final de semana pra implementar :( )  <br/> <br/>

Por isso tive que mokar algumas informações dentro do código, como por exemplo, o método <b>needExecuteJOb()</b> da classe <b>RankingService.java</b> <br/>
Caso queira simular uma execução sem o consumo da API externa, deixe-o retornando false. True para o contrário. <br/> <br/>

Confesso que apanhei igual filho sem mãe dos testes Unitários e de Integração porque não estou habituados com os mesmos. (Cuidado, o Mock mata kkkkk) <br/>
Como estava gastando muitas horas neles e ainda estavam impedindo o build da API, tive que comentá-los e deixar com //TODO não tão distante (by Shurek). <br/> <br/>

Então é isso ai pessoal :) <br/> <br/>

Vou deixar meu e-mail para contato caso tenham dúvidas e fiquem a vontade para críticas. São elas quem nos fazem crescer \o/  <br/>
Bjos no coração <br/>
#TheEnd <br/> <br/>

Júlio Guimarães <br/>
julio0345@gmail.com
