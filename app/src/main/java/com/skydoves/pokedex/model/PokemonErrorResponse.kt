package com.skydoves.pokedex.model

/**
 * A customized pokemon error response.
 *
 * @param code A network response code.
 * @param message A network error message.
 */
data class PokemonErrorResponse(
  val code: Int,
  val message: String?
)
