package com.juliabolting.potioncrafterapp.ui.screen;

/**
 * Activity para criar um novo jogador.
 *
 * Exibe uma tela com um campo para o usuário digitar seu nome,
 * e botão para iniciar o jogo com o novo jogador salvo no banco.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/juliabolting/potioncrafterapp/ui/screen/NewPlayerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "db", "Lcom/juliabolting/potioncrafterapp/data/database/AppDatabase;", "playerDao", "Lcom/juliabolting/potioncrafterapp/data/dao/PlayerDAO;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class NewPlayerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.juliabolting.potioncrafterapp.data.database.AppDatabase db;
    private com.juliabolting.potioncrafterapp.data.dao.PlayerDAO playerDao;
    
    public NewPlayerActivity() {
        super();
    }
    
    /**
     * Inicializa a activity, configura os componentes da UI,
     * animação do texto e o clique do botão para salvar jogador.
     */
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}