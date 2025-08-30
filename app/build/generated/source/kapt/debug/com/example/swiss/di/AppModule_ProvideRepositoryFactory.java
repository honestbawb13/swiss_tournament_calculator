package com.example.swiss.di;

import com.example.swiss.data.SwissDatabase;
import com.example.swiss.data.TournamentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideRepositoryFactory implements Factory<TournamentRepository> {
  private final Provider<SwissDatabase> dbProvider;

  public AppModule_ProvideRepositoryFactory(Provider<SwissDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TournamentRepository get() {
    return provideRepository(dbProvider.get());
  }

  public static AppModule_ProvideRepositoryFactory create(Provider<SwissDatabase> dbProvider) {
    return new AppModule_ProvideRepositoryFactory(dbProvider);
  }

  public static TournamentRepository provideRepository(SwissDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideRepository(db));
  }
}
