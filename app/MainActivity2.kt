import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
// ... outras importações

class MainActivity : AppCompatActivity() {
    // ... variáveis e onCreate aqui
    
    // Código de solicitação (pode ser qualquer número único)
    private val OVERLAY_PERMISSION_CODE = 1001

    // Função para verificar se a permissão está OK e solicitá-la, se necessário.
    fun verificarESolicitarPermissaoOverlay(): Boolean {
        // Verifica se o SDK do Android é o 23 (Marshmallow) ou superior,
        // pois a permissão só é necessária a partir dessa versão.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            
            // 1. Checa se o app tem a permissão de desenhar sobre outros
            if (!Settings.canDrawOverlays(this)) {
                
                // 2. Se não tiver, notifica o usuário
                Toast.makeText(
                    this,
                    "Permissão de Janela Flutuante Necessária. Ative-a na próxima tela.",
                    Toast.LENGTH_LONG
                ).show()

                // 3. Redireciona para a tela de configuração
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE)
                return false
            }
        }
        // Se a permissão já estiver OK (ou a versão do Android for antiga), retorna true.
        return true
    }
    
    // Função para tratar o resultado de quando o usuário volta das configurações
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OVERLAY_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "Permissão concedida! Agora você pode ativar o modo flutuante.", Toast.LENGTH_SHORT).show()
                    // INICIE A JANELA FLUTUANTE AQUI
                } else {
                    Toast.makeText(this, "Permissão negada. O modo flutuante não funcionará.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
