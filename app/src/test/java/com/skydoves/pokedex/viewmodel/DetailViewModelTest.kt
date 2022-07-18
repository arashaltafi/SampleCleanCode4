package com.skydoves.pokedex.viewmodel

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.pokedex.MainCoroutinesRule
import com.skydoves.pokedex.network.PokedexClient
import com.skydoves.pokedex.network.PokedexService
import com.skydoves.pokedex.persistence.PokemonInfoDao
import com.skydoves.pokedex.repository.DetailRepository
import com.skydoves.pokedex.ui.details.DetailViewModel
import com.skydoves.pokedex.utils.MockUtil
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DetailViewModelTest {

  private lateinit var viewModel: DetailViewModel
  private lateinit var detailRepository: DetailRepository
  private val pokedexService: PokedexService = mock()
  private val pokdexClient: PokedexClient = PokedexClient(pokedexService)
  private val pokemonInfoDao: PokemonInfoDao = mock()

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    detailRepository = DetailRepository(pokdexClient, pokemonInfoDao, coroutinesRule.testDispatcher)
    viewModel = DetailViewModel(detailRepository, "bulbasaur")
  }

  @Test
  fun fetchPokemonInfoTest() = runTest {
    val mockData = MockUtil.mockPokemonInfo()
    whenever(pokemonInfoDao.getPokemonInfo(name_ = "bulbasaur")).thenReturn(mockData)

    detailRepository.fetchPokemonInfo(
      name = "bulbasaur",
      onComplete = { },
      onError = { }
    ).test(2.toDuration(DurationUnit.SECONDS)) {
      val item = requireNotNull(awaitItem())
      Assert.assertEquals(item.id, mockData.id)
      Assert.assertEquals(item.name, mockData.name)
      Assert.assertEquals(item, mockData)
      awaitComplete()
    }

    verify(pokemonInfoDao, atLeastOnce()).getPokemonInfo(name_ = "bulbasaur")
  }
}
