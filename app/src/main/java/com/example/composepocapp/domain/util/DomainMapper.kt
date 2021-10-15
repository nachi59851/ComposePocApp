package com.example.composepocapp.domain.util

interface DomainMapper <T,DomainModel> {

    //from network
    fun mapToDomainModel(model: T): DomainModel

    //to network
    fun mapFromDomainModel(domainModel: DomainModel): T

}