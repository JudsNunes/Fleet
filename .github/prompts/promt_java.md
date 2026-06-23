---
name: skill-name
description: Description of what the skill does and when to use it
---


# boas práticas
em classe que possuam implements e que tem a marcação @Service às boas práticas seriam sempre usar o lombok com o RequiredArgsConstructor e o método  private final vehicleRepository vehicleRepository;

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

