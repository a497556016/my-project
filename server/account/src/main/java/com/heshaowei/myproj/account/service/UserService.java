package com.heshaowei.myproj.account.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshaowei.myproj.account.entity.Permission;
import com.heshaowei.myproj.account.entity.Resource;
import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.account.repository.RoleRepository;
import com.heshaowei.myproj.account.repository.UserRepository;
import com.heshaowei.myproj.auth.core.SecurityMetadataSourceService;
import com.heshaowei.myproj.auth.core.model.ConfigAttribute;
import com.heshaowei.myproj.auth.core.model.RoleAttribute;
import com.heshaowei.myproj.auth.core.model.UserInfo;
import com.heshaowei.myproj.file.client.controller.FileClient;
import com.heshaowei.myproj.file.client.domain.FileReq;
import com.heshaowei.myproj.utils.PropertyCopys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements com.heshaowei.myproj.auth.core.UserService, SecurityMetadataSourceService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FileClient fileClient;

    @Value("${spring.application.name}")
    private String appId;

    private void updateFileBusId(String path, Long id) {
        if (null != path) {
            this.fileClient.updateBusId(new FileReq(path, appId + "/user/" + id));
        }
    }

    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return this.userRepository.findOne(Example.of(user)).orElse(null);
    }

    public void save(User user) {
        user = this.userRepository.save(user);

        this.updateFileBusId(user.getAvatar(), user.getId());
    }

    public Page selectPage(PageRequest pageReq) {
        return this.userRepository.findAll(pageReq);
    }

    public User selectById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void update(User user) {
        User updateUser = this.userRepository.findById(user.getId()).orElse(new User());
        PropertyCopys.copyNotnull(updateUser, user);
        this.userRepository.saveAndFlush(updateUser);
        this.updateFileBusId(user.getAvatar(), user.getId());
    }

    public void batchDelete(List<Long> ids) {
        final List<User> users = Lists.newArrayList();
        ids.forEach(id -> {
            User user = new User();
            user.setId(id);
            users.add(user);
        });
        this.userRepository.deleteInBatch(users);
    }

    public void updateUserRoles(User user) {
        User update = this.userRepository.findById(user.getId()).get();
        if (null != update) {
            update.setRoles(user.getRoles());
        }
        this.userRepository.save(update);
    }

    private Map<String, Collection<ConfigAttribute>> resourceMaps;
    @Override
    public void loadResourceDefine() {
        resourceMaps = Maps.newHashMap();

        List<Role> roles = this.roleRepository.findAll();
        for (Role role : roles) {

            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                List<Resource> resources = permission.getResources();

                for (Resource resource : resources) {
                    Collection<ConfigAttribute> roleAttributes = resourceMaps.get(resource.getPath());
                    if(null == roleAttributes) {
                        roleAttributes = Lists.newArrayList();
                        resourceMaps.put(resource.getPath(), roleAttributes);
                    }
                    roleAttributes.add(new RoleAttribute(role.getName()));
                }
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(String path) {
        if(null == resourceMaps) {
            this.loadResourceDefine();
        }
        return resourceMaps.get(path);
    }

    @Override
    public UserDetail selectByUsername(String username) {
        User user = this.findByUsername(username);
        UserDetail userInfo = new UserDetail();
        userInfo.setAvatar(user.getAvatar());
        userInfo.setSalt(user.getSalt());
        userInfo.setUsername(username);
        userInfo.setPassword(user.getPassword());
        userInfo.setLock(false);
        userInfo.setRoleAttributes(user.getRoles().stream().map(role -> new RoleAttribute(role.getName())).collect(Collectors.toList()));
        return userInfo;
    }
}
