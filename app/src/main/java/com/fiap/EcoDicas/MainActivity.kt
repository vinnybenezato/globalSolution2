package com.fiap.EcoDicas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.EcoDicas.adapter.TipAdapter
import com.fiap.EcoDicas.database.TipDatabaseHelper
import com.fiap.EcoDicas.model.Tip

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TipAdapter
    private lateinit var searchView: SearchView
    private lateinit var databaseHelper: TipDatabaseHelper

    private var tips = mutableListOf<Tip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        databaseHelper = TipDatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        tips = databaseHelper.listTips().toMutableList()

        if (tips.isEmpty()) {
            tipsInitializer()
            tips = databaseHelper.listTips().toMutableList()
        }

        adapter = TipAdapter(this, tips)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredTips = tips.filter { it.title.contains(newText ?: "", ignoreCase = true) }
                recyclerView.adapter = TipAdapter(this@MainActivity, filteredTips)
                return true
            }
        })
    }

    private fun tipsInitializer() {
        val initialTips = listOf(
            Tip(
                "Use garrafas reutilizáveis",
                "Substitua garrafas plásticas por opções reutilizáveis.",
                "https://exemplo.com/garrafas-reutilizaveis",
                "Ao evitar garrafas descartáveis, você reduz significativamente a produção de resíduos plásticos."
            ),
            Tip(
                "Composte resíduos orgânicos",
                "Transforme restos de alimentos em adubo natural.",
                "https://exemplo.com/compostagem",
                "A compostagem pode reduzir em até 50% o lixo doméstico e enriquecer o solo de maneira natural."
            ),
            Tip(
                "Plante árvores",
                "Contribua para o meio ambiente plantando árvores na sua região.",
                "https://exemplo.com/plantar-arvores",
                "Uma única árvore pode absorver até 22 kg de CO2 por ano."
            ),
            Tip(
                "Evite descartáveis",
                "Substitua pratos, copos e talheres descartáveis por versões reutilizáveis.",
                "https://exemplo.com/evite-descartaveis",
                "Descartáveis podem levar centenas de anos para se decompor e aumentam os resíduos nos aterros."
            ),
            Tip(
                "Economize água no banho",
                "Reduza o tempo no chuveiro para economizar água.",
                "https://exemplo.com/economizar-agua",
                "Diminuir o tempo do banho em 2 minutos pode economizar até 20 litros de água por dia."
            ),
            Tip(
                "Consuma alimentos locais",
                "Prefira alimentos de produtores locais para reduzir a pegada de carbono.",
                "https://exemplo.com/alimentos-locais",
                "Comprar localmente reduz o impacto ambiental do transporte e apoia pequenos agricultores."
            )
        )

        for (tip in initialTips) {
            databaseHelper.insertTip(tip)
        }
    }
}
