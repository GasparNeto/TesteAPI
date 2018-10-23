/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaspar.ws;

import com.gaspar.controller.ClienteEJB;
import com.gaspar.model.Cliente;
import com.gaspar.util.TokenUtils;
import com.gaspar.util.UsuarioPojo;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * REST Web Service
 *
 * @author Neto-PC
 */
@Path("/")
public class api {

    @Context
    private UriInfo context;
    @EJB
    ClienteEJB clienteEJB;

    /**
     * Creates a new instance of api
     */
    public api() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getToken() {
        return "Bem Vindo ao TESTE API";
    }

    //http://localhost:8080/TesteAPI/webresources/geraToken/{"email": "gaspar@metre","senha": "123"}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("geraToken/{credenciais}")
    public Response getToken(@PathParam("credenciais") String credenciais) {
        Response retorno = Response.status(Status.NOT_FOUND).build();
        try {
            UsuarioPojo usuarioPojo = new Gson().fromJson(credenciais, UsuarioPojo.class);
            if (usuarioPojo.getEmail() != null && usuarioPojo.getSenha() != null) {
                retorno = Response.status(Status.OK).entity(TokenUtils.geraToken(credenciais)).build();
            }
        } catch (Exception e) {
            retorno = Response.status(Status.BAD_REQUEST).build();
        }
        return retorno;
    }

    /*
        TOKEN = eyJhbGciOiJIUzI1NiJ9.eyAiZW1haWwiOiAidGFkZXVAbWV0cmUiLCAic2VuaGEiOiAiMTIzIiB9.qNL0Uz4N52qqRy-ssa_VnwWTT8VAh_eew61sSMSYhsQ
        JSON ={"nomeFantasia": "gasparzinho","dataCadastro": "2018-10-22T00:00:00.000Z","limite": 100.5,"idade": 18}
        URL teste = http://localhost:8080/TesteAPI/webresources/saveCliente/eyJhbGciOiJIUzI1NiJ9.eyAiZW1haWwiOiAidGFkZXVAbWV0cmUiLCAic2VuaGEiOiAiMTIzIiB9.qNL0Uz4N52qqRy-ssa_VnwWTT8VAh_eew61sSMSYhsQ/{"nomeFantasia": "gasparzinho","dataCadastro": "2018-10-22T00:00:00.000Z","limite": 100.5,"idade": 18}
    */
    //
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("saveCliente/{token}/{cliente}")
    public Response insereCliente(@PathParam("token") String token, @PathParam("cliente") String clienteJson) {
        try {
            if (TokenUtils.validaToken(token)) {
                Cliente cliente = new Gson().fromJson(clienteJson, Cliente.class);
                clienteEJB.salvar(cliente);
                return Response.status(Status.CREATED).entity("Cliente cadastrado com sucesso!").build();
            } else {
                return Response.status(Status.UNAUTHORIZED).entity("Acesso não autorizado ou Token inválido!").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.NOT_FOUND).entity("Erro ao salvar o Cliente, Parâmetro inválido.").build();
        }
    }
}
