package com.example.projetoextensao.api.api1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClimaService {
    @GET("clima/atual") //Aqui colocar o endpoint da api
    Call<DadosClimaticos> getDadosClimaticos(@Query("intervalo") String intervalo);
}
