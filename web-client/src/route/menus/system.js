const routes = [
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

export default routes;