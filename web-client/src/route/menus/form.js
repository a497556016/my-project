const menus = [
    {
        name: 'FormMain',
        component: () => import('@/views/form/Main.vue'),
        meta: {
            showInFirst: true,
            title: '自定义表单首页',
            icon: 'home'
        }
    },
    {
        name: 'FormCreate',
        component: () => import('@/views/form/Create.vue'),
        meta: {
            title: '创建表单',
            icon: 'diff'
        }
    },
    {
        name: 'FormManage',
        component: () => import('@/views/form/Manage.vue'),
        meta: {
            title: '表单管理',
            icon: 'form'
        }
    },
    {
        name: 'Test',
        meta: {
            title: '测试栏目'
        },
        children: [
            {
                name: 'Test001',
                meta: {
                    title: '测试001'
                }
            }
        ]
    }
]

export default menus;