import Mock from 'mockjs'
import utils from '../../utils'

const Random = Mock.Random;

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
    const params = utils.common.getUrlParams(options.url);
    const current = parseInt(params.current), size = parseInt(params.size);
    const total = 32, pages = parseInt((total % size == 0)?(total/size): (total/size + 1));
    const data = [];
    for (let i = 0; i < (current < pages ? size : (total - ((current - 1) * size))); i++) {
        const item = {title: Random.ctitle(10,18), content: Random.cparagraph(5, 10), desc: Random.datetime(), status: 'hot', author: {
            name: Random.cname(),
                avatar: Random.dataImage('50x50', '头像')
            }};
        if(i < 3){
            item.image = Random.dataImage('200x80', '封面图片');
        }
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

export default {
    getTopImages,
    getRecommendPosts
}