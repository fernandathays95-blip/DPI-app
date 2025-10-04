import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Constantes de Limites de Segurança
    private val MIN_DPI_SEGURO = 300
    private val MAX_DPI_SEGURO = 1000
    private val DPI_PADRAO_COMPUTADOR = 800
    // O DPI padrão de fábrica deve ser lido do sistema, mas usaremos um valor comum como exemplo
    private val DPI_PADRAO_FABRICA = 411 

    private lateinit var editDpiManual: EditText
    private lateinit var textAviso: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editDpiManual = findViewById(R.id.edit_dpi_manual)
        textAviso = findViewById(R.id.text_aviso)
        
        // Define o clique do botão "Aplicar DPI Personalizado"
        findViewById<Button>(R.id.btn_aplicar_dpi).setOnClickListener {
            aplicarDPI(editDpiManual.text.toString())
        }

        // Define o clique do botão "Modo Computador"
        findViewById<Button>(R.id.btn_modo_computador).setOnClickListener {
            aplicarDPI(DPI_PADRAO_COMPUTADOR.toString())
        }

        // Define o clique do botão "Reiniciar"
        findViewById<Button>(R.id.btn_reiniciar_dpi).setOnClickListener {
            aplicarDPI(DPI_PADRAO_FABRICA.toString(), isReiniciar = true)
        }
    }

    /**
     * Valida o DPI e simula a aplicação.
     */
    private fun aplicarDPI(dpiInput: String, isReiniciar: Boolean = false) {
        
        val dpiDesejado = dpiInput.toIntOrNull()
        
        if (dpiDesejado == null || dpiDesejado == 0) {
            // Se for reiniciar, usa o padrão de fábrica
            if (isReiniciar) {
                aplicarNoSistema(DPI_PADRAO_FABRICA, "DPI restaurado para o padrão de fábrica: $DPI_PADRAO_FABRICA")
            } else {
                Toast.makeText(this, "Por favor, insira um valor DPI válido.", Toast.LENGTH_SHORT).show()
            }
            return
        }

        var dpiValidado = dpiDesejado
        var mensagem = "DPI aplicado com sucesso: $dpiDesejado"

        // LÓGICA DE VALIDAÇÃO E LIMITAÇÃO DE SEGURANÇA
        when {
            // Limite Inferior
            dpiDesejado < MIN_DPI_SEGURO -> {
                dpiValidado = MIN_DPI_SEGURO
                mensagem = "Valor muito baixo. DPI ajustado para o mínimo seguro: $MIN_DPI_SEGURO."
            }
            // Limite Superior
            dpiDesejado > MAX_DPI_SEGURO -> {
                dpiValidado = MAX_DPI_SEGURO
                mensagem = "Valor muito alto. DPI ajustado para o máximo seguro: $MAX_DPI_SEGURO."
            }
        }
        
        aplicarNoSistema(dpiValidado, mensagem)
    }
    
    /**
     * SIMULAÇÃO da aplicação no sistema e notifica o usuário.
     */
    private fun aplicarNoSistema(dpi: Int, mensagem: String) {
        // --- AQUI É ONDE VOCÊ PRECISARIA DA PERMISSÃO WRITE_SECURE_SETTINGS ---
        // Exemplo (Apenas para demonstração, pode não funcionar sem permissão ou root):
        // Settings.System.putInt(contentResolver, "min_density", dpi)
        
        // Exibir o resultado final para o usuário
        Toast.makeText(this, "$mensagem. Valor Final: $dpi", Toast.LENGTH_LONG).show()
        
        // Atualizar o campo de texto com o valor aplicado/validado
        editDpiManual.setText(dpi.toString())
    }
}
