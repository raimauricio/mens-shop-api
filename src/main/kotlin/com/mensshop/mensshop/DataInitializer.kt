package com.mensshop.mensshop

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mensshop.mensshop.model.Produto
import com.mensshop.mensshop.repository.ProdutoRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val produtoRepository: ProdutoRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (produtoRepository.count() == 0L) {
            val mapper = jacksonObjectMapper()
            val inputStream = javaClass.classLoader.getResourceAsStream("data/produtos.json")
            val produtos: List<Produto> = mapper.readValue(inputStream)
            produtoRepository.saveAll(produtos)
            println("Produtos carregados com sucesso!")
        }
    }
}