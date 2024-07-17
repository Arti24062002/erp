package com.erp.erp.moduleAndSubmodule;



import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepoistory moduleRepository;

    public List<ModuleResponse> getAllModules() {
        List<Module> all = moduleRepository.findAll();
        return all.stream()
                .map(ModuleMapper::moduleToModuleResponse)
                .collect(Collectors.toList());
    }

    public ModuleResponse getModuleById(Integer id) {
        Module module = moduleRepository.findById(id).orElse(null);
        if (module != null) {
            return ModuleMapper.moduleToModuleResponse(module);
        }
        throw new ModuleException("Module not found");
    }
    public Module getModuleByIdOrElseThrowException(Integer id) {
        Module module = moduleRepository.findById(id).orElse(null);
        if (module != null) {
            return (module);
        }
        throw new ModuleException("Module not found");
    }


    public boolean isModuleExist(Integer id) {
        return moduleRepository.findById(id).isPresent();
    }
    public boolean isModuleExist(ModuleRequest id) {
        return moduleRepository.findAll().stream().filter(mr->mr.getModuleName().equals(id.moduleName() )).findFirst().isPresent();
    }
    public boolean isModuleExist(ModuleRequest id,Module exiModule) {
        return moduleRepository.findAll().stream().filter(mr->mr.getModuleName().equals(id.moduleName() )&& mr.getId()!=exiModule.getId()).findFirst().isPresent();
    }
    public void addModule(ModuleRequest moduleRequest) {
        if(!isModuleExist(moduleRequest)){
        Module module = ModuleMapper.fromModuleRequest(moduleRequest);
        moduleRepository.save(module);
        }
        else
        throw new ModuleException("Module Already Exist ");
    }

    public ModuleResponse updateModule(Integer id, ModuleRequest moduleRequest) {
        Module existingModule = moduleRepository.findById(id).orElse(null);
        if (existingModule != null) {
            if(!isModuleExist(moduleRequest,existingModule)){
            existingModule.setModuleName(moduleRequest.moduleName());
            
            moduleRepository.save(existingModule);
            return ModuleMapper.moduleToModuleResponse(existingModule);
            }
            else
            throw new ModuleException("Cant update because module alreay exist with this name");
          
        }
        throw new ModuleException("Module not found");
    }

    public void deleteModule(Integer id) {
        if (isModuleExist(id)) {
            moduleRepository.deleteById(id);
        } else {
            throw new ModuleException("Module not found");
        }
    }
}
