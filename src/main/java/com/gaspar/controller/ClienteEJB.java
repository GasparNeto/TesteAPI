/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaspar.controller;

import com.gaspar.model.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Neto-PC
 */
@Stateless
public class ClienteEJB {

    @PersistenceContext(unitName = "TesteAPI_PU")
    private EntityManager em;

    public Cliente salvar(Cliente att) {
        return em.merge(att);
    }
}
