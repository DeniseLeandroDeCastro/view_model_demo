<div align="center">

![Static Badge](https://img.shields.io/badge/status-Conclu%C3%ADdo-%2390EE90) <br>
:smiley:


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
Esta separação de responsabilidades aborda as questões relacionadas com o ciclo de vida das activities. Independentemente de quantas vezes uma activity é recriada durante o ciclo de vida de um aplicativo, as instâncias do ViewModel permanecem na memória, mantendo assim, a consistência dos dados. Um ViewModel usado por uma activity, por exemplo, permanecerá na memória até que a activity termine, o que, no aplicativo de activity única, não ocorre até que o aplicativo exista. O principal objetivo de um ViewModel é armazenar dados que podem ser observados pela interface do usuário de uma activity. Isso permite que a interface do usuário reaja quando ocorrerem alterações nos dados do ViewModel. Existem duas maneiras de declarar os dados em uma declaração de ViewModel para que sejam observáveis. Uma opção é usar o mecanismo de estado Compose. Uma abordagem alternativa é usar o componente JetpackLiveData. Assim como o estado declarado nos elementos <b>composable</b>, o estado do ViewModel é declarado usando o grupo de funções <b>mutableStateOf.</b>
</p>
<p>
Quando um valor de temperatura é inserido no <b>OutlinedTextField</b> e o botão é clicado, o valor convertido aparecerá em um componente de texto resultante. O componente <b>Switch</b> é usado para indicar se a temperatura inserida é Fahrenheit ou Celsius. A configuração atual da chave, o resultado da conversão e a lógica de conversão estarão todos contidos em um ViewModel.
</p>

<div align="center">

<img src="https://github.com/DeniseLeandroDeCastro/view_model_demo/assets/29150094/ec15eecd-e1fb-4425-8fd2-a0868ac9a1c5" width="300" height="600"/>

</div>

## Class ViewModel
<p>
  O ViewModel precisa conter valores de estado nos quais armazenar o resultado da conversão e a posição atual da chave como a seguir:
</p>

```kotlin
class DemoViewModel(
  var isFahrenheit by mutablesStateOf(true)
  var result by mutableStateOf("")
)
```

<p>
A classe também precisa conter a lógica do modelo, começando com uma função para realizar a conversão da unidade de temperatura. Como a temperatura é inserida pelo usuário em um campo de texto, ela é passada para a função como uma String. Além de realizar o cálculo, o código também precisa converter entre os tipos <b>String</b> e <b>Integer.</b> Este código também precisa garantir que o usuário inseriu um número válido. Permanecendo no arquivo <b>DemoViewModel.kt</b>, adicione uma nova função chamada <b>convertTemp()</b> para que fique assim:
</p>

```kotlin
class DemoViewModel {

    var isFahrenheit by mutableStateOf(false)
    var result by mutableStateOf("0")

    fun convertTemp(temp: String) {
        try {
            val tempInt = temp.toInt()
            if (isFahrenheit) {
                result = ( (tempInt - 32) * 0.5556 ).roundToInt().toString()
            } else {
                result = ( (tempInt * 1.8) + 32 ).roundToInt().toString()
            }
        } catch(e: Exception) {
            result = "Entrada inválida!"
        }
    }
    fun switchChange() {
        isFahrenheit = !isFahrenheit
    }
}
```

<p>
A função acima começa convertendo o valor da da temperatura em <b>String</b> em um número inteiro. Isso é executado no contexto de uma instrução <i>try ... catch</i> que relata entrada inválida se o texto não for igual a um número válido. Em seguida, a conversão apropriada é realizada dependendo das configurações <i><b>isFahrenheit</b></i> atuais e o resultado é arredondado para um número inteiro e convertido novamente em uma string, antes de ser atribuído à variável de estado do resultado. A outra função que precisa ser adicionada ao <b>view model</b> será chamada quando a configuração da chave for alterada e simplesmente inverter a configuração atual do estado <i><b>isFahrenheit</b></i>:
</p>

```kotlin
fun switchChange() {
  isFahrenheit = !isFahrenheit
}
```

## Acessando DemoViewModel da MainActivity

<p>
Agora que declaramos uma classe <b>ViewModel</b>, a próxima etapa é criar uma instância e integrá-la aos elementos <b>composables</b> que compõem nossa MainActivity. Este projeto envolverá a criação de uma instância <b>DemoViewModel</b> como parâmetro para a função <b>ScreenSetup</b> e a passagem das variáveis ​​de estado e referências de função para a função MainScreen. Abra MainActivity no editor de código e faça as seguintes alterações:
</p>

```kotlin
@Composable
fun ScreenSetup(viewModel: DemoViewModel = DemoViewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel. result,
        convertTemp = { viewModel.convertTemp(it) },
        switchChange = { viewModel.switchChange() }
    )
}


@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit
) {
//TODO
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview(viewModel: DemoViewModel = DemoViewModel()) {
    ViewModelDemoTheme {
        MainScreen(
            isFahrenheit = viewModel.isFahrenheit,
            result = viewModel.result,
            convertTemp = { viewModel.convertTemp(it) },
            switchChange = { viewModel.switchChange() }
        )
    }
}
```

Para evitar que a função <b>MainScreen</b> fique desordenada, os componentes <b>Switch, OutlinedTextField</b> e <b>Text</b> do indicador de unidade serão colocados em um elemento composable separado chamado <b>InputRow</b>, que agora pode ser adicionado à <b>MainActivity.</b>

```kotlin
@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange() },
            modifier = Modifier.padding(end = 10.dp)
        )
        OutlinedTextField(
            value = textState,
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number ),
            singleLine = true,
            label = { Text(stringResource(R.string.label_outlined_text_field)) },
            modifier = Modifier.padding(2.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ac_unit),
                    contentDescription = "Ícone de temperatura frio",
                    modifier = Modifier.size(40.dp),
                    tint = colorResource(id = R.color.dodger_blue)
                )
            },
            shape = MaterialTheme.shapes.large
        )
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(3000),
            modifier = Modifier.padding(end = 10.dp),
        ) { visible ->
            when (visible) {
                true -> Text("\u2109", style = MaterialTheme.typography.headlineMedium)
                false -> Text("\u2103", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
```

<p>
A função <b>InputRow</b> espera como parâmetros os valores de estado e funções contidas no view model junto com uma variável de estado <b>textState</b> e um manipulador de eventos <b>onTextChange.</b> Esses dois últimos parâmetros são usados ​​para exibir o texto digitado pelo usuário no campo de texto e serão "içados" para a função MainScreen. O valor textState atual também é passado para a função convertTemp() quando o usuário clica no botão.
</p>

<div align="center">

![image](https://github.com/DeniseLeandroDeCastro/view_model_demo/assets/29150094/ad53686a-c374-4dd1-b658-e24b5b2d7821)
 
</div>
<br>
<p>Quando o campo está em foco, entretanto, o rótulo aparece como um título posicionado dentro do contorno.</p> <br>

<div align="center">

![image](https://github.com/DeniseLeandroDeCastro/view_model_demo/assets/29150094/c6b73a0d-fb22-4d88-8589-4c1e76c0d44f)

</div><br>

<p>
O resultado de uma chamada para a função <b>TextStyle</b> é atribuído à propriedade <b>textStyle</b> do <b>OutlinedTextField. TextStyle</b> é usado para agrupar configurações de estilo em um único objeto que pode ser aplicado a outros elementos composables em uma única operação. Neste caso, estamos apenas definindo a espessura e o estilo da fonte, mas <b>TextStyle</b> também pode ser usado para definir configurações de estilo, incluindo cor, plano de fundo, família de fontes, sombra, alinhamento de texto, espaçamento entre letras e recuo de texto.<br>

A propriedade <b>trailingIcon</b> é usada para posicionar o ícone adicionado anteriormente no final da área de entrada de texto:
</p>

```kotlin
 trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ac_unit),
                    contentDescription = "Ícone de temperatura frio",
                    modifier = Modifier.size(40.dp),
                    tint = colorResource(id = R.color.dodger_blue)
                )
            }
```
<p>
Finalmente, a animação crossfade é usada ao alternar o campo de texto da unidade entre ºF e Cº (representado pelos valores Unicode <b>\u2109</b> e <b>\u2103</b> respectivamente) com base na configuração <b>isFahrenheit</b> atual.
</p>

## Código completo da MainActivity

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ViewModelDemoTheme {
                ScreenSetup()
            }
        }
    }
}
@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange() },
            modifier = Modifier.padding(end = 10.dp)
        )
        OutlinedTextField(
            value = textState,
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number ),
            singleLine = true,
            label = { Text(stringResource(R.string.label_outlined_text_field)) },
            modifier = Modifier.padding(2.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ac_unit),
                    contentDescription = "Ícone de temperatura frio",
                    modifier = Modifier.size(40.dp),
                    tint = colorResource(id = R.color.dodger_blue)
                )
            },
            shape = MaterialTheme.shapes.large
        )
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(3000),
            modifier = Modifier.padding(end = 10.dp),
        ) { visible ->
            when (visible) {
                true -> Text("\u2109", style = MaterialTheme.typography.headlineMedium)
                false -> Text("\u2103", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = DemoViewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel. result,
        convertTemp = { viewModel.convertTemp(it) },
        switchChange = { viewModel.switchChange() }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var textState by remember { mutableStateOf("") }
        val onTextChange = { text: String -> textState = text }
        Text(
            "Conversor de temperatura",
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        InputRow(
            isFahrenheit = isFahrenheit,
            textState = textState,
            switchChange = switchChange,
            onTextChange = onTextChange
        )
        Text(
            result,
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.displayLarge
        )
        Button(
            onClick = { convertTemp(textState) }
        ) {
            Text("Converter Temperatura")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview(viewModel: DemoViewModel = DemoViewModel()) {
    ViewModelDemoTheme {
        MainScreen(
            isFahrenheit = viewModel.isFahrenheit,
            result = viewModel.result,
            convertTemp = { viewModel.convertTemp(it) },
            switchChange = { viewModel.switchChange() }
        )
    }
}
```
<p>
O elemento <b>composable MainScreen</b> declara a variável de estado <b>textState</b> e um manipulador de eventos <b>onTextChange.</b> O primeiro filho do layout Coluna é um componente static Text que exibe um título. A seguir, o <b>InputRow</b> é chamado e são passados ​​os parâmetros necessários. O terceiro filho é outro componente Text, desta vez, configurado para exibir o conteúdo da variável de estado de resultado do modelo de visualização. Por fim, um elemento composable de <b>Button</b> é configurado para chamar a função <b>convertTemp()</b> do view model, passando o <b>textState.</b> A função <b>convertTemp()</b> irá calcular a temperatura convertida e atribuí-la à variável de estado do resultado, desencadeando assim, uma recomposição da hierarquia combinável.
</p>

## Resultado final do app

<div align="center"><br>

<img src="https://github.com/DeniseLeandroDeCastro/view_model_demo/assets/29150094/629cf3e9-2686-4af7-bf02-244d90049c5b" width="300" height="600"/>
<img src="https://github.com/DeniseLeandroDeCastro/view_model_demo/assets/29150094/14014626-8f93-49fc-b4db-d790a6eba0b8" width="300" height="600"/>
  
</div>




