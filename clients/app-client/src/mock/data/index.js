import Mock from 'mockjs'
import utils from '../../utils'

const Random = Mock.Random;

const createPageData = function (options, total, call) {
    const params = utils.common.getUrlParams(options.url);
    const current = parseInt(params.current), size = parseInt(params.size);
    const pages = parseInt((total % size == 0)?(total/size): (total/size + 1));
    const data = [];

    for (let i = 0; i < (current < pages ? size : (total - ((current - 1) * size))); i++) {
        const item = call(i);
        data.push(item);
    }

    return {
        code: 1,
        current,
        size,
        total,
        pages,
        data
    }
}

const getTopImages = function (options) {
    return {
        code: 1,
        data: [
            {id: 1, path: Random.dataImage('300x200', '首页展示')},
            {id: 2, path: Random.dataImage('300x200', '首页展示')},
            {id: 3, path: Random.dataImage('300x200', '首页展示')}
        ]
    }
}

const getRecommendPosts = function (options) {
    return createPageData(options, 16, (i) => {
        const item = {
            title: Random.ctitle(10,20),
            content: Random.cparagraph(3, 6),
            desc: Random.datetime(),
            status: 'hot',
            author: {
                name: Random.cname(),
                avatar: Random.dataImage('50x50', '头像')
            },
            reads: Random.natural(0, 10000),
            likes: Random.natural(0, 500),
            stars: Random.natural(0, 500),
        };
        if(i < 3){
            item.image = Random.dataImage('200x80', '封面图片');
        }
        return item;
    })
}

const getFriendsShareData = function (options) {
    return createPageData(options, 100, (i) => {
        const item = {id: i+1, name: Random.cname(), avatar: Random.dataImage('60x60', this.name), content: Random.cparagraph(3, 10), date: Random.date(),
            likes: [],
            comments: [],
            images: []
        }
        const likesSize = Random.natural(0, 10), commentsSize = Random.natural(0, 10), imagesSize = Random.natural(0,9);
        for (let j = 0; j < likesSize; j++) {
            item.likes.push({id: j+1, name: Random.cname()})
        }
        for (let j = 0; j < commentsSize; j++) {
            item.comments.push({from: Random.cname(), to: Random.cname(), comment: Random.cparagraph(1,3)});
        }
        for (let j = 0; j < imagesSize; j++) {
            item.images.push({title: Random.title(), src: Random.dataImage('300x500', '晒图啦')})
        }
        return item;
    })
}

export default {
    getTopImages,
    getRecommendPosts,
    getFriendsShareData
}