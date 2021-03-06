/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.presenta.di;

import com.example.presenta.model.Chats;
import com.example.presenta.model.QuoteService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.techery.presenta.addition.flow.util.GsonParceler;
import io.techery.presenta.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import flow.Parceler;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Defines app-wide singletons.
 */
@Module(
    includes = { ActionBarModule.class, Chats.Module.class },
    complete = false,
    library = true)
public class RootModule {
  @Provides @ApplicationScope
  Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides @ApplicationScope Parceler provideParcer(Gson gson) {
    return new GsonParceler(gson);
  }

  @Provides @ApplicationScope QuoteService provideQuoteService() {
    RestAdapter restAdapter =
        new RestAdapter.Builder().setEndpoint("http://www.iheartquotes.com/api/v1/")
            .setConverter(new GsonConverter(new Gson()))
            .build();
    return restAdapter.create(QuoteService.class);
  }
}
