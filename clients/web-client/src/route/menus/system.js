const menus = [
    {
        component: () => import('@/views/Main.vue'),
        name: 'Main',
        meta: {
            title: '首页',
            showInFirst: true,
            icon: 'home'
        }
    },
    {
        meta: {
            title: '系统管理',
            icon: 'setting'
        },
        children: [
            {
                component: () => import('@/views/system/user/Manage'),
                name: 'UserManagePage',
                meta: {
                    title: '用户管理',
                    icon: 'user'
                }
            },
            {
                component: () => import('@/views/system/role/Manage'),
                name: 'RoleManagePage',
                meta: {
                    title: '角色管理',
                    icon: 'cluster'
                }
            },
            {
                component: () => import('@/views/system/permission/Manage'),
                name: 'PermissionManagePage',
                meta: {
                    title: '权限管理',
                    icon: 'cluster'
                }
            },
            {
                component: () => import('@/views/system/resource/Manage'),
                name: 'ResourceManagePage',
                meta: {
                    title: '资源管理',
                    icon: 'cluster'
                }
            }
        ]
    },
    {
        name: 'UserCenter',
        meta: {
            title: '个人中心',
            icon: 'user',
            hideInMenu: true
        }
    }
]

export default menus;