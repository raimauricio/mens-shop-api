package com.mensshop.mensshop.controller

import com.mensshop.mensshop.model.Produto
import com.mensshop.mensshop.repository.ProdutoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/produtos")
class ProdutoController(
    private val produtoRepository: ProdutoRepository
) {

    @GetMapping
    fun listarProdutos(): List<Produto> = produtoRepository.findAll()
}
