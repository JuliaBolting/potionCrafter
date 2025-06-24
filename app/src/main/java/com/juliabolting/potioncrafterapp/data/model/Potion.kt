package com.juliabolting.potioncrafterapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa uma poção criada pelo jogador.
 *
 * @property id Identificador único da poção (gerado automaticamente).
 * @property nome Nome da poção.
 * @property descricao Descrição da poção.
 * @property efeito Efeito ou habilidade que a poção proporciona.
 * @property imagem Caminho ou URI da imagem associada à poção.
 * @property dataCriacao Timestamp da data e hora em que a poção foi criada (em milissegundos desde epoch).
 */
@Entity
data class Potion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descricao: String,
    val efeito: String,
    val imagem: String,
    val dataCriacao: Long
)