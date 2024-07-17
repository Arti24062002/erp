package com.erp.erp.moduleAndSubmodule;

import org.springframework.stereotype.Service;

import com.erp.erp.permission.Permission;
import com.erp.erp.permission.PermissionException;
import com.erp.erp.permission.PermissionRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubModuleService {
    private final SubModuleRepoistory subModuleRepository;
    private final ModuleService moduleService;
    private final ModuleRepoistory moduleRepository;
    private final PermissionRepository permissionRepository;

    public List<SubModuleResponse> getAllSubModules() {
        List<SubModule> all = subModuleRepository.findAll();
        return all.stream()
                .map(SubModuleMapper::subModuleToSubModuleResponse)
                .collect(Collectors.toList());
    }

    public List<SubModuleResponse> getSubModuleByModuleId(Integer id) {
        if (moduleService.isModuleExist(id)) {
            ModuleResponse module = moduleService.getModuleById(id);
            Module m = new Module();
            m.setId(module.id());
            m.setModuleName(module.moduleName());
            List<SubModule> listSubModule = subModuleRepository.findByModule(m);
            if (listSubModule != null) {
                return listSubModule.stream().map(lm -> SubModuleMapper.subModuleToSubModuleResponse(lm))
                        .collect(Collectors.toList());
            } else
                throw new SubModuleException("SubModule not found");

        }
        throw new ModuleException("Module does not exist");
    }

    public SubModuleResponse getSubModuleById(Integer id) {
        SubModule subModule = subModuleRepository.findById(id).orElse(null);
        if (subModule != null) {
            return SubModuleMapper.subModuleToSubModuleResponse(subModule);
        }
        throw new SubModuleException("SubModule not found");
    }

    public boolean isSubModuleExist(Integer id) {
        return subModuleRepository.findById(id).isPresent();
    }

    public boolean isSubModuleExist(SubModuleRequest subModuleRequest) {

        return subModuleRepository.findAll().stream().filter(f -> {
            return f.getName().equals(subModuleRequest.name());
        })
                .findFirst().isPresent();
    }

    public boolean isSubModuleExist(SubModuleRequest subModuleRequest, SubModule subModule) {

        return subModuleRepository.findAll().stream().filter(f -> {
            return f.getName().equals(subModuleRequest.name()) && f.getId() != subModule.getId();
        })
                .findFirst().isPresent();
    }

    public void addSubModule(SubModuleRequest subModuleRequest) {
        int moduleId = subModuleRequest.moduleId();
        if (moduleId <= 0) {
            throw new SubModuleException("Module id must be provided or its is zero");
        }
        Module existingModule = moduleRepository.findById(moduleId).orElse(null);
        if (existingModule == null) {
            throw new ModuleException("Module does not exist with this id");
        }
        SubModule subModule = SubModuleMapper.fromSubModuleRequest(subModuleRequest, existingModule);

        if (!isSubModuleExist(subModuleRequest)) {
            Permission permission = savePermission(subModule);
            subModule.setPermission(permission);
            subModuleRepository.save(subModule);
        } else
            throw new SubModuleException("SubModule already exist  with this name");
    }

    public SubModuleResponse updateSubModule(Integer id, SubModuleRequest subModuleRequest) {
        int moduleId = subModuleRequest.moduleId();
        if (moduleId <= 0) {
            throw new SubModuleException("Module id must be provided or its is zero");
        }
        Module existingModule = moduleRepository.findById(moduleId).orElse(null);
        if (existingModule == null) {
            throw new ModuleException("Module does not exist with this id");
        }
        SubModule existingSubModule = subModuleRepository.findById(id).orElse(null);
        if (existingSubModule != null) {

            System.out.println(subModuleRequest);
            if (!isSubModuleExist(subModuleRequest,existingSubModule)) {
                existingSubModule.setName(subModuleRequest.name());
                Permission permission = updatePermission(existingSubModule.getPermission(),
                        existingSubModule.getName());
                
                existingSubModule.setPermission(permission);
                subModuleRepository.save(existingSubModule);
            } else
                throw new SubModuleException("Cant update with this name because SubModule already exist");
            return SubModuleMapper.subModuleToSubModuleResponse(existingSubModule);
        }
        throw new SubModuleException("SubModule not found");
    }

    public void deleteSubModule(Integer id) {
        if (isSubModuleExist(id)) {
            subModuleRepository.deleteById(id);
        } else {
            throw new SubModuleException("SubModule not found");
        }
    }

    public Permission savePermission(SubModule subModule) {
        Permission permission = new Permission();
        String name = subModule.getName();
        String newName = name.toLowerCase().replaceAll(" ", "-");
        System.out.println("------------------------NewName " + newName);
        Permission existingPermission = permissionRepository.findAll().stream()
                .filter(fn -> fn.getAccessPermissionName().equals(newName)).findFirst().orElse(null);
        if (existingPermission == null) {
            permission.setAccessPermissionName(newName);
            permission.setUrl("/" + newName);

            return permission;
        }
        throw new PermissionException("Module Permision name cant be same");

    }

    public Permission updatePermission(Permission oldPermission, String name) {

        String newName = name.toLowerCase().replaceAll(" ", "-");

        Permission existingPermission = permissionRepository.findAll().stream()
                .filter(fn -> fn.getAccessPermissionName().equals(newName)).findFirst().orElse(null);
        if (existingPermission == null) {
            oldPermission.setAccessPermissionName(newName);
            oldPermission.setUrl("/" + newName);

            return oldPermission;
        }
        existingPermission.setAccessPermissionName(newName);
        existingPermission.setUrl("/"+newName);
        return existingPermission;
    }
}
