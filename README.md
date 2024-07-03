<div align="center">

![Static Badge](https://img.shields.io/badge/Status-Em%20Constru%C3%A7%C3%A3o-%23FFD700)

# View Model com Jetpack Compose
  
</div>
<p>
O objetivo do ViewModel é <b>separar o modelo de dados e a lógica, relacionados à interface do usuário de um aplicativo, do código responsável por exibir e gerenciar a interface do usuário e interagir com o sistema operacional.</b>
Quando projetado dessa forma, um aplicativo consistirá em um ou mais controladores de UI, como uma activity, juntamente com instâncias de ViewModel responsáveis ​​por lidar com os dados necessários a esses controladores. 
Um ViewModel é implementado como uma classe separada e contém valores de estado contendo os dados do modelo e funções que podem ser chamadas para gerenciar esses dados. A activity que contém a interface do usuário observa os valores de estado do     modelo, de forma que qualquer alteração de valor acione uma recomposição. Os eventos da interface do usuário relacionados aos dados do modelo, como um clique de botão, são configurados para chamar a função apropriada no ViewModel.
</p>

<div align="center"><br>

<img src="https://github.com/DeniseLeandroDeCastro/view_model_demo/assets/29150094/ec23101b-008d-43d8-863a-7f38c110c5a6" width="500" height="300"/>
  
</div><br>

<p>
Esta separação de responsabilidades aborda as questões relacionadas com o ciclo de vida das activities. Independentemente de quantas vezes uma activity é recriada durante o ciclo de vida de um aplicativo, as instâncias do ViewModel permanecem na memória, mantendo assim, a consistência dos dados. Um ViewModel usado por uma activity, por exemplo, permanecerá na memória até que a activity termine, o que, no aplicativo de activity única, não ocorre até que o aplicativo exista. O principal objetivo de um ViewModel é armazenar dados que podem ser observados pela interface do usuário de uma activity. Isso permite que a interface do usuário reaja quando ocorrerem alterações nos dados do ViewModel. Existem duas maneiras de declarar os dados em uma declaração de ViewModel para que sejam observáveis. Uma opção é usar o mecanismo de estado Compose. Uma abordagem alternativa é usar o componente JetpackLiveData. Assim como o estado declarado nos elementos <b>composable</b>, o estado do ViewModel é declarado usando o grupo de funções <b>mutableStateOf.</b> A declaração ViewModel a seguir, por exemplo, declara um estado contendo um valor de contagem inteiro com um valor inicial de 0:
</p> <br>

```kotlin

class MyViewModel {

  var customerCount by mutableStateOf(0)
  
}

```

<p>
  Com alguns dados encapsulados no modelo, a próxima etapa é adicionar uma função que pode ser chamada de dentro da UI para alterar o valor do contador.
</p>

```kotlin

class MyViewModel {

  var customerCount by mutableStateOf(0)

  fun increaseCount() {
      customerCount++
  }
  
}

```
