# Enterprise Tactical RPG 2

**Número da Lista**: 25<br>
**Conteúdo da Disciplina**: Grafos 2<br>

## Alunos
|Matrícula | Aluno |
| ---------- | -- |
| 15/0058462 |  Davi Antônio da Silva Santos |
| 18/0100840 |  Gabriel Azevedo Batalha |

## Sobre 
 É uma evolução do projeto de Grafos 1, um jogo de sobrevivência onde o jogador precisa fugir durante o maior tempo possível das unidades inimigas.

## Screenshots

![Jogo em execução](https://i.imgur.com/HdfvKZu.png)


Jogo em execução

![Jogo em execução 2](https://i.imgur.com/VMevzdd.png)


Jogo em execução 

![Tela de fim de jogo](https://i.imgur.com/zP7EUYP.png)

Tela de fim de jogo

## Instalação 
**Linguagem**: Java<br>
**Framework**: Swing<br>

### Requisitos
- Java JRE 11 ou superior.
  - JDK 11 ou superior exigido para compilar ou desenvolver
- Computador com *mouse*.

## Uso 

Clone o repositório para compilar o projeto ou baixe somente o .jar disponível nas [releases](https://github.com/projeto-de-algoritmos/Grafos2_Enterprise_Tactical_RPG_2/releases)


Para executar o programa, use
```
java -jar EnterpriseTacticalRPG2.jar
```
O jogo é controlado pelo *mouse*.

O jogador é um ponto azul na tela e deve fugir dos pontos vermelhos
inimigos.

As áreas são coloridas conforme os custos para atravessá-las. Regiões
verdes possuem o custo mais baixo, e quanto mais amarelo, mais alto o
custo. Regiões pretas são intransponíveis.

A partida termina quando o jogador é alcançado por qualquer um dos
inimigos ou quando não há movimentos válidos restantes.

## Desenvolvimento
Ao importar o projeto em sua IDE talvez seja necessário retirar o
.jar. É possível que a IDE tente usar as classes empacotadas no lugar
das que estão definidas no código fonte.

## Outros 
Os movimentos do jogador e dos inimigos agora são determinados pelo 
algoritmo de Dijkstra implementado para traçar o caminho de menor custo
em uma matriz de elementos genéricos. Também agora existem casas com custos
de travessia mais altos e obstáculos no mapa. Foram realizadas otimizações
no código anterior para se diminuir o alto uso de CPU.
