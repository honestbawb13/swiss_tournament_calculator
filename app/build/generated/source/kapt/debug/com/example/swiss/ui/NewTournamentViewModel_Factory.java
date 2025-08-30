package com.example.swiss.ui;

import com.example.swiss.data.TournamentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class NewTournamentViewModel_Factory implements Factory<NewTournamentViewModel> {
  private final Provider<TournamentRepository> repoProvider;

  public NewTournamentViewModel_Factory(Provider<TournamentRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public NewTournamentViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static NewTournamentViewModel_Factory create(Provider<TournamentRepository> repoProvider) {
    return new NewTournamentViewModel_Factory(repoProvider);
  }

  public static NewTournamentViewModel newInstance(TournamentRepository repo) {
    return new NewTournamentViewModel(repo);
  }
}
