package com.skydoves.pokedex.network

import com.skydoves.pokedex.model.PokemonInfo
import com.skydoves.pokedex.model.PokemonResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class PokedexClient @Inject constructor(
  private val pokedexService: PokedexService
) {

  suspend fun fetchPokemonList(
    page: Int
  ): ApiResponse<PokemonResponse> =
    pokedexService.fetchPokemonList(
      limit = PAGING_SIZE,
      offset = page * PAGING_SIZE
    )

  suspend fun fetchPokemonInfo(
    name: String
  ): ApiResponse<PokemonInfo> =
    pokedexService.fetchPokemonInfo(
      name = name
    )

  companion object {
    private const val PAGING_SIZE = 20
  }
}
