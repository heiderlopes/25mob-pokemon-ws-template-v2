package br.com.heiderlopes.pokemonwstemplatev2.di

import br.com.heiderlopes.pokemonwstemplatev2.data.remote.PokemonService
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.picasso.PicassoClient
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.retrofit.HttpClient
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.retrofit.RetrofitClient
import br.com.heiderlopes.pokemonwstemplatev2.data.repository.PokemonRepositoryImpl
import br.com.heiderlopes.pokemonwstemplatev2.domain.repository.PokemonRepository
import br.com.heiderlopes.pokemonwstemplatev2.domain.usecase.GetFirstGenerationPokemonsUseCase
import br.com.heiderlopes.pokemonwstemplatev2.presentation.listpokemons.ListPokemonsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainModules = module {
    factory { GetFirstGenerationPokemonsUseCase(pokemonRepository = get()) }
}

val presentationModules = module {
    viewModel { ListPokemonsViewModel(getFirstGenerationPokemonsUseCase =get()) }
}

val dataModules = module {
    factory<PokemonRepository> { PokemonRepositoryImpl(pokemonService = get()) }
}

val networkModules = module {
    single { RetrofitClient(application = androidContext()).newInstance() }
    single { HttpClient(retrofit = get()) }
    factory { get<HttpClient>().create(PokemonService::class.java) }
    single { PicassoClient(application = androidContext()).newInstance() }

}
