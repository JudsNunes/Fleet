---
name: lombok-aplicacao-performance
description: Description of what the skill does and when to use it

---

# Skill Instructions

Essa Skills tem como objetivo informar a melhor forma de utilizar o lombok para injetar dependências 

```java
package com.evolutech.fleet.service.impl;

import com.evolutech.fleet.entity.Vehicle;
import com.evolutech.fleet.repository.vehicleRepository;
import com.evolutech.fleet.service.vehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
@Slf4j
public abstract class vehicleServiceImpl implements vehicleService {

    private final vehicleRepository vehicleRepository;


    @Override
    public Vehicle saveByEntity(Vehicle vehicle){

    }

}
```