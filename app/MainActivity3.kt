class MainActivity : AppCompatActivity() {
    // ...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // CHAME A VERIFICAÇÃO DE PERMISSÃO AQUI
        verificarESolicitarPermissaoOverlay() 
        
        // ... restante do seu código onCreate
    }
}
