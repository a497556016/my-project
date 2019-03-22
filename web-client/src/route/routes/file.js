const routes = [
    {
        path: '/main',
        component: () => import('@/views/file/Main.vue'),
        name: 'FileSystemMain',
        meta: {
            title: '主页',
            icon: 'home',
            showInFirst: true
        }
    },
    {
        path: '/fileMana',
        component: () => import('@/views/file/Manage.vue'),
        name: 'FileSystemManage',
        meta: {
            title: '文件管理',
            icon: 'file'
        }
    }
]

export default routes;