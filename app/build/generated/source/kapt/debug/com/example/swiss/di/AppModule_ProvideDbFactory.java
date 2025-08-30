package com.example.swiss.di;

import android.content.Context;
import com.example.swiss.data.SwissDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideDbFactory implements Factory<SwissDatabase> {
  private final Provider<Context> ctxProvider;

  public AppModule_ProvideDbFactory(Provider<Context> ctxProvider) {
    this.ctxProvider = ctxProvider;
  }

  @Override
  public SwissDatabase get() {
    return provideDb(ctxProvider.get());
  }

  public static AppModule_ProvideDbFactory create(Provider<Context> ctxProvider) {
    return new AppModule_ProvideDbFactory(ctxProvider);
  }

  public static SwissDatabase provideDb(Context ctx) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDb(ctx));
  }
}
