package com.example.swiss.ui;

import androidx.lifecycle.SavedStateHandle;
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
public final class ManualPairingViewModel_Factory implements Factory<ManualPairingViewModel> {
  private final Provider<TournamentRepository> repoProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ManualPairingViewModel_Factory(Provider<TournamentRepository> repoProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repoProvider = repoProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ManualPairingViewModel get() {
    return newInstance(repoProvider.get(), savedStateHandleProvider.get());
  }

  public static ManualPairingViewModel_Factory create(Provider<TournamentRepository> repoProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ManualPairingViewModel_Factory(repoProvider, savedStateHandleProvider);
  }

  public static ManualPairingViewModel newInstance(TournamentRepository repo,
      SavedStateHandle savedStateHandle) {
    return new ManualPairingViewModel(repo, savedStateHandle);
  }
}
