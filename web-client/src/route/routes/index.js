import HomePage from '@/views/Home'

const routes = [
    {
        path: '/',
        redirect: { path: '/home' }
    },
    {
        path: '/login',
        component: () => import('@/views/Login.vue'),
        meta: {
            title: '登录',
            hideInMenu: true
        }
    },
    {
        path: '/home',
        component: HomePage,
        meta: {
            title: '主页',
            hideInMenu: true
        },
        children: [
            {
                path: '/main',
                component: () => import('@/views/Main.vue'),
                name: 'Main',
                meta: {
                    title: '首页',
                    showInFirst: true,
                    icon: 'home'
                }
            },
            {
                path: '/system',
                component: HomePage,
                meta: {
                    title: '系统管理',
                    icon: 'setting'
                },
                children: [
                    {
                        path: '/userManage',
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
                path: '/form',
                component: HomePage,
                meta: {
                    title: '开发工具',
                    icon: 'tool'
                },
                children: [
                    {
                        path: '/formManage',
                        component: () => import('@/views/form/Manage'),
                        name: 'FormManagePage',
                        meta: {
                            title: '表单管理',
                            icon: 'table'
                        }
                    }
                ]
            },
        ]
    }
]

export default routes;