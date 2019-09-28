# Face Recognition

Sistema Distribuído para transformação de intensidade de imagens utilizando balanceamento de carga. Os serviços nos servidores utilizam uma interface RESTful para comunicação, contudo, a implementação ainda encontra-se incompleta na troca de imagens entre o RESTful e o Middleware.

## Iniciando o sistema

### Requisitos

Possuir o Android Studio e o NetBeans EE instalados.

### Execução

Inicialmente, o IP do Middleware deve ser inserido no cliente android. Em seguida, os IPs dos servidores que processarão a imagem de entrada devem ser definidos no Middleware, para que o mesmo envie as requisições para os hosts corretos.

A imagem capturada no dispositivo Android é enviada para o Middleware, que realiza o balanceamento de carga a fim de enviar a tarefa de processamento para o servidor mais ocioso.

Para executar o servidor de Middleware:

    java Middleware
    
Para executar os servidores RESTful, é necessário fazer a implantação de cada projeto e depois executá-los.

Com os servidores prontos, é só pressionar o botão da câmera no app e a imagem será enviada para o Middleware, que irá requisitar o processamento dela em um dos servidores.

## Desenvolvedores

* Açucena Rodrigues dos Santos Soares - [acucenarodrigues1998](<https://github.com/acucenarodrigues1998/>)
* Jederson Sousa Luz - [JedersonLuz](<https://github.com/JedersonLuz/>)
* Vitória de Carvalho Brito - [VitoriaCarvalho](<https://github.com/VitoriaCarvalho/>)

## Licença

[MIT](https://github.com/JedersonLuz/stats_parallel_servers/blob/master/LICENSE)
