# Métodos disponíveis na API:
**OBS: Nesta documentação, as URL's dos métodos estão iniciando com "localhost:8080". Porém, 
ao usar tais métodos, substitua este trecho pelo domínio de produção, assim que eu tiver um, irei disponibilizá-lo.

## 1 - Métodos para Autenticação e Autorização:

### 1.1 - Login:

#### URL da requisição: localhost:8080/api/auth/login
#### Tipo da requisição: POST
#### Corpo da requisição: O corpo da requisição deve ser do tipo JSON, abaixo está um exemplo dela:
![corpo_da_requisição_para_login](https://github.com/user-attachments/assets/39444cc2-4153-48ca-a256-c76608042650)
#### Corpo da resposta: A resposta tem como status 200, indicando que o usuário se autenticou na aplicação. O seu corpo está no formato JSON, contendo o token JWT no qual deve ser usado pelo usuário para acessar os recursos da API. Abaixo está um exemplo dela:
![corpo_da_resposta_para_login](https://github.com/user-attachments/assets/1dc501bd-e5c3-4878-95d8-1fe77846864b)
#### Avisos:
- Se os dados enviados não pertencam a um usuário cadastrado na aplicação, a API irá devolver uma resposta com status 403 e sem nenhum conteúdo em seu corpo.
- O dado para o campo "cpf" deve ser enviado sem nenhum sinal, apenas com 11 números. Além disso, esse CPF precisa ser válido, a API contém uma verificação para isso. Se o dado enviado estiver inválido, a resposta da API será:
  
  ![resposta_com_erro_de_CPF](https://github.com/user-attachments/assets/611c0bc7-a47a-404c-ae5e-033ef6f28f34)



## 2 - Métodos para Usuário:

### 1.1 - Criar usuário:

#### URL da requisição: localhost:8080/api/user
#### Tipo da requisição: POST
#### Corpo da requisição: O corpo da requisição deve ser do tipo JSON, abaixo está um exemplo dela:
![corpo_da_requisição_para_criar_usuario](https://github.com/user-attachments/assets/8219bc0f-cd5a-4edf-848f-389f52a2c22c)
#### Corpo da resposta: A resposta tem como status 201, indicando que o usuário foi cadastrado na aplicação. Não há nenhum dado no corpo da resposta.
#### Avisos:
- O dado para o campo "cpf" não deve ser enviado com sinais, apenas com 11 números. Além disso, esse CPF precisa ser válido, a API contém uma verificação para isso.
- O dado do campo "phoneNumber" não deve ser enviado com sinais, apenas com 11 números.
- O dado para o campo "birthDate" deve ser enviado no formato "yyyy-MM-dd".
- Não é permitido mais de um usuário cadastrado com os mesmos CPF ou número de telefone.
  
#### Abaixo estão possíveis respostas que você pode receber se caso alguma das regras exibidas acima forem detectadas:
![resposta_com_erro_de_CPF](https://github.com/user-attachments/assets/d7aa6e91-b127-412f-bb6c-0639f6110302)

![resposta_com_erro_de_numero_telefone_e_data_de_nascimento](https://github.com/user-attachments/assets/7f5166e2-a64f-425b-937b-5ce09745cd7c)

![resposta_com_erro_de_CPF_repetido](https://github.com/user-attachments/assets/81ce3641-a1d3-4e66-af0c-6aaccd2f11bf)

![resposta_com_erro_de_numero_telefone_repetido](https://github.com/user-attachments/assets/bff14635-f0e7-4040-a459-a1dbcbb63321)

