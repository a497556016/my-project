const routes = [
    {
        path: '/main',
        component: () => import('@/views/Main.vue'),
        name: 'Main',
        meta: {
            title: '主页',
            showInFirst: true
        }
    },
    {
        path: '/fileMana',
        component: () => import('@/views/Main.vue'),
        name: 'Main',
        meta: {
            title: '文件管理'
        }
    }
]

export default routes;