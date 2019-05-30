export default [
    {
        name: 'Emoticon',
        meta: {
            title: '表情包',
            icon: 'smile'
        },
        children: [
            {
                name: 'EmoticonSpider',
                component: () => import('@/views/tools/emoticon/EmoticonSpider'),
                meta: {
                    title: '爬虫管理',
                    icon: 'cloud'
                }
            },
            {
                name: 'EmoticonCatalog',
                component: () => import('@/views/tools/emoticon/EmoticonCatalog'),
                meta: {
                    title: '分类管理',
                    icon: 'profile'
                }
            },
            {
                name: 'EmoticonImg',
                component: () => import('@/views/tools/emoticon/EmoticonImg'),
                meta: {
                    title: '图片管理',
                    icon: 'table'
                }
            }
        ]
    }
]