package com.erp.erp.moduleAndSubmodule;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface SubModuleRepoistory extends JpaRepository<SubModule,Integer>{
    List<SubModule> findByModule(Module module);
}
