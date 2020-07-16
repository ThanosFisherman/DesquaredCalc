package com.thanosfisherman.data_network.repos

import com.thanosfisherman.data_network.api.Exchangeratesapi
import com.thanosfisherman.domain.repos.NetworkRepo

class NetworkRepoImpl(private val exchangeratesapi: Exchangeratesapi) : NetworkRepo {
}