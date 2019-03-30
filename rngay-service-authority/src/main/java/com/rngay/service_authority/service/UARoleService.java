package com.rngay.service_authority.service;

import com.rngay.service_authority.model.UARole;

import java.util.List;

public interface UARoleService {

    List<UARole> load(Integer orgId);

    Integer save(UARole uaRole);

    Integer update(UARole uaRole);

}
