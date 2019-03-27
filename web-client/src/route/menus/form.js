const routes = [
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

export default routes;